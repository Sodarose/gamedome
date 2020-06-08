package com.game.gameserver.module.account.entity;

import lombok.Data;

/**
 * 账号信息
 * @author xuewenkang
 * @date 2020/5/18 14:44
 */
@Data
public class Account {
    private Integer id;
    private String loginId;
    private String password;
}
