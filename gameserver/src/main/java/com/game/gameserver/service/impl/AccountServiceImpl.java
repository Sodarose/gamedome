package com.game.gameserver.service.impl;

import com.game.config.MessageType;
import com.game.entity.User;
import com.game.gameserver.annotation.CmdHandler;
import com.game.gameserver.handler.MessageDispatcher;
import com.game.gameserver.mapper.AccountMapper;
import com.game.gameserver.service.AbstractAccountService;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author xuewenkang
 */
@Service
public class AccountServiceImpl extends AbstractAccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private AccountMapper accountMapper;

    @PostConstruct
    public void init(){
        messageDispatcher.registerService(this);
    }


    /**
     * description: 处理登录事件
     *
     * @param message 登录信息载体
     * @return com.game.protocol.Message
     */
    @CmdHandler(cmd = MessageType.USER_LOGIN_REQ)
    @Override
    public Message login(Message message) {
        logger.info("user login by message {}",message);
        try {
            Protocol.loginReq loginReq =  Protocol.loginReq.parseFrom(message.getData());
            User user = accountMapper.findUserByLoginId(loginReq.getLoginId());
            Protocol.loginRes res = null;
            if(user!=null&&user.getPassword().equals(loginReq.getPassword())){
               res = Protocol.loginRes.newBuilder()
                       .setCode(0)
                       .setMsg("success")
                       .setId(user.getId())
                       .setToken(UUID.randomUUID().toString())
                       .build();
            }else{
               res = Protocol.loginRes.newBuilder()
                       .setCode(404)
                       .setMsg("用户不存在或密码错误")
                       .build();
            }
            return createMessage(MessageType.USER_LOGIN_RES,res.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("login message parse error {}",message);
        }
        return null;
    }


    @CmdHandler(cmd = MessageType.USER_REGISTER_REQ)
    @Override
    public Message register(Message message) {
        logger.info("user register by message {}",message);
        try {
            Protocol.registerReq registerReq = Protocol.registerReq.parseFrom(message.getData());
            User user = accountMapper.findUserByLoginId(registerReq.getLoginId());
            Protocol.registerRes res = null;
            if(user!=null){
                res = Protocol.registerRes.newBuilder()
                        .setCode(1001)
                        .setMsg("该用户已存在")
                        .build();
            }else{
                user = new User(null,registerReq.getLoginId(),registerReq.getPassword());
                int index = accountMapper.insertUserByUser(user);
                if(index==1){
                    res = Protocol.registerRes.newBuilder()
                            .setCode(0)
                            .setMsg("success")
                            .build();
                }else{
                    res = Protocol.registerRes.newBuilder()
                            .setCode(1002)
                            .setMsg("注册失败")
                            .build();
                }
            }
            return createMessage(MessageType.USER_REGISTER_RES,res.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("register message parse error {}",message);
        }
        return null;
    }

    private Message createMessage(short cmd,byte[] bytes){
        int length = 4+2;
        if(bytes!=null){
            length += bytes.length;
        }
        return new Message(length,cmd,bytes);
    }

}
