package com.game.gameserver.module.friend.model;

import com.game.gameserver.module.friend.entity.FriendEntity;
import lombok.Data;

/**
 * 好友模型
 *
 * @author xuewenkang
 * @date 2020/7/13 17:00
 */
@Data
public class Friend extends FriendEntity {
    /** 是否在线 */
    private boolean online = false;

    public Friend(){

    }
}
