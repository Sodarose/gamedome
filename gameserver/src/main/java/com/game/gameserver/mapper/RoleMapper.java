package com.game.gameserver.mapper;

import com.game.pojo.Role;
import org.springframework.stereotype.Repository;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 20:21
 */
@Repository
public interface RoleMapper {
    /**
     * description: 根据用户ID查找用户角色
     * @param userId
     * @return com.game.pojo.Role
     */
    Role findRoleByUserId(Integer userId);
}
