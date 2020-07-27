package com.game.gameserver.net.modelhandler.player;

import com.game.gameserver.module.user.module.User;
import com.game.gameserver.module.user.service.UserService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/5/25 12:21
 */
@ModuleHandler(module = ModuleKey.PLAYER_MODULE)
@Component
public class PlayerHandle extends BaseHandler {
    private final static int LOGIN_PARAM_LENGTH = 1;
    private final static int CREATE_ROLE_PARAM_LENGTH = 2;

    @Autowired
    private PlayerService playerService;

    @CmdHandler(cmd = PlayerCmd.ROLE_LIST)
    public void roleList(Message message, Channel channel) {
        // 验证账号是否已经登录
        User user = channel.attr(UserService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (user == null) {
            NotificationHelper.notifyChannel(channel, "未登录");
            return;
        }
        // 请求角色列表
        playerService.findRoleList(user, channel);
    }

    @CmdHandler(cmd = PlayerCmd.CAREER_LIST)
    public void careerList(Message message, Channel channel) {
        // 验证账号是否已经登录
        User user = channel.attr(UserService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (user == null) {
            NotificationHelper.notifyChannel(channel, "未登录");
            return;
        }
        // 请求职业列表
        playerService.findCareerList(channel);
    }

    @CmdHandler(cmd = PlayerCmd.LOGIN)
    public void login(Message message, Channel channel) {
        User user = channel.attr(UserService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (user == null) {
            NotificationHelper.notifyChannel(channel, "未登录");
            return;
        }
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player!=null){
            NotificationHelper.notifyChannel(channel, "请勿重复登录");
            return;
        }
        long playerId = Long.parseLong(message.getContent());
        playerService.login(user,playerId, channel);
    }

    @CmdHandler(cmd = PlayerCmd.CREATE)
    public void createRole(Message message, Channel channel) {
        User user = channel.attr(UserService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (user == null) {
            NotificationHelper.notifyChannel(channel, "未登录");
            return;
        }
        String[] array = message.getContent().split("\\s+");
        if (array.length != CREATE_ROLE_PARAM_LENGTH) {
            NotificationHelper.notifyChannel(channel, "参数错误");
            return;
        }
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player!=null){
            NotificationHelper.notifyChannel(channel, "请先退出当前角色");
            return;
        }
        String name = array[0];
        int careerId = Integer.parseInt(array[1]);
        playerService.createRole(user, name, careerId, channel);
    }

    @CmdHandler(cmd = PlayerCmd.SHOW_PLAYER)
    public void showPlayer(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        playerService.showPlayer(player);
    }

    @CmdHandler(cmd = PlayerCmd.LOGOUT)
    public void logout(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        playerService.logout(player);
    }
}
