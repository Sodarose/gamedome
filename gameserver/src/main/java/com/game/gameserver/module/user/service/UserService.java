package com.game.gameserver.module.user.service;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.player.dao.PlayerDbService;
import com.game.gameserver.module.user.dao.UserDbService;
import com.game.gameserver.module.player.entity.Role;
import com.game.gameserver.module.user.entity.UserEntity;
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

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/24 23:45
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public final static AttributeKey<User> ACCOUNT_ATTRIBUTE_KEY = AttributeKey.newInstance("ACCOUNT_ATTRIBUTE_KEY");


    @Autowired
    private UserManager userManager;

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private PlayerDbService playerDbService;

    /**
     * 登录
     *
     * @param loginId
     * @param password
     * @param channel
     * @return void
     */
    public void login(String loginId, String password, Channel channel) {
        User user = userManager.getUser(loginId);
        // 从数据库中获取数据
        if (user == null) {
            UserEntity userEntity = userDbService.select(loginId);
            if (userEntity == null || !userEntity.getPassword().equals(password)) {
                NotificationHelper.notifyChannel(channel, "账户或密码错误");
                return;
            }
            // 生成账号信息
            user = new User();
            BeanUtils.copyProperties(userEntity, user);
            // 查询用户角色拥有的Id列表
            List<Long> roles = playerDbService.selectRoleIds(user.getId());
            user.getRoles().addAll(roles);
            // 放入缓存
            userManager.putUser(loginId, user);
            // 设置连接
            user.setChannel(channel);
            channel.attr(ACCOUNT_ATTRIBUTE_KEY).set(user);
            NotificationHelper.notifyChannel(channel, "登录成功");
        } else {
            // 掉线通知
            NotificationHelper.notifyChannel(user.getChannel(), "您被挤了");
            // 移除缓存
            userManager.removeUser(loginId);
            // 关闭连接
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
        UserEntity userEntity = new UserEntity(GameUUID.getInstance().generate(),loginId,password);
        i = userDbService.insert(userEntity);
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
        userManager.removeUser(user.getLoginId());
        // 移除连接
        user.getChannel().attr(ACCOUNT_ATTRIBUTE_KEY).set(null);
        // 通知
        NotificationHelper.notifyChannel(user.getChannel(), "退出账户");
    }
}
