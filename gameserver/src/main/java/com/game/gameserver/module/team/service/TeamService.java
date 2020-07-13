package com.game.gameserver.module.team.service;

import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.team.helper.TeamHelper;
import com.game.gameserver.module.team.manager.TeamManager;
import com.game.gameserver.module.team.model.Team;
import com.game.gameserver.util.GameUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/13 12:06
 */
@Service
public class TeamService {
    private final static Logger logger = LoggerFactory.getLogger(TeamService.class);
    private final static int MAX_CAPACITY = 8;
    private final static int MIN_CAPACITY = 2;

    @Autowired
    TeamManager teamManager;
    @Autowired
    private PlayerService playerService;

    /**
     * @param player
     * @param teamName
     * @param capacity
     * @return void
     */
    public void createTeam(Player player, String teamName, int capacity) {

        if (player.getTeamId() != null) {
            NotificationHelper.notifyPlayer(player, "已有队伍 不能创建新的队伍");
            return;
        }
        if (capacity < MIN_CAPACITY || capacity > MAX_CAPACITY) {
            NotificationHelper.notifyPlayer(player, MessageFormat.format("队伍人数著能在{0} {1} 之间",
                    MIN_CAPACITY, MAX_CAPACITY));
            return;
        }
        // 创建一个新的队伍
        Team team = new Team();
        team.setId(GameUUID.getInstance().generate());
        team.setTeamName(teamName);
        team.setCapacity(capacity);
        team.setCaptainId(player.getPlayerEntity().getId());
        team.getMemberMap().put(player.getPlayerEntity().getId(), player);
        // 放入缓存
        teamManager.putTeam(team);
        // 设置组队标识
        player.setTeamId(team.getId());
        NotificationHelper.notifyPlayer(player,
                MessageFormat.format("创建队伍{0}成功", teamName));
    }

    /**
     * 展示队伍
     *
     * @param player
     * @return void
     */
    public void showTeam(Player player) {
        if (player.getTeamId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有队伍");
            return;
        }
        Team team = teamManager.getTeam(player.getTeamId());
        if (team == null) {
            NotificationHelper.notifyPlayer(player, "队伍不存在");
            player.setTeamId(null);
            return;
        }
        // 发出信息
        NotificationHelper.notifyPlayer(player, TeamHelper.buildTeamMsg(team));
    }

    /**
     * 展示所有队伍列表
     *
     * @param player
     * @return void
     */
    public void showTeamList(Player player) {
        List<Team> teams = teamManager.getTeamList();
        NotificationHelper.notifyPlayer(player, TeamHelper.buildTeamListMsg(teams));
    }

    /**
     * 申请组队
     *
     * @param player
     * @param teamId
     * @return void
     */
    public void applyForItem(Player player, long teamId) {
        if (player.getTeamId() != null) {
            NotificationHelper.notifyPlayer(player, "你已经有队伍了");
            return;
        }
        Team team = teamManager.getTeam(teamId);
        if (team == null) {
            NotificationHelper.notifyPlayer(player, "队伍不存在");
            return;
        }
        // 放入申请人列表
        team.getApply().add(player.getPlayerEntity().getId());
        NotificationHelper.notifyPlayer(player,"已发送申请");
        NotificationHelper.notifyTeam(team, MessageFormat.format("玩家id:{0} 姓名:{1}申请加入队伍", player
                .getPlayerEntity().getId() + "", player.getPlayerEntity().getName()));
    }

    /**
     * 邀请入队
     *
     * @param player
     * @return void
     */
    public void inviteTeam(Player player, long targetId) {
        if (player.getTeamId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有队伍");
            return;
        }
        Player targetPlayer = playerService.getPlayer(targetId);
        if (targetPlayer == null) {
            NotificationHelper.notifyPlayer(player, "玩家不在线");
            return;
        }
        if (targetPlayer.getTeamId() != null) {
            NotificationHelper.notifyPlayer(player, "该玩家已经有队伍了");
            return;
        }
        // 放入邀请人列表
        Team team = teamManager.getTeam(player.getTeamId());
        team.getInvite().add(targetId);
        // 通知对方
        NotificationHelper.notifyPlayer(player,"已发送邀请");
        NotificationHelper.notifyPlayer(targetPlayer, MessageFormat.format("队伍id:{0} name:{1} 邀请您进入组队",
                team.getId() + "", team.getTeamName()));
    }

    /**
     * @param player
     * @param targetId
     * @param agree    是否同意该玩家进入队伍
     * @return void
     */
    public void processApply(Player player, long targetId, int agree) {
        if (player.getTeamId() == null) {
            NotificationHelper.notifyPlayer(player, "你没有队伍");
            return;
        }
        Team team = teamManager.getTeam(player.getTeamId());
        // 玩家根本们没有申请这个队伍
        if (!team.getApply().contains(targetId)) {
            return;
        }

        // 移除该标记
        team.getApply().remove(targetId);

        // 目标玩家是否在线
        Player targetPlayer = playerService.getPlayer(targetId);
        if (targetPlayer == null) {
            NotificationHelper.notifyPlayer(player, "玩家不在线");
            return;
        }

        // 玩家已有队伍
        if (targetPlayer.getTeamId() != null) {
            return;
        }

        // 拒绝
        if (agree == 0) {
            NotificationHelper.notifyPlayer(targetPlayer, "对方拒绝了你的组队申请");
            return;
        }
        // 同意
        if (!team.hasScape()) {
            return;
        }
        // 进入组队
        team.getMemberMap().put(targetPlayer.getPlayerEntity().getId(), targetPlayer);
        targetPlayer.setTeamId(team.getId());
        // 发出组队事件

        NotificationHelper.notifyTeam(team, MessageFormat.format("玩家{0}进入队伍", targetPlayer
                .getPlayerEntity().getName()));

    }

    /**
     * 处理组队邀请
     *
     * @param player
     * @param itemId
     * @return void
     */
    public void processInvite(Player player, long itemId, int agree) {
        Team team = teamManager.getTeam(itemId);
        if (team == null) {
            return;
        }
        if (!team.getInvite().contains(player.getPlayerEntity().getId())) {
            return;
        }
        // 移除邀请队列
        team.getInvite().remove(player.getPlayerEntity().getId());
        // 如果已有队伍直接返回
        if (player.getTeamId() != null) {
            return;
        }
        // 拒绝
        if (agree == 0) {
            return;
        }
        // 同意
        if (!team.hasScape()) {
            NotificationHelper.notifyPlayer(player, "队伍已满");
            return;
        }
        // 进入组队
        team.getMemberMap().put(player.getPlayerEntity().getId(), player);
        player.setTeamId(team.getId());

        NotificationHelper.notifyTeam(team, MessageFormat.format("玩家{0}进入队伍", player
                .getPlayerEntity().getName()));
    }

    /**
     * 退出组队
     *
     * @param player
     * @return void
     */
    public void exitItem(Player player) {
        Team team = teamManager.getTeam(player.getTeamId());
        if (team == null) {
            return;
        }
        // 移除玩家
        team.getMemberMap().remove(player.getPlayerEntity().getId());
        player.setTeamId(null);
        // 队伍是否为空
        if (team.getMemberMap().size() == 0) {
            // 移除队伍
            teamManager.remove(team.getId());
            return;
        }
        // 如果是队长 则移交队长
        if (player.getPlayerEntity().getId().equals(team.getCaptainId())) {
            Iterator<Map.Entry<Long, Player>> iterator = team.getMemberMap().entrySet().iterator();
            if (iterator.hasNext()) {
                team.setCaptainId(iterator.next().getValue().getPlayerEntity().getId());
            }
        }
        NotificationHelper.notifyTeam(team, MessageFormat.format("玩家{0}退出队伍", player
                .getPlayerEntity().getName()));
    }

    public void dissolveTeam(Player player) {
        Team team = teamManager.getTeam(player.getTeamId());
        if (team == null) {
            return;
        }
        if (!player.getPlayerEntity().getId().equals(team.getCaptainId())) {
            NotificationHelper.notifyPlayer(player, "你不是队长");
            return;
        }
        // 解散队伍
        team.getMemberMap().forEach((key, value) -> {
            value.setTeamId(null);
        });
        // 移除队伍
        teamManager.remove(team.getId());
        // 通知
        NotificationHelper.notifyTeam(team,"队伍已经被解散了");
    }

}
