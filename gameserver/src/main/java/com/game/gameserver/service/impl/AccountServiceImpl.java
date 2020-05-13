package com.game.gameserver.service.impl;

import com.game.config.MessageType;
import com.game.gameserver.manager.RoleManager;
import com.game.pojo.User;
import com.game.gameserver.annotation.CmdHandler;
import com.game.gameserver.context.GameContext;
import com.game.gameserver.handler.MessageDispatcher;
import com.game.gameserver.mapper.AccountMapper;
import com.game.gameserver.service.AbstractAccountService;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.ulit.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
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

    @Autowired
    private GameContext gameContext;

    @Autowired
    private RoleManager roleManager;

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
    public void login(Message message, Channel channel) {
        logger.info("user login by message {}",message);
        try {
            Protocol.LoginReq loginReq =  Protocol.LoginReq.parseFrom(message.getData());
            User user = accountMapper.findUserByLoginId(loginReq.getLoginId());
            Protocol.LoginRes res = null;
            if(user!=null&&user.getPassword().equals(loginReq.getPassword())){
               res = Protocol.LoginRes.newBuilder()
                       .setCode(0)
                       .setMsg("success")
                       .setId(user.getId())
                       .setToken(UUID.randomUUID().toString())
                       .build();
               Attribute<User> attr = channel.attr(GameContext.CHANNEL_USER_KEY);
               attr.compareAndSet(null,user);
               gameContext.addUserGroup(channel);
               roleManager.loadRole(user);
            }else{
               res = Protocol.LoginRes.newBuilder()
                       .setCode(404)
                       .setMsg("用户不存在或密码错误")
                       .build();
            }
            channel.writeAndFlush(MessageUtil.createMessage(MessageType.USER_LOGIN_RES,res.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("login message parse error {}",message);
        }
    }

    @CmdHandler(cmd = MessageType.USER_REGISTER_REQ)
    @Override
    public void register(Message message,Channel channel) {
        logger.info("user register by message {}",message);
        try {
            Protocol.RegisterReq registerReq = Protocol.RegisterReq.parseFrom(message.getData());
            User user = accountMapper.findUserByLoginId(registerReq.getLoginId());
            Protocol.RegisterRes res = null;
            if(user!=null){
                res = Protocol.RegisterRes.newBuilder()
                        .setCode(1001)
                        .setMsg("该用户已存在")
                        .build();
            }else{
                user = new User(null,registerReq.getLoginId(),registerReq.getPassword());
                int index = accountMapper.insertUserByUser(user);
                if(index==1){
                    res = Protocol.RegisterRes.newBuilder()
                            .setCode(0)
                            .setMsg("success")
                            .build();
                }else{
                    res = Protocol.RegisterRes.newBuilder()
                            .setCode(1002)
                            .setMsg("注册失败")
                            .build();
                }
            }
            Message msgRes = MessageUtil.createMessage(MessageType.USER_REGISTER_RES,res.toByteArray());
            channel.writeAndFlush(msgRes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("register message parse error {}",message);
        }
    }

}
