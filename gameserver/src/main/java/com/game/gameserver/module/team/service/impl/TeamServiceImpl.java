package com.game.gameserver.module.team.service.impl;

import com.game.gameserver.module.chat.entity.ChatChannel;
import com.game.gameserver.module.chat.manager.ChatManager;
import com.game.gameserver.module.chat.type.ChannelType;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.manager.TeamManager;
import com.game.gameserver.module.team.entity.Team;
import com.game.gameserver.module.team.service.TeamService;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.team.TeamCmd;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.Message;
import com.game.protocol.TeamProtocol;
import com.game.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author xuewenkang
 * @date 2020/6/9 18:54
 */
@Service
public class TeamServiceImpl implements TeamService {

    private final static Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Autowired
    private TeamManager teamManager;
    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private ChatManager chatManager;

    /**
     * 创建队伍
     *
     * @param playerObject
     * @param teamName
     * @param maxNum
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    @Override
    public  TeamProtocol.CreateTeamRes createTeam(PlayerObject playerObject, String teamName, int maxNum) {
        if (playerObject.getTeamId() != null) {
            return ProtocolFactory.createCreateTeamRes(1001, "以有队伍", null);
        }
        // 创建队伍
        Team team = new Team(playerObject, teamName, maxNum);
        playerObject.setTeamId(team.getId());
        // 创建队伍聊天频道
        Long channelId = chatManager.createChatChannel();
        team.setChannelId(channelId);
        // 进入频道
        playerObject.getPlayerChannelMap().put(ChannelType.TEAM_CHAT, channelId);
        chatManager.entryChannel(playerObject, channelId);
        // 存储
        teamManager.putTeamObject(team);
        return ProtocolFactory.createCreateTeamRes(0, "创建队伍成功", team);
    }

    /**
     * 解散队伍
     *
     * @param playerObject
     * @return com.game.protocol.Team.TeamResult
     */
    @Override
    public void dissolveTeam(PlayerObject playerObject) {
        Long teamId = playerObject.getTeamId();
        if (teamId == null) {
            return;
        }
        Team team = teamManager.getTeamObject(teamId);
        // 没有该队伍存在
        if (team == null) {
            playerObject.setTeamId(null);
            return;
        }
        Lock lock = team.getWriteLock();
        lock.lock();
        try {
            if (team.getCaptainId().equals(teamId)) {
                return;
            }
            // 删除队伍聊天频道
            Long channelId = team.getChannelId();
            chatManager.removeChannel(channelId);
            // 删除队伍成员中的组队标记
            List<PlayerObject> playerObjects = new ArrayList<>();
            for (Long playerId : team.getMembers()) {
                PlayerObject player = playerManager.getPlayerObject(playerId);
                player.setTeamId(null);
                playerObjects.add(player);
            }
            // 删除队伍
            teamManager.removeTeamObject(team.getId());
            // 发出解散队伍通知
            Message message = MessageUtil.createMessage(ModuleKey.TEAM_MODULE,
                    TeamCmd.DISSOLVE_TEAM_NOTIFY, null);
            for (PlayerObject player : playerObjects) {
                player.getChannel().writeAndFlush(message);
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * 获取队伍列表
     *
     * @return com.game.protocol.Team.TeamList
     */
    @Override
    public TeamProtocol.TeamList getTeamList() {
        List<Team> teamList = teamManager.getListTeamObject();
        TeamProtocol.TeamList.Builder builder = TeamProtocol.TeamList.newBuilder();
        for (Team team : teamList) {
            TeamProtocol.TeamInfo teamInfo = ProtocolFactory.createTeamInfo(team);
            builder.addTeamInfo(teamInfo);
        }
        return builder.build();
    }

    /**
     * 进入队伍
     *
     * @param playerObject
     * @param teamId
     * @return com.game.protocol.Team.EntryTeamReq
     */
    @Override
    public  TeamProtocol.EntryTeamRes entryTeam(PlayerObject playerObject, Long teamId) {
        if (playerObject.getTeamId() != null) {
            return ProtocolFactory.createEntryRes(1001,"已有队伍",null);
        }
        Team team = teamManager.getTeamObject(teamId);
        // 队伍不存在
        if(team ==null){
            return ProtocolFactory.createEntryRes(1002,"队伍不存在",null);
        }
        Lock lock = team.getWriteLock();
        lock.lock();
        try {
            // 队伍已经进入了副本
            if (team.getInstanceId() != null) {
                return ProtocolFactory.createEntryRes(1003, "队伍已经进入了副本", null);
            }
            // 队伍已经满
            if (team.isFull()) {
                return ProtocolFactory.createEntryRes(1004, "队伍已经满了", null);
            }
            // 进入队伍
            boolean result = team.entryTeam(playerObject);
            if (!result) {
                return ProtocolFactory.createEntryRes(1005, "已经在队伍中", null);
            }
            playerObject.setTeamId(teamId);
            // 进入队伍聊天频道
            ChatChannel chatChannel = chatManager.getChatChannel(team.getChannelId());
            chatChannel.exit(playerObject.getPlayer().getId());
            // 更新队伍信息
            syncTeam(team);
            return ProtocolFactory.createEntryRes(1003, "进入队伍", team);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 退出队伍
     *
     * @param playerObject
     * @return void
     */
    @Override
    public void exitTeam(PlayerObject playerObject) {
        Long teamId = playerObject.getTeamId();
        // 角色没有加入队伍
        if(teamId==null){
            return;
        }
        Team team = teamManager.getTeamObject(teamId);
        // 队伍不存在
        if(team ==null){
            return;
        }
        Lock lock = team.getWriteLock();
        lock.lock();
        try {
            boolean result = team.exitTeam(playerObject);
            if (!result) {
                return;
            }
            // 队伍为空 删除队伍
            if(team.getCurrNum()==0){
                teamManager.removeTeamObject(team.getId());
            }
            playerObject.setTeamId(null);
            // 给客户端发送退出组队通知
            Message message = MessageUtil.createMessage(ModuleKey.TEAM_MODULE,TeamCmd.EXIT_TEAM_NOTIFY,null);
            playerObject.getChannel().writeAndFlush(message);
            // 同步组队数据
            syncTeam(team);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 提出队伍
     *
     * @param playerObject
     * @param playerId
     * @return void
     */
    @Override
    public void kickTeam(PlayerObject playerObject, Long playerId) {
        Long teamId = playerObject.getTeamId();
        // 角色没有加入队伍
        if(teamId==null){
            return;
        }
        Team team = teamManager.getTeamObject(teamId);
        // 队伍不存在
        if(team ==null){
            return;
        }
        Lock lock = team.getWriteLock();
        lock.lock();
        try {
            // 非队长
            if (!team.getCaptainId().equals(playerObject.getPlayer().getId())) {
                return;
            }
            // 队伍中没有该队员
            if (!team.hasPlayer(playerId)) {
                return;
            }
            PlayerObject targetPlayer = playerManager.getPlayerObject(playerId);
            team.exitTeam(targetPlayer);
            // 给该队员发出踢出通知
            Message kickNotify = MessageUtil.createMessage(ModuleKey.TEAM_MODULE,
                    TeamCmd.KICK_TEAM_NOTIFY, null);
            targetPlayer.getChannel().writeAndFlush(kickNotify);
            // 同步组队信息
            syncTeam(team);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 同步队伍数据到队员
     *
     * @param team
     * @return void
     */
    private void syncTeam(Team team) {
        TeamProtocol.TeamInfo teamInfo = ProtocolFactory.createTeamInfo(team);
        Message syncMsg = MessageUtil.createMessage(ModuleKey.TEAM_MODULE, TeamCmd.SYNC_TEAM
                , teamInfo.toByteArray());
        List<Long> teamMembers = team.getMembers();
        for (Long playerId : teamMembers) {
            PlayerObject playerObject = playerManager.getPlayerObject(playerId);
            if(playerObject==null){
                continue;
            }
            playerObject.getChannel().writeAndFlush(syncMsg);
        }
    }
}
