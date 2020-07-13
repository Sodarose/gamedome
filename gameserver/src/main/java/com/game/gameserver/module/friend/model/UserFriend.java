package com.game.gameserver.module.friend.model;

import lombok.Getter;

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
public class PlayerFriend {
    private final long playerId;
    private Map<Long,Friend> friendMap = new ConcurrentHashMap<>();

    /** 好友申请 */
    private Set<Long> applicant = new ConcurrentSkipListSet<>();

    public PlayerFriend(long playerId){
        this.playerId = playerId;
    }

    public void initialize(List<Friend> friendList){
        for(Friend friend:friendList){
            friendMap.put(friend.getFriendId(),friend);
        }
    }
}
