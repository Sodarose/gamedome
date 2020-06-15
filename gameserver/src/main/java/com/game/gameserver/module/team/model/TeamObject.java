package com.game.gameserver.module.team.model;

import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.GameUUID;
import com.game.gameserver.util.GenIdUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 组队对象
 *
 * @author xuewenkang
 * @date 2020/6/8 17:34
 */
public class TeamObject implements Unit {

    /** 组队ID */
    private Long id;
    /** 队长ID */
    private Long captainId;
    /** 队伍名称 */
    private String teamName;
    /** 队伍状态 */
    private int state;
    /** 队伍最大人数 */
    private int maxCount;
    /** 团队成员 */
    private List<Long> members;
    /** 团队频道 */
    private Long channelId;

    public TeamObject(PlayerObject playerObject,String teamName,int maxCount){
        this.id = GameUUID.getInstance().generate();
        this.teamName = teamName;
        this.captainId = playerObject.getPlayer().getId();
        this.state = TeamState.NULL_FULL;
        this.maxCount = maxCount;
        members = new ArrayList<>();
        members.add(playerObject.getPlayer().getId());
    }

    /**
     * 入队操作
     *
     * @param playerObject
     * @return void
     */
    public void entryTeam(PlayerObject playerObject){

    }

    /**
     * 出队操作
     *
     * @param playerObject
     * @return void
     */
    public void exitTeam(PlayerObject playerObject){
        members.remove(playerObject);
    }

    /**
     * 交换队长
     *
     * @param playerObject 队伍成员
     * @return void
     */
    public void changeCaptain(PlayerObject playerObject){
        if(!members.contains(playerObject)){
            return;
        }
        this.captainId = playerObject.getUnitId();
    }

    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

    public List<Long> getMembers(){
        return members;
    }

    public Long getCaptainId() {
        return captainId;
    }

    public int getMaxCount(){
        return maxCount;
    }

    public int getState(){
        return state;
    }

    public Long getId(){
        return id;
    }

    @Override
    public void update() {

    }
}
