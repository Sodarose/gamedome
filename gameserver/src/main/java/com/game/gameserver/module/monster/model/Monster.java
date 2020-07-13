package com.game.gameserver.module.monster.model;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.entity.UnitType;
import com.game.gameserver.module.ai.fsm.StateMachine;
import com.game.gameserver.module.ai.state.monster.MonsterState;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 怪物模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:18
 */
@Data
public class Monster implements Unit {

    /**
     * 唯一id
     */
    private final long id;
    /**
     * 怪物静态数据
     */
    private final MonsterConfig monsterConfig;
    /**
     * 怪物动态属性
     */
    private int hp;
    private int currHp;
    private int mp;
    private int currMp;
    private int attack;
    private int defense;
    /**
     * 怪物类型
     */
    private final int monsterType;
    /**
     * 怪物所在位置ID /场景Id/副本Id
     */
    private Long addrId;
    /**
     * 怪物状态机
     */
    private final StateMachine<Monster, MonsterState> stateStateMachine;
    /**
     * 怪物仇恨对象
     */
    private Unit hateUnit;
    /**
     * 临时数据
     */
    private final Map<String, Object> tempData = new ConcurrentHashMap<>();

    public Monster(MonsterConfig monsterConfig, int monsterType) {
        this.id = GameUUID.getInstance().generate();
        this.monsterConfig = monsterConfig;
        this.monsterType = monsterType;
        this.stateStateMachine = new StateMachine<>(this, MonsterState.PATROL);
    }

    public Monster(MonsterConfig monsterConfig, int monsterType, Long addrId) {
        this.addrId = addrId;
        this.monsterConfig = monsterConfig;
        this.monsterType = monsterType;
        this.id = GameUUID.getInstance().generate();
        this.stateStateMachine = new StateMachine<>(this, MonsterState.PATROL);
    }


    /**
     * 初始化
     */
    public void initialize() {
        this.hp = monsterConfig.getHp();
        this.mp = monsterConfig.getMp();
        this.currHp = monsterConfig.getHp();
        this.currMp = monsterConfig.getMp();
        this.attack = monsterConfig.getAttack();
        this.defense = monsterConfig.getDefense();
    }

    public long getId() {
        return id;
    }


    public MonsterConfig getMonsterConfig() {
        return monsterConfig;
    }

    /**
     * 更新
     */
    @Override
    public void update() {
        if (stateStateMachine != null) {
            stateStateMachine.update();
        }
    }

    /**
     * 单位类型
     */
    @Override
    public int getUnitType() {
        return UnitType.MONSTER;
    }

    /**
     * 单位Id
     */
    @Override
    public long getUnitId() {
        return id;
    }

    /**
     * 是否死亡
     */
    @Override
    public boolean isDead() {
        if (stateStateMachine == null) {
            return true;
        }
        return stateStateMachine.getCurrState().equals(MonsterState.DEAD);
    }

    /**
     * 扣除血量
     *
     * @param value
     * @return void
     */
    public void reduceCurrHp(int value) {
        this.currHp -= value;
        if (this.currHp < 0) {
            this.currHp = 0;
        }
    }

    public void addCurrHp(int value) {
        this.currHp += value;
        if (this.currHp > hp) {
            this.currHp = hp;
        }
    }

    public boolean isCurrHpEmpty() {
        return this.currHp == 0;
    }

    public void changeState(MonsterState state) {
        if (stateStateMachine != null) {
            stateStateMachine.changeState(state);
        }
    }

    public MonsterState getCurrState() {
        if (stateStateMachine == null) {
            return null;
        }
        return stateStateMachine.getCurrState();
    }
}
