package com.game.module.player;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.order.CmdHandle;
import com.game.module.gui.WordPage;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.game.util.TransFromUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/19 17:25
 */
@Service
@ModuleHandler(module = ModuleKey.PLAYER_MODULE)
public class PlayerHandle extends BaseHandler {
    private final static Logger logger = LoggerFactory.getLogger(PlayerHandle.class);

    private final Map<String, SimplePlayerInfo> roles = new HashMap<>();
    private PlayerProtocol.PlayerInfo playerInfo;
    private boolean print = false;

    @Autowired
    private WordPage wordPage;
    @Autowired
    private ClientGameContext gameContext;

    public void showPlayerInfo() {
        requestPlayerInfo();
    }

    public void requestPlayerInfo() {
        Message message = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.PLAYER_INFO_REQ, null);
        gameContext.getChannel().writeAndFlush(message);
    }

    public void loginRole(String roleName) {
        SimplePlayerInfo simplePlayerInfo = roles.get(roleName);
        if (simplePlayerInfo == null) {
            wordPage.print("没有该角色");
            return;
        }
        PlayerProtocol.LoginPlayerReq.Builder builder = PlayerProtocol.LoginPlayerReq.newBuilder();
        builder.setPlayerId(simplePlayerInfo.getId());
        Message message = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.LOGIN_PLAYER,
                builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }


    @CmdHandler(cmd = PlayerCmd.LIST_PLAYERS)
    public void receivePlayerList(Message message) {
        roles.clear();
        try {
            PlayerProtocol.PlayerListRes res = PlayerProtocol.PlayerListRes.parseFrom(message.getData());
            for (PlayerProtocol.SimplePlayerInfo info : res.getPlayerInfoListList()) {
                SimplePlayerInfo simplePlayerInfo = TransFromUtil.transFromSimplePlayerInfo(info);
                roles.put(simplePlayerInfo.getName(), simplePlayerInfo);
            }
            wordPage.clean();
            wordPage.printRoles(roles);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    @CmdHandler(cmd = PlayerCmd.LOGIN_PLAYER)
    public void receiveLoginPlayerRes(Message message) {
        try {
            PlayerProtocol.LoginPlayerRes loginPlayerRes = PlayerProtocol.LoginPlayerRes.parseFrom(message.getData());
            if (loginPlayerRes.getCode() != 0) {
                wordPage.print("登录失败:" + loginPlayerRes.getMsg());
                return;
            }
            wordPage.print("登录成功");
            this.playerInfo = loginPlayerRes.getPlayerInfo();
            wordPage.printPlayerInfo(playerInfo);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = PlayerCmd.PLAYER_INFO_REQ)
    public void receivePlayerInfo(Message message) {
        try {
            PlayerProtocol.PlayerInfo info = PlayerProtocol.PlayerInfo.parseFrom(message.getData());
            this.playerInfo = info;
            wordPage.clean();
            wordPage.printPlayerInfo(playerInfo);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    @CmdHandler(cmd = PlayerCmd.SYNC_PLAYER_INFO)
    public void syncPlayerInfo(Message message) {
        try {
            PlayerProtocol.PlayerInfo info = PlayerProtocol.PlayerInfo.parseFrom(message.getData());
            this.playerInfo = info;
            if (print) {
                wordPage.clean();
                wordPage.printPlayerInfo(playerInfo);
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = PlayerCmd.SYNC_PLAYER_BATTLE)
    public void syncPlayerBattle(Message message) {

    }

    public PlayerProtocol.PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
}
