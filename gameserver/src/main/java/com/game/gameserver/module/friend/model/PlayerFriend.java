package com.game.gameserver.module.friend.model;

import com.game.gameserver.module.friend.entity.PlayerFriendEntity;
import com.game.gameserver.module.guild.model.Applicant;
import com.game.gameserver.module.player.entity.PlayerEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 好友容器
 *
 * @author xuewenkang
 * @date 2020/7/8 12:05
 */
@Getter
public class PlayerFriend extends PlayerFriendEntity {

    public PlayerFriend(Long playerId) {
        super(playerId);
    }
}
