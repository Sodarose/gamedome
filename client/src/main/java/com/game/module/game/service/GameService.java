package com.game.module.game.service;

import com.game.context.ClientGameContext;
import com.game.module.game.GameCmd;
import com.game.module.game.model.Role;
import com.game.module.gui.WordPage;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.task.MessageDispatcher;
import com.game.task.annotation.CmdHandler;
import com.game.util.TransFromUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/19 17:25
 */
@Service
public class GameService {
    private final static Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private MessageDispatcher dispatcher;
    @Autowired
    private WordPage wordPage;
    @Autowired
    private ClientGameContext gameContext;

    @CmdHandler(cmd = GameCmd.LIST_ROLES)
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

    @PostConstruct
    public void init(){
        dispatcher.registerService(this);
    }

}
