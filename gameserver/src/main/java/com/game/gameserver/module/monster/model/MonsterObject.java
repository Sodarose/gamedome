package com.game.gameserver.module.monster.model;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

/**
 * 怪物模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:18
 */
@Data
public class MonsterObject implements Unit {

    /** 唯一id */
    private final long id;
    /** 怪物静态数据 */
    private final MonsterConfig monsterConfig;
    /** 怪物动态属性 */
    private int hp;
    private int currHp;
    private int mp;
    private int currMp;
    private int attack;
    private int defense;
    /** 怪物类型 */
    private final int monsterType;
    /** 怪物所在位置ID /场景Id/副本Id */
    private Long addrId;
    /** 是否死亡 */
    private volatile boolean dead = false;

    public MonsterObject(MonsterConfig monsterConfig,int monsterType){
        this.id = GameUUID.getInstance().generate();
        this.monsterConfig = monsterConfig;
        this.monsterType = monsterType;
    }

    public MonsterObject(MonsterConfig monsterConfig,int monsterType,Long addrId){
        this.addrId = addrId;
        this.monsterConfig = monsterConfig;
        this.monsterType = monsterType;
        this.id = GameUUID.getInstance().generate();
    }


    /** 初始化 */
    public void initialize(){
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


    public MonsterConfig getMonsterConfig(){
        return monsterConfig;
    }

    /**
     * 更新
     */
    @Override
    public void update() {

    }

    /**
     * 单位类型
     */
    @Override
    public int getUnitType() {
        return 0;
    }

    /**
     * 单位Id
     */
    @Override
    public long getUnitId() {
        return 0;
    }
}
