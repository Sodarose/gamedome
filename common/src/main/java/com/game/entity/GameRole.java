package com.game.entity;

import com.game.config.RoleStatus;
import com.game.pojo.Role;
import lombok.Data;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 20:35
 */
@Data
public class GameRole extends Role {

    /**
     * 角色状态
     * */
    private Integer status;

    public GameRole(Role role) {
        setId(role.getId());
        setName(role.getName());
        setPh(role.getPh());
        setMp(role.getMp());
        setPhyAttack(role.getPhyAttack());
        setPhyDefense(role.getPhyDefense());
        setMagicAttack(role.getMagicAttack());
        setMagicDefense(role.getMagicDefense());
        setUserId(role.getUserId());
        setMapId(role.getMapId());
        setStatus(RoleStatus.ROLE_LIVE);
    }

}
