package com.game.gameserver.net.modelhandler.player;

import com.game.gameserver.module.account.entity.Account;
import com.game.gameserver.module.account.service.AccountService;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
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

    @Autowired
    private PlayerService playerService;

    /**
     * 获得账户角色列表
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = PlayerCmd.QUERY_ROLE_LIST)
    public void handleQueryRoleListReq(Message message, Channel channel) {
        // 验证账号是否已经登录
        Account account = channel.attr(AccountService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (account == null) {
            return;
        }
        // 请求角色列表
        PlayerProtocol.QueryRoleListRes res = playerService.queryRoleList(account.getId());
        Message resMsg = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.QUERY_ROLE_LIST, res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    /**
     * 登录角色操作
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = PlayerCmd.LOGIN_ROLE)
    public void handleLoginRoleReq(Message message, Channel channel) {
        // 验证连接
        Account account = channel.attr(AccountService.ACCOUNT_ATTRIBUTE_KEY).get();
        if (account == null) {
            return;
        }
        // 是否重复登录
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player != null) {
            return;
        }
        try {
            PlayerProtocol.LoginRoleReq req = PlayerProtocol.LoginRoleReq.parseFrom(message.getData());
            PlayerProtocol.LoginRoleRes res = playerService.loginRole(req.getRoleId(), channel);
            Message resMsg = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.LOGIN_ROLE,
                    res.toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = PlayerCmd.PLAYER_INFO_REQ)
    public void handleQueryPlayerInfoReq(Message message, Channel channel) {
        // 角色是否已经登录
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        PlayerProtocol.QueryPlayerInfoRes res = playerService.queryPlayerInfo(player);
        Message resMsg = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.PLAYER_INFO_REQ,
                res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

}
