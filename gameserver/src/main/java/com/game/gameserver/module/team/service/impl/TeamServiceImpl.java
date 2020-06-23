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
    public TeamProtocol.CreateTeamRes createTeam(PlayerObject playerObject, String teamName, int maxNum) {
        if (playerObject.getTeamId() != null) {
            return ProtocolFactory.createCreateTeamRes(1001, "以有队伍");
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

        return ProtocolFactory.createCreateTeamRes(0, "创建队伍成功");
    }

    /**
     * @param playerObject
     * @return com.game.protocol.TeamProtocol.CheckTeamRes
     */
    @Override
    public TeamProtocol.CheckTeamRes checkTeamRes(PlayerObject playerObject) {
        Long teamId = playerObject.getTeamId();
        if (teamId == null) {
            return ProtocolFactory.createCheckTeamRes(1001, "角色没有组队", null);
        }
        Team team = teamManager.getTeamObject(teamId);
        if (team == null) {
            playerObject.setTeamId(null);
            return ProtocolFactory.createCheckTeamRes(1002, "队伍不存在", null);
        }
        return ProtocolFactory.createCheckTeamRes(0, "success", team);
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
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取队伍列表
     *
     * @return com.game.protocol.Team.TeamList
     */
    @Override
    public TeamProtocol.TeamListRes getTeamList() {
        List<Team> teamList = teamManager.getListTeamObject();
        TeamProtocol.TeamListRes.Builder builder = TeamProtocol.TeamListRes.newBuilder();
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
    public TeamProtocol.EntryTeamRes entryTeam(PlayerObject playerObject, Long teamId) {
        if (playerObject.getTeamId() != null) {
            return ProtocolFactory.createEntryRes(1001, "已有队伍");
        }
        Team team = teamManager.getTeamObject(teamId);
        // 队伍不存在
        if (team == null) {
            return ProtocolFactory.createEntryRes(1002, "队伍不存在");
        }
        Lock lock = team.getWriteLock();
        lock.lock();
        try {
            // 队伍已经进入了副本
            if (team.getInstanceId() != null) {
                return ProtocolFactory.createEntryRes(1003, "队伍已经进入了副本");
            }
            // 队伍已经满
            if (team.isFull()) {
                return ProtocolFactory.createEntryRes(1004, "队伍已经满了");
            }
            // 进入队伍
            boolean result = team.entryTeam(playerObject);
            if (!result) {
                return ProtocolFactory.createEntryRes(1005, "已经在队伍中");
            }
            playerObject.setTeamId(teamId);
            // 进入队伍聊天频道
            ChatChannel chatChannel = chatManager.getChatChannel(team.getChannelId());
            chatChannel.exit(playerObject.getPlayer().getId());
            return ProtocolFactory.createEntryRes(0, "进入队伍");
        } finally {
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
    public TeamProtocol.ExitTeamRes exitTeam(PlayerObject playerObject) {
        Long teamId = playerObject.getTeamId();
        // 角色没有加入队伍
        if (teamId == null) {
            return TeamProtocol.ExitTeamRes.newBuilder().setCode(1001).setMsg("尚未加入队伍").build();
        }
        Team team = teamManager.getTeamObject(teamId);
        // 队伍不存在
        if (team == null) {
            playerObject.setTeamId(null);
            return TeamProtocol.ExitTeamRes.newBuilder().setCode(1002).setMsg("队伍不存在").build();
        }
        // 在副本中不允许退出队伍 只能通过退出副本来退出队伍
        if (playerObject.getInstanceId() != null) {
            return TeamProtocol.ExitTeamRes.newBuilder().setCode(1002).setMsg("副本中不允许退出队伍").build();
        }

        Lock lock = team.getWriteLock();
        lock.lock();
        try {
            if (!team.hasPlayer(playerObject.getPlayer().getId())) {
                playerObject.setTeamId(null);
                return TeamProtocol.ExitTeamRes.newBuilder().setCode(1003).setMsg("目标队伍没有你").build();
            }
            team.exitTeam(playerObject);
            // 如果退出的人是队长 则移交队长
            if (playerObject.getPlayer().getId().equals(team.getCaptainId())) {
                if (!team.getMembers().isEmpty()) {
                    Long playerId = team.getMembers().get(0);
                    team.changeCaptain(playerId);
                }
            }
            // 队伍为空 删除队伍
            if (team.getMembers().isEmpty()) {
                teamManager.removeTeamObject(team.getId());
            }
            playerObject.setTeamId(null);
            return TeamProtocol.ExitTeamRes.newBuilder().setCode(0).setMsg("退出队伍成功").build();
        } finally {
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
        if (teamId == null) {
            return;
        }
        Team team = teamManager.getTeamObject(teamId);
        // 队伍不存在
        if (team == null) {
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
        } finally {
            lock.unlock();
        }
    }


}
