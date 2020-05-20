package com.game.gameserver.game.account;

import com.game.gameserver.game.player.PlayerService;
import com.game.gameserver.task.MessageDispatcher;
import com.game.gameserver.task.annotation.CmdHandler;
import com.game.protocol.Message;
import com.game.protocol.MessageType;
import com.game.protocol.Protocol;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author xuewenkang
 * @date 2020/5/18 17:56
 */
@Service
public class AccountService {

    @Autowired
    private AccountMapper mapper;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MessageDispatcher dispatcher;

    /**
     * 账号登录
     * @param message 具体消息
     * @param channel channel 通道
     * @return void
     */
    @CmdHandler(cmd = MessageType.USER_LOGIN_REQ)
    public void login(Message message, Channel channel){
        try {
            Protocol.LoginReq loginReq = Protocol.LoginReq.parseFrom(message.getData());
            Protocol.LoginRes.Builder builder = Protocol.LoginRes.newBuilder();
            Account account = mapper.findUserByLoginId(loginReq.getLoginId());
            // 账户不存在或者密码错误
            if(account==null||!account.getPassword().equals(loginReq.getPassword())){
                builder.setCode(404);
                builder.setMsg("账户或密码错误");
                channel.writeAndFlush(MessageUtil.createMessage(MessageType.USER_LOGIN_RES,
                        builder.build().toByteArray()));
                return;
            }
            builder.setId(account.getId());
            builder.setCode(0);
            builder.setMsg("登录成功");
            // 暂时只模拟Token
            builder.setToken(UUID.randomUUID().toString());
            // 读取角色信息
            playerService.loadPlayer(account,channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账户注册
     * @param message 消息
     * @param channel channel 通道
     * @return void
     */
    @CmdHandler(cmd = MessageType.USER_REGISTER_REQ)
    public void register(Message message,Channel channel){

    }


    @PostConstruct
    public void init(){
        dispatcher.registerService(this);
    }

}
