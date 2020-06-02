package com.game.module.player.handle;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.player.PlayerCmd;
import com.game.module.player.entity.PlayerObject;
import com.game.module.player.model.Player;
import com.game.module.gui.WordPage;
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

    @CmdHandler(cmd = PlayerCmd.LIST_ROLES)
    public void receivePlayerList(Message message){
        try {
            PlayerProtocol.PlayerList playerList = PlayerProtocol.PlayerList.parseFrom(message.getData());
            List<Player> players = new ArrayList<>();
            for(PlayerProtocol.SimplePlayerInfo simplePlayerInfo:playerList.getPlayerInfoList()){
                Player player = new Player(simplePlayerInfo);
                players.add(player);
            }
            gameContext.setPlayerList(players);
            wordPage.print(players);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = PlayerCmd.LOGIN_ROLE)
    public void receiveLoginRole(Message message){
        try {
            PlayerProtocol.PlayerInfo playerInfo = PlayerProtocol.PlayerInfo.parseFrom(message.getData());
            PlayerObject playerObject = new PlayerObject(playerInfo);
            gameContext.setPlayerObject(playerObject);
            wordPage.print(playerObject);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = PlayerCmd.PLAYER_INFO)
    public void receivePlayerInfo(Message message){

    }

}
