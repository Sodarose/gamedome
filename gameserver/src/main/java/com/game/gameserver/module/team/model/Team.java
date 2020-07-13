package com.game.gameserver.module.team.model;

import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 组队对象
 *
 * @author xuewenkang
 * @date 2020/6/8 17:34
 */
@Data
public class Team {

    /**
     * id
     */
    private long id;
    /**
     * name
     */
    private String teamName;
    /**
     * 队长Id
     */
    private long captainId;
    /**
     * 容量
     */
    private int capacity;
    /**
     * 团队成员
     */
    private Map<Long, Player> memberMap = new ConcurrentHashMap<>();
    /**
     * 申请人列表
     */
    private List<Long> apply = new CopyOnWriteArrayList<>();
    /**
     * 邀请人列表
     */
    private List<Long> invite = new CopyOnWriteArrayList<>();

    public Team() {

    }

    public Team(int id, String teamName, int captainId, int capacity) {
        this.id = id;
        this.teamName = teamName;
        this.captainId = captainId;
        this.capacity = capacity;
    }

    public boolean hasScape() {
        return memberMap.size() < capacity;
    }
}
