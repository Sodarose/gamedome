package com.game.util;
import com.game.module.game.model.Role;
import com.game.protocol.PlayerProtocol;

/**
 * 转换工具
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class TransFromUtil {
    /*public static PlayerProtocol.RoleInfo roleTransFromPlayerProtocolRoleInfo(Role role){
        PlayerProtocol.RoleInfo.Builder builder = PlayerProtocol.RoleInfo.newBuilder();
        builder.setId(role.getId());
        builder.setName(role.getName());
        builder.setLevel(role.getLevel());
        builder.setCareer(role.getCareer());
        return builder.build();
    }
    */

    public static Role playerProtocolRoleInfoTransFromRole(PlayerProtocol.RoleInfo roleInfo){
        Role role = new Role();
        role.setId(roleInfo.getId());
        role.setName(roleInfo.getName());
        role.setLevel(roleInfo.getLevel());
        role.setCareer(roleInfo.getCareer());
        return role;
    }
}
