package com.game.gameserver.module.team.model;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.GameUUID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 组队对象
 *
 * @author xuewenkang
 * @date 2020/6/8 17:34
 */
public class TeamObject {
    
    /**
     * 组队ID
     */
    private final Long id;
    /**
     * 队长ID
     */
    private Long captainId;
    /**
     * 队伍名称
     */
    private String teamName;
    /**
     * 队伍是否已满
     */
    private boolean full;
    /**
     * 队伍所在的副本
     * */
    private Long instanceId;
    /**
     * 队伍当前人数
     */
    private int currNum;
    /**
     * 队伍最大人数
     */
    private final int maxNum;
    /**
     * 团队成员
     */
    private final List<Long> members;
    /**
     * 队伍聊天频道
     */
    private Long channelId;

    public TeamObject(PlayerObject player, String teamName, int maxNum) {
        this.id = GameUUID.getInstance().generate();
        this.teamName = teamName;
        this.captainId = player.getPlayer().getId();
        this.full = false;
        this.maxNum = maxNum;
        members = new ArrayList<>();
        members.add(player.getPlayer().getId());
        currNum = 1;
    }

    /**
     * 入队操作
     *
     * @param playerId
     * @return void
     */
    public void entryTeam(Long playerId) {
        synchronized (members) {
            if (members.contains(playerId)) {
                return;
            }
            members.add(playerId);
            currNum += 1;
        }
    }

    /**
     * 出队操作
     *
     * @param playerId
     * @return void
     */
    public boolean exitTeam(Long playerId) {
        synchronized (members) {
            if (!members.contains(playerId)) {
                return false;
            }
            members.remove(playerId);
            currNum += 1;
            if (currNum == maxNum) {

            }
            return true;
        }
    }

    /**
     * 交换队长
     *
     * @param playerId Id
     * @return void
     */
    public void changeCaptain(Long playerId) {
        if (!members.contains(playerId)) {
            return;
        }
        this.captainId = playerId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Long> getMembers() {
        return members;
    }

    public Long getCaptainId() {
        return captainId;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public boolean isFull() {
        return full;
    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void update() {

    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public void setInstanceId(Long instanceId){
        this.instanceId = instanceId;
    }

    public Long getInstanceId(){
        return instanceId;
    }

}
