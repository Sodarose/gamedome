package com.game.gameserver.game.account;

import lombok.Data;

import java.time.LocalDate;

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
    private LocalDate createTime;
    private LocalDate updateTime;
}
