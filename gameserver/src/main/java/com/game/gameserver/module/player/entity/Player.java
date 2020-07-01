package com.game.gameserver.module.player.entity;

import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.entity.UnitType;
import com.game.gameserver.module.ai.fsm.StateMachine;
import com.game.gameserver.module.ai.state.player.PlayerState;
import com.game.gameserver.module.buffer.entity.Buffer;
import com.game.gameserver.module.fighter.type.FighterModeEnum;
import io.netty.channel.Channel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家实体
 *
 * @author xuewenkang
 * @date 2020/6/8 16:16
 */

public class Player implements Serializable, Unit {

    /** id */
    private Long id;
    /** 姓名*/
    private String name;
    /** 等级 */
    private Integer level;
    /** 职业*/
    private Integer careerId;
    /** 金币数量*/
    private Integer golds;
    /** 场景Id*/
    private Integer sceneId;
    /** 角色经验*/
    private int expr;
    /** 背包容量 */
    private Integer bagCapacity = 36;
    /** 仓库容量*/
    private Integer warehouse;
    /** 账号ID*/
    private Integer userId;
    /** 创建时间*/
    private LocalDate createTime;
    /** 更新时间*/
    private LocalDate updateTime;

    /** 角色属性 */
    private int hp;
    private int currHp;
    private int mp;
    private int currMp;
    private int attack;
    private int defense;

    /** 不存档数据 */
    /** 角色连接信息*/
    private Channel channel;
    /** 当前所在的组队 队伍ID*/
    private Long teamId;
    /** 当前玩家所在的副本*/
    private Long instanceId;
    /** 聊天频道  key 频道类型  value 频道Id*/
    private final Map<Integer, Long> playerChannelMap = new ConcurrentHashMap<>();
    /** 角色状态机*/
    private StateMachine<Player, PlayerState> stateMachine;
    /** 临时数据*/
    private Map<String, Object> tempData = new ConcurrentHashMap<>();
    /** 战斗模式*/
    private FighterModeEnum fighterModeEnum = FighterModeEnum.PEACE;
    /** 攻击的单位*/
    private volatile Unit attackTarget;

    public Player() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCareerId() {
        return careerId;
    }

    public void setCareerId(Integer careerId) {
        this.careerId = careerId;
    }

    public Integer getGolds() {
        return golds;
    }

    public void setGolds(Integer golds) {
        this.golds = golds;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getExpr() {
        return expr;
    }

    public void setBagCapacity(Integer bagCapacity) {
        this.bagCapacity = bagCapacity;
    }

    public Integer getBagCapacity() {
        return bagCapacity;
    }

    public void setWarehouse(Integer warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getWarehouse() {
        return warehouse;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMp() {
        return mp;
    }

    public void setCurrHp(int currHp) {
        this.currHp = currHp;
    }

    public int getCurrHp() {
        return currHp;
    }

    public void setCurrMp(int currMp) {
        this.currMp = currMp;
    }

    public int getCurrMp() {
        return currMp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public Map<Integer, Long> getPlayerChannelMap() {
        return playerChannelMap;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public void changeGlobalState(PlayerState playerState) {
        if (stateMachine != null) {
            stateMachine.changeGlobalState(playerState);
        }
    }

    public void changeState(PlayerState playerState) {
        if (stateMachine != null) {
            stateMachine.changeState(playerState);
        }
    }

    public Map<String, Object> getTempData() {
        return tempData;
    }

    public FighterModeEnum getFighterModeEnum() {
        return fighterModeEnum;
    }

    public void setFighterModeEnum(FighterModeEnum modeEnum) {
        this.fighterModeEnum = modeEnum;
    }


    public PlayerState getGlobalState() {
        if (stateMachine == null) {
            return null;
        }
        return stateMachine.getGlobalState();
    }

    public PlayerState getCurrState() {
        if (stateMachine == null) {
            return null;
        }
        return stateMachine.getCurrState();
    }

    public void setAttackTarget(Unit attackTarget) {
        this.attackTarget = attackTarget;
    }

    public Unit getAttackTarget() {
        return attackTarget;
    }

    public void subtractCurrHp(int value) {
        currHp -= value;
        if (currHp <= 0) {
            currHp = 0;
        }
    }

    public void addCurrHp(int value) {
        currHp += value;
        if (currHp > hp) {
            currHp = hp;
        }
    }

    public void addCurrMp(int value) {
        currMp += value;
        if (currMp > mp) {
            currMp = mp;
        }
    }

    public void subtractCurrMap(int value) {
        currMp -= value;
        if (currMp < 0) {
            currMp = 0;
        }
    }

    public void addHp(int value) {
        hp += value;
    }

    public void addMp(int value) {
        mp += value;
    }

    public void subtractHp(int value) {
        hp -= value;
    }

    public void subtractMp(int value) {
        mp -= value;
    }

    public void addAttack(int value) {
        attack += value;
    }

    public void subtractAttack(int value) {
        attack -= value;
    }

    public void addDefense(int value) {
        defense += value;
    }

    public void subtractDefense(int value) {
        defense -= value;
    }

    public boolean isCurrHpEmpty() {
        return currHp == 0;
    }

    public void addExpr(int expr) {

    }

    public void addGolds(int value) {

    }

    @Override
    public boolean isDead() {
        return getCurrState().equals(PlayerState.DEAD);
    }

    /**
     * 更新
     */
    @Override
    public void update() {
        if (stateMachine != null) {
            stateMachine.update();
        }
    }

    /**
     * 单位类型
     */
    @Override
    public int getUnitType() {
        return UnitType.PLAYER;
    }

    /**
     * 单位Id
     */
    @Override
    public long getUnitId() {
        return id;
    }
}
