package com.game.gameserver.net.modelhandler.player;

import com.game.gameserver.module.account.manager.AccountManager;
import com.game.gameserver.module.account.model.Account;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.module.player.facade.PlayerFacade;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.util.TransFromUtil;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
     * 返回账户的角色列表 角色列表
     * */
    @CmdHandler(cmd = PlayerCmd.LIST_ROLES)
    public void getRoleList(Message message, Channel channel){
        Account account = channel.attr(AccountManager.ACCOUNT_ATTRIBUTE_KEY).get();
        if(account==null){
            return;
        }
        // 得到角色列表
        List<PlayerProtocol.SimplePlayerInfo> playerInfos =  playerService.getPlayerList(account.getId());
        if(playerInfos.size()==0){
            return;
        }
        PlayerProtocol.PlayerList.Builder builder = PlayerProtocol.PlayerList.newBuilder();
        builder.addAllPlayerInfo(playerInfos);
        Message res = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE,PlayerCmd.LIST_ROLES,builder.build().toByteArray());
        channel.writeAndFlush(res);
    }

    /**
     * 登录目标角色
     * @param message 信息
     * @param channel 通道
     * @return void
     */
    @CmdHandler(cmd = PlayerCmd.LOGIN_ROLE)
    public void loginRole(Message message,Channel channel){
        try {
            PlayerProtocol.LoginRole loginRole = PlayerProtocol
                    .LoginRole.parseFrom(message.getData());
            PlayerProtocol.PlayerInfo playerInfo = playerFacade
                    .loginRoleByRoleId(loginRole.getId(),channel);
            Message msg = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE,PlayerCmd.LOGIN_ROLE,
                    playerInfo.toByteArray());
            channel.writeAndFlush(msg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = PlayerCmd.PLAYER_INFO)
    public void getPlayerInfo(Message message,Channel channel){
        PlayerObject playerObject = channel.attr(PlayerFacade.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        PlayerProtocol.PlayerInfo playerInfo = TransFromUtil
                .playerTransFromPlayerProtocolPlayerInfo(playerObject);
        Message msg = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE,PlayerCmd.LOGIN_ROLE,
                playerInfo.toByteArray());
        channel.writeAndFlush(msg);
    }

}
