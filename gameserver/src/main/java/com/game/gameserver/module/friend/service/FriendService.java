package com.game.gameserver.module.friend.service;

import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.FriendEvent;
import com.game.gameserver.event.event.LoginEvent;
import com.game.gameserver.event.event.LogoutEvent;
import com.game.gameserver.module.email.service.EmailService;
import com.game.gameserver.module.email.type.SystemSender;
import com.game.gameserver.module.friend.dao.FriendDbService;
import com.game.gameserver.module.friend.entity.PlayerFriendEntity;
import com.game.gameserver.module.friend.helper.FriendHelper;
import com.game.gameserver.module.friend.manager.FriendManager;
import com.game.gameserver.module.friend.model.Friend;
import com.game.gameserver.module.friend.model.PlayerFriend;
import com.game.gameserver.module.friend.type.FriendType;
import com.game.gameserver.module.guild.model.Applicant;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author xuewenkang
 * @date 2020/7/8 12:05
 */
@Listener
@Service
public class FriendService {

    @Autowired
    private FriendManager friendManager;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private FriendDbService friendDbService;
    @Autowired
    private EmailService emailService;

    public PlayerFriend getPlayerFriend(long playerId) {
        return friendManager.getPlayerFriend(playerId);
    }

    public void loadFriends(Player player) {
        PlayerFriendEntity playerFriendEntity = friendDbService.select(player.getId());
        if (playerFriendEntity == null) {
            playerFriendEntity = new PlayerFriendEntity(player.getId());
            friendDbService.insertAsync(playerFriendEntity);
        }
        PlayerFriend playerFriend = new PlayerFriend(player.getId());
        BeanUtils.copyProperties(playerFriendEntity, playerFriend);
        playerFriend.getFriendMap().forEach((key, value) -> {
            Player friend = playerService.getPlayer(value.getFriendId());
        });
        friendManager.putPlayerFriend(playerFriend.getPlayerId(), playerFriend);
    }

    /**
     * 展示好友列表
     *
     * @param player
     * @return void
     */
    public void showFriends(Player player) {
        PlayerFriend playerFriend = friendManager.getPlayerFriend(player.getPlayerEntity().getId());
        if (playerFriend == null) {
            return;
        }
        NotificationHelper.notifyPlayer(player, FriendHelper.buildFriendListMsg(playerFriend));
    }

    /**
     * 添加好友
     *
     * @param player
     * @param targetId
     * @return void
     */
    public void applyForFriend(Player player, long targetId) {
        PlayerFriend playerFriend = friendManager.getPlayerFriend(player.getPlayerEntity().getId());
        if (playerFriend.getFriendMap().get(targetId) != null) {
            NotificationHelper.notifyPlayer(player, "已经添加对方为好友了");
            return;
        }
        Player target = playerService.getPlayer(targetId);
        PlayerFriend targetFriend = friendManager.getPlayerFriend(targetId);
        if (targetFriend == null) {
            // 不在线 插入数据库 执行一个异步操作
            friendDbService.submit(() -> {
                // 获取目标好友列表
                PlayerFriendEntity tempFriend = friendDbService.select(targetId);
                // 创建一个申请 放入好友列表
                Applicant applicant = new Applicant(player.getName(), player.getId());
                tempFriend.getApplicantMap().put(player.getName(), applicant);
                // 更新
                friendDbService.update(tempFriend);
                NotificationHelper.notifyPlayer(player, "已发送好友请求");
            });
            return;
        }
        // 创建一个申请
        Applicant applicant = new Applicant(player.getName(), player.getId());
        // 将申请放入对方申请表
        targetFriend.getApplicantMap().put(applicant.getName(), applicant);
        friendDbService.updateAsync(targetFriend);
        // 通知
        NotificationHelper.notifyPlayer(player, "已发送好友请求");
        NotificationHelper.notifyPlayer(target, MessageFormat
                .format("{0}请求添加您为好友", player.getName()));
    }

    /**
     * 处理玩家申请信息
     *
     * @param player
     * @param playerName
     * @return void
     */
    public void processFriendApply(Player player, String playerName, int agree) {
        PlayerFriend playerFriend = friendManager.getPlayerFriend(player.getPlayerEntity().getId());

        if (!playerFriend.getApplicantMap().containsKey(playerName)) {
            NotificationHelper.notifyPlayer(player, "没有该添加好友信息");
            return;
        }
        // 移除该添加好友信息
        Applicant applicant = playerFriend.getApplicantMap().remove(playerName);
        if (playerFriend.getFriendMap().get(applicant.getPlayerId()) != null) {
            // 宁已经添加对方为好友了
            NotificationHelper.notifyPlayer(player, "已经添加对方为好友了");
            return;
        }
        Player target = playerService.getPlayer(applicant.getPlayerId());
        if (agree == 0) {
            // 判断用户是否在线
            if (target == null) {
                // 发送邮件通知
                emailService.sendEmail(SystemSender.SYSTEM.getId(), applicant.getPlayerId(), "添加好友失败", MessageFormat.format(
                        "添加{0}为好友被拒绝", player.getPlayerEntity().getName()));
                return;
            }
            NotificationHelper.notifyPlayer(target, MessageFormat.format(
                    "添加{0}为好友被拒绝", player.getPlayerEntity().getName()));
        } else if (agree == 1) {
            PlayerFriend targetFriend = friendManager.getPlayerFriend(applicant.getPlayerId());
            // 对方不在线
            if (targetFriend == null) {
                friendDbService.submit(() -> {
                    PlayerFriendEntity tempFriend = friendDbService.select(applicant.getPlayerId());
                    // 创建对方好友信息
                    Friend friend = new Friend();
                    friend.setName(player.getName());
                    friend.setFriendId(player.getId());
                    friend.setFriendType(FriendType.COMMON);
                    tempFriend.getFriendMap().put(friend.getFriendId(), friend);
                    // 发送邮件通知
                    emailService.sendEmail(SystemSender.SYSTEM.getId(), applicant.getPlayerId(), "添加好友成功", MessageFormat.format(
                            "{0}已经同意添加您为好友", player.getName()));
                    friendDbService.update(tempFriend);
                });
            } else {
                // 对方在线
                Friend friend = new Friend();
                friend.setName(player.getName());
                friend.setFriendId(player.getId());
                friend.setFriendType(FriendType.COMMON);
                targetFriend.getFriendMap().put(friend.getFriendId(), friend);
                NotificationHelper.notifyPlayer(target, MessageFormat.format("{0}已经同意添加您为好友",
                        player.getName()));
                friendDbService.updateAsync(targetFriend);
                EventBus.EVENT_BUS.fire(new FriendEvent(target, player));
            }
            // 创建我方好友信息
            Friend friend = new Friend();
            friend.setFriendType(FriendType.COMMON);
            friend.setFriendId(applicant.getPlayerId());
            friend.setName(applicant.getName());
            playerFriend.getFriendMap().put(friend.getFriendId(), friend);
            // 异步保存
            friendDbService.updateAsync(playerFriend);
            NotificationHelper.notifyPlayer(player, "已同意添加对方为好友");
            // 发送添加好友事件
            EventBus.EVENT_BUS.fire(new FriendEvent(player, target));
        }
    }

    /**
     * 移除好友
     *
     * @param player
     * @param targetId
     * @return void
     */
    public void removeFriend(Player player, long targetId) {
        PlayerFriend playerFriend = friendManager.getPlayerFriend(player.getPlayerEntity().getId());
        if (player.getId() == targetId) {
            return;
        }
        if (playerFriend.getFriendMap().get(targetId) == null) {
            NotificationHelper.notifyPlayer(player, "对方不是你的好友");
            return;
        }
        // 删除好友
        playerFriend.getFriendMap().remove(targetId);
        // 我方列表删除
        friendDbService.updateAsync(playerFriend);
        NotificationHelper.notifyPlayer(player, "删除好友成功");
    }

    /**
     * 改变好友类型
     *
     * @param player
     * @param targetId
     * @param friendType
     * @return void
     */
    public void changeFriendType(Player player, long targetId, int friendType) {
        PlayerFriend playerFriend = friendManager.getPlayerFriend(player.getPlayerEntity().getId());
        if (playerFriend.getFriendMap().get(targetId) == null) {
            NotificationHelper.notifyPlayer(player, "对方不是你的好友");
            return;
        }
        Friend friend = playerFriend.getFriendMap().get(targetId);
        friend.setFriendType(friendType);
        // 异步更新数据库
        friendDbService.updateAsync(playerFriend);
        NotificationHelper.notifyPlayer(player, "更改好友类型成功");
    }

    @EventHandler
    public void handleLogoutEvent(LogoutEvent logoutEvent){
        Player player = logoutEvent.getPlayer();
        friendManager.removePlayerFriend(player.getId());
    }
}
