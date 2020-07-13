package com.game.gameserver.module.friend.entity;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/8 12:05
 */
@Data
public class FriendEntity {
    private long id;
    private long friendId;
    private int friendType;
}
