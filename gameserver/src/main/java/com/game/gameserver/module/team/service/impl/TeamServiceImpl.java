package com.game.gameserver.module.team.service.impl;

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
import com.game.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public com.game.protocol.Team.CreateTeamRes createTeam(PlayerObject playerObject, String teamName, int maxNum) {
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
        // 通知队伍成员解散队伍
        Message message = MessageUtil.createMessage(ModuleKey.TEAM_MODULE,
                TeamCmd.DISSOLVE_TEAM_NOTIFY, null);
        for (PlayerObject player : playerObjects) {
            player.getChannel().writeAndFlush(message);
        }
    }

    /**
     * 获取队伍列表
     *
     * @return com.game.protocol.Team.TeamList
     */
    @Override
    public com.game.protocol.Team.TeamList getTeamList() {
        List<Team> teamList = teamManager.getListTeamObject();
        com.game.protocol.Team.TeamList.Builder builder = com.game.protocol.Team.TeamList.newBuilder();
        for (Team team : teamList) {
            com.game.protocol.Team.TeamInfo teamInfo = ProtocolFactory.createTeamInfo(team);
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
    public void entryTeam(PlayerObject playerObject, Long teamId) {
        if (playerObject.getTeamId() != null) {
            return;
        }
        Team team = teamManager.getTeamObject(teamId);
        if(team ==null){
            return;
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

    }


}
