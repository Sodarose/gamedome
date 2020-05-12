package com.game.gameserver.manager;

import com.game.entity.GameRole;
import com.game.gameserver.mapper.RoleMapper;
import com.game.pojo.Role;
import com.game.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 16:51
 *
 * 玩家角色管理器
 */
@Component
public class RoleManager {

    private final static Logger logger = LoggerFactory.getLogger(RoleMapper.class);

    /**
     * KEY:用户ID VALUE:角色
     * */
    private final ConcurrentHashMap<Integer, GameRole> roles = new ConcurrentHashMap<>(16);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SceneManager sceneManager;

    /***
     * description: 读取用户角色 并投入场景
     *
     * @param user 用户
     * @return void
     */
    public void loadRole(User user){
        logger.info("加载用户{}的角色",user.getId());
        Role role = roleMapper.findRoleByUserId(user.getId());
        if(role==null){
            logger.warn("用户{}没有创建角色，请先创建!",user.getId());
            return;
        }
        GameRole gameRole = new GameRole(role);
        roles.put(user.getId(),gameRole);
        sceneManager.intoScene(gameRole);
    }
}
