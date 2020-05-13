package com.game.gameserver.game.manager;

import com.game.entity.GameRole;
import com.game.entity.Scene;
import com.game.gameserver.game.context.GameContext;
import com.game.gameserver.game.mapper.RoleMapper;
import com.game.pojo.Role;
import com.game.pojo.User;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 游戏管理器
 * @author: xuewenkang
 * @date: 2020/5/13 15:16
 */
@Component
public class GameManager {

    private final static Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private GameContext gameContext;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * description: 加载用户 将用户放入context中
     *
     * @param user 用户数据
     * @return void
     */
    public void loadUser(User user, Channel channel){
        Attribute<User> attr = channel.attr(GameContext.CHANNEL_USER_KEY);
        attr.compareAndSet(null,user);
        gameContext.getUserGroup().add(channel);
        gameContext.getUserChannelIdMap().put(user.getId(),channel.id());
        loadUserRole(user);
    }

    /**
     * description: 加载用户角色 投入场景
     *
     * @param user 用户
     * @return void
     */
    private void loadUserRole(User user){
        logger.info("加载用户{}的角色",user.getId());
        Role role = roleMapper.findRoleByUserId(user.getId());
        if(role==null){
            logger.warn("用户{}没有创建角色，请先创建!",user.getId());
            return;
        }
        GameRole gameRole = new GameRole(role);
        gameContext.getRoles().put(user.getId(),gameRole);
        Scene scene = gameContext.getScenes().get(gameRole.getMapId());
        scene.getRoles().put(gameRole.getId(),gameRole);
    }
}
