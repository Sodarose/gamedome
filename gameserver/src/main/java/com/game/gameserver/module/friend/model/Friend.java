package com.game.gameserver.module.friend.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 好友模型
 *
 * @author xuewenkang
 * @date 2020/7/13 17:00
 */
@Data
public class Friend implements Serializable {
    private long friendId;
    private String name;
    private int friendType;

    public Friend(){

    }
}
