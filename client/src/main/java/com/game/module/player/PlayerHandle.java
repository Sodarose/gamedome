package com.game.module.player.handle;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.order.handle.CmdHandle;
import com.game.module.player.PlayerCmd;
import com.game.module.gui.WordPage;
import com.game.module.player.entity.SimplePlayerInfo;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.TransFromUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/19 17:25
 */
@Service
@ModuleHandler(module = ModuleKey.PLAYER_MODULE)
public class PlayerHandle extends BaseHandler {
    private final static Logger logger = LoggerFactory.getLogger(PlayerHandle.class);

    @Autowired
    private WordPage wordPage;
    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private CmdHandle cmdHandle;

    @CmdHandler(cmd = PlayerCmd.LIST_PLAYERS)
    public void receivePlayerList(Message message){
        try {
            PlayerProtocol.PlayerListRes res = PlayerProtocol.PlayerListRes.parseFrom(message.getData());
            List<SimplePlayerInfo> playerList = new ArrayList<>();
            for(PlayerProtocol.SimplePlayerInfo info:res.getPlayerInfoListList()){
                SimplePlayerInfo simplePlayerInfo = TransFromUtil.transFromSimplePlayerInfo(info);
                playerList.add(simplePlayerInfo);
            }
            gameContext.setPlayerList(playerList);
            wordPage.print(playerList);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    @CmdHandler(cmd = PlayerCmd.LOGIN_PLAYER)
    public void handleLoginPlayer(Message message){
        try {
            PlayerProtocol.LoginPlayerRes loginPlayerRes = PlayerProtocol.LoginPlayerRes.parseFrom(message.getData());
            if(loginPlayerRes.getCode()!=0){
                wordPage.print("登录失败:"+loginPlayerRes.getMsg());
                return;
            }
            cmdHandle.requestSceneInfo();
            cmdHandle.requestPlayerInfo();
            cmdHandle.requestBag();
            cmdHandle.requestEquip();
            cmdHandle.requestSkill();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 角色信息响应
     *
     * @param message
     * @return void
     */
    @CmdHandler(cmd = PlayerCmd.PLAYER_INFO_REQ)
    public void handlePlayerInfoRes(Message message){

    }

    /**
     * 角色数据同步
     *
     * @param message
     * @return void
     */
    @CmdHandler(cmd = PlayerCmd.SYNC_PLAYER_INFO)
    public void handleSyncPlayerInfo(Message message){

    }
}
