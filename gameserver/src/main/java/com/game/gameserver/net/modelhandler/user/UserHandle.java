package com.game.gameserver.net.modelhandler.account;

import com.game.gameserver.module.user.module.User;
import com.game.gameserver.module.user.service.UserService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账户模块处理器
 *
 * @author xuewenkang
 * @date 2020/5/24 15:53
 */
@ModuleHandler(module = ModuleKey.ACCOUNT_MODULE)
@Component
public class AccountHandle extends BaseHandler {
    private final static int LOGIN_PARAM_LENGTH = 2;
    private final static int REGISTER_PARAM_LENGTH = 2;

    @Autowired
    private UserService userService;

    /**
     * 账号登录请求
     *
     * @param message 消息体
     * @param channel 用户通道
     * @return void
     */
    @CmdHandler(cmd = AccountCmd.LOGIN)
    public void login(Message message, Channel channel) {
        // 判断是否重复登录
        User user = channel.attr(UserService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (user != null) {
            NotificationHelper.notifyChannel(channel, "请勿重复登录账户");
            return;
        }
        String[] array = message.getContent().split("\\s+");
        if (array.length != LOGIN_PARAM_LENGTH) {
            NotificationHelper.notifyChannel(channel, "参数错误");
            return;
        }
        String loginId = array[0];
        String password = array[1];
        userService.login(loginId,password,channel);
    }

    @CmdHandler(cmd = AccountCmd.REGISTER)
    public void register(Message message, Channel channel) {
        User user = channel.attr(UserService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (user != null) {
            NotificationHelper.notifyChannel(channel, "您已经登录账户 禁止注册");
            return;
        }
        String[] array = message.getContent().split("\\s+");
        if (array.length != REGISTER_PARAM_LENGTH) {
            NotificationHelper.notifyChannel(channel, "参数错误");
            return;
        }
        String loginId = array[0];
        String password = array[1];
        userService.register(loginId,password,channel);
    }

    @CmdHandler(cmd = AccountCmd.LOGOUT)
    public void logout(Message message,Channel channel){
        User user = channel.attr(UserService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (user == null) {
            NotificationHelper.notifyChannel(channel, "你根本没登录账户");
            return;
        }
        userService.logout(user);
    }


}
