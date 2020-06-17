package com.game.module.account.entity;

import lombok.Data;

/**
 * 记录玩家账户信息 以便重连
 * @author xuewenkang
 * @date 2020/5/19 15:26
 */
@Data
public class Account {
    private Integer id;
    private String token;
}
