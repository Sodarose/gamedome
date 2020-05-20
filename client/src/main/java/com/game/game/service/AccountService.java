package com.game.game.service;

import com.game.game.context.ClientSpringContext;
import com.game.game.gui.GameClientPage;
import com.game.protocol.Message;
import com.game.protocol.MessageType;
import com.game.protocol.Protocol;
import com.game.task.MessageDispatcher;
import com.game.task.annotation.CmdHandler;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author xuewenkang
 * @date 2020/5/19 14:42
 */
@Service
public class AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private MessageDispatcher dispatcher;

    @Autowired
    private ClientSpringContext clientSpringContext;

    @Autowired
    private GameClientPage page;


    /**
     * 登录
     * @param loginId 登录账户
     * @param password 密码
     * @return void
     */
    public void login(String loginId,String password){
        Protocol.LoginReq.Builder builder = Protocol.LoginReq.newBuilder();
        builder.setLoginId(loginId);
        builder.setPassword(password);
        Message message = MessageUtil.createMessage(MessageType.USER_LOGIN_REQ,builder.build().toByteArray());
        clientSpringContext.getChannel().writeAndFlush(message);
    }

    /**
     * 处理登录返回
     * @param message 返回的消息
     * @return void
     */
    @CmdHandler(cmd = MessageType.USER_LOGIN_RES)
    public void loginHandle(Message message){
        try {
            Protocol.LoginRes loginRes = Protocol.LoginRes.parseFrom(message.getData());
            if(loginRes.getCode()!=0){
                page.showMessageDialog("登录失败!");
                return;
            }
            page.showMessageDialog("登录成功!");
            page.showMainPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账号注册
     * @param loginId 登录ID
     * @param password 登录密码
     * @return void
     */
    public void register(String loginId,String password){
        Protocol.RegisterReq.Builder builder = Protocol.RegisterReq.newBuilder();
        builder.setLoginId(loginId);
        builder.setPassword(password);
        Message message = MessageUtil.createMessage(MessageType.USER_LOGIN_REQ,builder.build().toByteArray());
        clientSpringContext.getChannel().writeAndFlush(message);
    }

    /**
     * 注册消息结果处理
     * @param message 服务端返回的消息
     * @return void
     */
    @CmdHandler(cmd = MessageType.USER_REGISTER_RES)
    public void registerHandle(Message message){
        try {
            Protocol.RegisterRes registerRes = Protocol.RegisterRes.parseFrom(message.getData());
            if(registerRes.getCode()!=0){
                page.showMessageDialog(registerRes.getMsg());
                return;
            }
            page.showMessageDialog("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PostConstruct
    public void init(){
        dispatcher.registerService(this);
    }

}
