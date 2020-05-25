package com.game.gameserver.net.modelhandler.account;

import com.game.gameserver.module.account.facade.AccountFacade;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.handler.CmdExecutor;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.AccountProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 账户模块处理器
 * @author xuewenkang
 * @date 2020/5/24 15:53
 */
@ModuleHandler(module = ModuleKey.ACCOUNT_MODULE)
@Component
public class AccountHandle extends BaseHandler {

    @Autowired
    private AccountFacade accountFacade;


    /**
     * 账号登录请求
     * @param message 消息体
     * @param channel 用户通道
     * @return void
     */
    @CmdHandler(cmd = AccountCmd.LOGIN)
    public void accountLogin(Message message, Channel channel)  {
        try {
            AccountProtocol.LoginReq loginReq = AccountProtocol.LoginReq.parseFrom(message.getData());
            AccountProtocol.LoginRes loginRes = accountFacade.doLogin(loginReq.getLoginId(),loginReq.getPassword(),channel);
            if(loginRes==null){
                return;
            }
            Message res = MessageUtil.createMessage(ModuleKey.ACCOUNT_MODULE,AccountCmd.LOGIN_RES,loginRes.toByteArray());
            channel.writeAndFlush(res);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = AccountCmd.REGISTER)
    public void accountRegister(Message message,Channel channel){

    }


}
