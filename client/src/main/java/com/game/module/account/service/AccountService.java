package com.game.module.account.service;

import com.game.context.ClientGameContext;
import com.game.module.account.AccountCmd;
import com.game.module.account.entity.Account;
import com.game.module.gui.GameClientPage;
import com.game.protocol.AccountProtocol;
import com.game.protocol.CodeType;
import com.game.protocol.Message;
import com.game.task.MessageDispatcher;
import com.game.task.annotation.CmdHandler;
import com.game.util.MessageUtil;
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
    private GameClientPage page;
    @Autowired
    private ClientGameContext gameContext;


    /**
     * 登录
     * @param loginId 登录账户
     * @param password 密码
     * @return void
     */
    public void login(String loginId,String password){
        AccountProtocol.LoginReq.Builder builder = AccountProtocol.LoginReq.newBuilder();
        builder.setLoginId(loginId);
        builder.setPassword(password);
        Message message = MessageUtil.createMessage((short)1001,AccountCmd.LOGIN,builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    /**
     * 处理登录返回
     * @param message 返回的消息
     * @return void
     */
    @CmdHandler(cmd = AccountCmd.LOGIN_RES)
    public void loginHandle(Message message){
        try {
            AccountProtocol.LoginRes loginRes = AccountProtocol.LoginRes.parseFrom(message.getData());
            if(loginRes.getCode()!= CodeType.LOGIN_SUCCESS){
                page.showMessageDialog("登录失败!");
                return;
            }
            // 设置客户端账户
            Account account = new Account();
            account.setId(loginRes.getId());
            account.setToken(loginRes.getToken());
            gameContext.setAccount(account);

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

    }

    /**
     * 注册消息结果处理
     * @param message 服务端返回的消息
     * @return void
     */
    public void registerHandle(Message message){

    }


    @PostConstruct
    public void init(){
        dispatcher.registerService(this);
    }

}
