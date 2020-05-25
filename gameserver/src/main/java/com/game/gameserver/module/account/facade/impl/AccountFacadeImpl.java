package com.game.gameserver.module.account.facade.impl;

import com.game.gameserver.module.account.dao.AccountMapper;
import com.game.gameserver.module.account.model.Account;
import com.game.gameserver.module.account.facade.AccountFacade;
import com.game.gameserver.module.player.facade.PlayerFacade;
import com.game.gameserver.module.player.model.Role;
import com.game.gameserver.util.TransFromUtil;
import com.game.protocol.AccountProtocol;
import com.game.protocol.CodeType;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author xuewenkang
 * @date 2020/5/24 23:45
 */
@Service
public class AccountFacadeImpl implements AccountFacade {
    private static final Logger logger = LoggerFactory.getLogger(AccountFacadeImpl.class);


    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private PlayerFacade playerFacade;

    /**
     * 登录接口
     * @param loginId 账号
     * @param password 密码
     * @return com.game.protocol.Account.LoginRes
     */
    @Override
    public AccountProtocol.LoginRes doLogin(String loginId, String password, Channel channel) {
        AccountProtocol.LoginRes.Builder builder = AccountProtocol.LoginRes.newBuilder();
        Account account = accountMapper.findUserByLoginId(loginId);
        // 账户不存在
        if(account==null){
            builder.setCode(CodeType.USER_NULL_EXISTS);
            return builder.build();
        }
        // 比较密码是否正确
        if(!account.getPassword().equals(password)){
            builder.setCode(CodeType.USER_ACCOUNT_ERROR);
            return builder.build();
        }
        builder.setCode(CodeType.LOGIN_SUCCESS);
        builder.setId(account.getId());
        // 模拟Token
        builder.setToken(UUID.randomUUID().toString());

        // 获取用户角色列表
        List<Role> roles = playerFacade.getPlayListByAccountId(account.getId());
        for(Role role : roles){
            builder.addRoles(TransFromUtil.roleTransFromPlayerProtocolRoleInfo(role));
        }
        channel.attr(ACCOUNT_ATTRIBUTE_KEY).compareAndSet(null,account);
        return builder.build();
    }

}
