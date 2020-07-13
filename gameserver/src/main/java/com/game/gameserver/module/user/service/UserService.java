package com.game.gameserver.module.user.service;

import com.game.gameserver.module.user.dao.UserDbService;
import com.game.gameserver.module.user.entity.AccountEntity;
import com.game.gameserver.module.user.manager.UserManager;
import com.game.gameserver.module.user.module.User;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.util.GameUUID;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/5/24 23:45
 */
@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    public final static AttributeKey<User> ACCOUNT_ATTRIBUTE_KEY = AttributeKey.newInstance("ACCOUNT_ATTRIBUTE_KEY");


    @Autowired
    private UserManager userManager;

    @Autowired
    private UserDbService userDbService;

    /**
     * 登录
     *
     * @param loginId
     * @param password
     * @param channel
     * @return void
     */
    public void login(String loginId, String password, Channel channel) {
        User user = userManager.getAccount(loginId);
        // 从数据库中获取数据
        if (user == null) {
            AccountEntity accountEntity = userDbService.select(loginId);
            if (accountEntity == null || !accountEntity.getPassword().equals(password)) {
                NotificationHelper.notifyChannel(channel, "账户或密码错误");
                return;
            }
            // 生成账号信息
            user = new User();
            BeanUtils.copyProperties(accountEntity, user);
            // 设置连接
            user.setChannel(channel);
            channel.attr(ACCOUNT_ATTRIBUTE_KEY).set(user);
            // 放入缓存
            userManager.putAccount(loginId, user);
            NotificationHelper.notifyChannel(channel, "登录成功");
        } else {
            // 掉线通知
            NotificationHelper.notifyChannel(user.getChannel(), "您被挤了");
            user.getChannel().close();
            // 设置新连接
            user.setChannel(channel);
            channel.attr(ACCOUNT_ATTRIBUTE_KEY).set(user);
            NotificationHelper.notifyChannel(channel, "登录成功");
        }
    }

    /**
     * 注册
     *
     * @param loginId
     * @param password
     * @param channel
     * @return void
     */
    public void register(String loginId, String password, Channel channel) {
        // 判断账户是否存在
        int i = userDbService.count(loginId);
        if (i != 0) {
            NotificationHelper.notifyChannel(channel, "账户已经存在");
            return;
        }
        // 同步注册
        AccountEntity accountEntity = new AccountEntity(GameUUID.getInstance().generate(),loginId,password);
        i = userDbService.insert(accountEntity);
        if(i!=1){
            NotificationHelper.notifyChannel(channel, "注册失败");
            return;
        }
        NotificationHelper.notifyChannel(channel, "注册成功");
    }


    /**
     * 退出
     *
     * @param user
     * @return void
     */
    public void logout(User user) {
        // 移除缓存
        userManager.removeAccount(user.getLoginId());
        // 移除连接
        user.getChannel().attr(ACCOUNT_ATTRIBUTE_KEY).set(null);
        // 通知
        NotificationHelper.notifyChannel(user.getChannel(), "退出账户");
    }
}
