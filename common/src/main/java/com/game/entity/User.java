package com.game.gameserver.entity;


import lombok.Data;

/**
 * @author xuewenkang
 */
@Data
public class User {
    private Integer id;
    private Integer loginId;
    private String password;
}
