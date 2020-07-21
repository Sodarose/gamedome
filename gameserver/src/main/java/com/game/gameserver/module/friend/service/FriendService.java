package com.game.gameserver.module.friend.service;

import com.game.gameserver.module.email.service.EmailService;
import com.game.gameserver.module.email.type.SystemSender;
import com.game.gameserver.module.friend.dao.FriendDbService;
import com.game.gameserver.module.friend.entity.FriendEntity;
import com.game.gameserver.module.friend.helper.FriendHelper;
import com.game.gameserver.module.friend.manager.FriendManager;
import com.game.gameserver.module.friend.model.Friend;
import com.game.gameserver.module.friend.model.UserFriend;
import com.game.gameserver.module.friend.type.FriendType;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.util.GameUUID;
import com.game.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/8 12:05
 */
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

    public void loadFriends(Player player){
        List<FriendEntity> friendEntities = friendDbService
                .selectFriendEntityList(player.getPlayerEntity().getId());
        UserFriend userFriend = new UserFriend(player.getPlayerEntity().getId());
        friendEntities.forEach(friendEntity -> {
            Friend friend = new Friend();
            BeanUtils.copyProperties(friendEntities,friend);
            Player temp = playerService.getPlayer(friend.getFriendId());
            if(temp!=null){
                friend.setOnline(true);
            }
            userFriend.getFriendMap().put(friend.getFriendId(),friend);
        });
        friendManager.putUserFriend(player.getPlayerEntity().getId(),userFriend);
    }

    /**
     * 展示好友列表
     *
     * @param player
     * @return void
     */
    public void showFriends(Player player){
        UserFriend userFriend = friendManager.getUserFriend(player.getPlayerEntity().getId());
        if(userFriend==null){
            return;
        }
        NotificationHelper.notifyPlayer(player, FriendHelper.buildFriendListMsg(userFriend));
    }

    /**
     * 添加好友
     *
     * @param player
     * @param targetId
     * @return void
     */
    public void applyForFriend(Player player, long targetId){
        UserFriend userFriend = friendManager.getUserFriend(player.getPlayerEntity().getId());
        if(userFriend.getFriendMap().get(targetId)!=null){
            NotificationHelper.notifyPlayer(player,"已经添加对方为好友了");
            return;
        }
        Player target = playerService.getPlayer(targetId);
        // 对方不在线 写入数据库
        if(target==null){
            return;
        }
        UserFriend targetFriends = friendManager.getUserFriend(targetId);
        if(targetFriends==null){
            // 不在线 插入数据库
            return;
        }
        targetFriends.getApplicant().add(player.getPlayerEntity().getId());
        NotificationHelper.notifyPlayer(player,"已发送好友请求");
        NotificationHelper.notifyPlayer(target,MessageFormat
                .format("{0}请求添加您为好友",player.getName()));
    }

    /**
     * 出路玩家申请信息
     *
     * @param player
     * @param targetId
     * @return void
     */
    public void processFriendApply(Player player,long targetId,int agree){
        UserFriend userFriend = friendManager.getUserFriend(player.getPlayerEntity().getId());

        if(!userFriend.getApplicant().contains(targetId)){
            NotificationHelper.notifyPlayer(player,"没有该添加好友信息");
            return;
        }

        // 移除该添加好友信息
        userFriend.getApplicant().remove(targetId);

        if(userFriend.getFriendMap().get(targetId)!=null){
            NotificationHelper.notifyPlayer(player,"已经添加对方为好友了");
            return;
        }
        // 获取对方缓存 判断对方是否在线
        Player target = playerService.getPlayer(targetId);
        // 拒绝
        if(agree==0){
            // 判断用户是否在线
            if(target==null){
                // 发送邮件通知
                emailService.sendEmail(SystemSender.SYSTEM.getId(),targetId,"添加好友失败", MessageFormat.format(
                        "添加{0}为好友被拒绝",player.getPlayerEntity().getName()));
                return;
            }
            NotificationHelper.notifyPlayer(target,MessageFormat.format(
                    "添加{0}为好友被拒绝",player.getPlayerEntity().getName()));
            return;
        }else if(agree==1){
            // 同意添加好友
            Friend playerFriend = new Friend();
            playerFriend.setId(GameUUID.getInstance().generate());
            playerFriend.setFriendId(targetId);
            playerFriend.setFriendType(FriendType.COMMON);
            playerFriend.setPlayerId(player.getId());
            // 放入缓存
            userFriend.getFriendMap().put(playerFriend.getFriendId(),playerFriend);
            if(target!=null){
                playerFriend.setOnline(true);
                NotificationHelper.notifyPlayer(target,MessageFormat.format("{0}已经同意添加您为好友",
                        player.getName()));
            }else{
                emailService.sendEmail(SystemSender.SYSTEM.getId(),targetId,"添加好友成功", MessageFormat.format(
                        "{0}已经同意添加您为好友",player.getPlayerEntity().getName()));
                return;
            }
            // 创建好友
            Friend targetFriend = new Friend();
            targetFriend.setId(GameUUID.getInstance().generate());
            targetFriend.setFriendId(player.getId());
            targetFriend.setFriendType(FriendType.COMMON);
            targetFriend.setPlayerId(targetId);
            targetFriend.setOnline(true);
            // 我方保存数据库
            friendDbService.insert(playerFriend);
            // 对方保存数据库
            friendDbService.insert(targetFriend);
        }
    }

    /**
     * 移除好友
     *
     * @param player
     * @param targetId
     * @return void
     */
    public void removeFriend(Player player,long targetId){
        UserFriend userFriend = friendManager.getUserFriend(player.getPlayerEntity().getId());
        if(userFriend.getFriendMap().get(targetId)==null){
            NotificationHelper.notifyPlayer(player,"对方不是你的好友");
            return;
        }
        // 删除好友
        userFriend.getFriendMap().remove(targetId);
        // 我方列表删除
        friendDbService.deleteAsync(targetId);
        NotificationHelper.notifyPlayer(player,"删除好友成功");
    }

    /**
     * 改变好友类型
     *
     * @param player
     * @param targetId
     * @param friendType
     * @return void
     */
    public void changeFriendType(Player player,long targetId,int friendType){
        UserFriend userFriend = friendManager.getUserFriend(player.getPlayerEntity().getId());
        if(userFriend.getFriendMap().get(targetId)==null){
            NotificationHelper.notifyPlayer(player,"对方不是你的好友");
            return;
        }
        Friend friend = userFriend.getFriendMap().get(targetId);
        friend.setFriendType(friendType);
        // 异步更新数据库
        friendDbService.updateAsync(friend);
        NotificationHelper.notifyPlayer(player,"更改好友类型成功");
    }
}
