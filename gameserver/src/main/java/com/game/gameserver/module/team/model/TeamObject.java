package com.game.gameserver.module.team.model;

import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.GameUUID;

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

    public TeamObject(PlayerObject player, String teamName, int maxCount){
        this.id = GameUUID.getInstance().generate();
        this.teamName = teamName;
        this.captainId = player.getPlayer().getId();
        this.state = TeamState.NULL_FULL;
        this.maxCount = maxCount;
        members = new ArrayList<>();
        members.add(player.getPlayer().getId());
    }

    /**
     * 入队操作
     *
     * @param player
     * @return void
     */
    public void entryTeam(Player player){

    }

    /**
     * 出队操作
     *
     * @param player
     * @return void
     */
    public void exitTeam(Player player){
        members.remove(player);
    }

    /**
     * 交换队长
     *
     * @param player 队伍成员
     * @return void
     */
    public void changeCaptain(PlayerObject playerObject){
        if(!members.contains(playerObject.getPlayer().getId())){
            return;
        }
        this.captainId = playerObject.getPlayer().getId();
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
