package com.game.module.player.handle;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.player.PlayerCmd;
import com.game.module.player.entity.Player;
import com.game.module.player.model.Role;
import com.game.module.gui.WordPage;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.task.MessageDispatcher;
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
    public void receiveRoleList(Message message){
        try {
            PlayerProtocol.RoleInfoList roleInfoList = PlayerProtocol.RoleInfoList.parseFrom(message.getData());
            if(roleInfoList.getRolesList().size()==0){
                logger.info("该账户无角色 请创建角色");
                wordPage.print("亲！账户目前没有任何角色呢！请创建角色哟！");
                return;
            }
            List<Role> roles = new ArrayList<>();
            for(PlayerProtocol.RoleInfo roleInfo:roleInfoList.getRolesList()){
                roles.add(TransFromUtil.playerProtocolRoleInfoTransFromRole(roleInfo));
            }
            gameContext.setRoleList(roles);
            wordPage.print(roles);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = PlayerCmd.LOGIN_ROLE)
    public void receiveLoginRole(Message message){
        try {
            PlayerProtocol.PlayerInfo playerInfo = PlayerProtocol.PlayerInfo.parseFrom(message.getData());
            Player player = TransFromUtil.playerProtocolPlayerInfoTransFromPlayer(playerInfo);
            gameContext.setPlayer(player);
            wordPage.print(player);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = PlayerCmd.PLAYER_INFO)
    public void receivePlayerInfo(Message message){

    }
}
