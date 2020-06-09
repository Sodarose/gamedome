package com.game.gameserver.module.player.model;

import com.game.gameserver.common.entity.Unit;

/**
 * 玩家模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:16
 */
public class PlayerObject implements Unit {

    /** 玩家Id */
    private int id;
    
    /** 当前所在的组队 队伍ID */
    private Integer teamId;

    @Override
    public void update() {

    }

    public int getId(){
        return id;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamId(){
        return teamId;
    }
}
