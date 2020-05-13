package com.game.gameserver.game.mapper;

import com.game.pojo.Role;
import org.springframework.stereotype.Repository;

/**
 * 查询用户数据库mapper
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

    /**
     * description: 保存角色信息
     * @param role 角色数据
     * @return java.lang.Integer
     */
    Integer saveRoleByRole(Role role);
}
