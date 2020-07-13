package com.game.gameserver.module.user.entity;

import lombok.Data;

/**
 * 账号信息
 *
 * @author xuewenkang
 * @date 2020/5/18 14:44
 */
@Data
public class UserEntity {
    private Long id;
    private String loginId;
    private String password;

    public UserEntity(){

    }

    public UserEntity(long id, String loginId, String password) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
    }
}
