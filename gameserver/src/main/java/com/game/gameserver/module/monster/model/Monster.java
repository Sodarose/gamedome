package com.game.gameserver.module.monster.model;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.entity.Creature;
import lombok.Data;

import java.util.List;

/**
 * 怪物模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:18
 */
@Data
public class Monster implements Creature {

    /** id */
    private final long monsterId;

    /** 怪物名称 */
    private final String name;

    /** 怪物等级 */
    private int level;

    /** 怪物类型 */
    private int type;

    /** 战斗属性 */
    private int hp;
    private int currHp;
    private int mp;
    private int currMp;
    private int attack;
    private int defense;

    /** 怪物技能列表 */
    private List<Integer> skills;

    /** 怪物刷新时间 */
    private Integer refreshTime;

    /** 攻击目标 */
    private Creature target;

    public Monster(MonsterConfig monsterConfig){
        this.monsterId = monsterConfig.getId();
        this.name = monsterConfig.getName();
        this.level = monsterConfig.getLevel();
        this.type = monsterConfig.getType();
        this.hp = monsterConfig.getHp();
        this.currHp = monsterConfig.getHp();
        this.mp = monsterConfig.getMp();
        this.currMp = monsterConfig.getMp();
        this.attack = monsterConfig.getAttack();
        this.defense = monsterConfig.getDefense();
        this.skills = monsterConfig.getSkills();
        this.refreshTime = monsterConfig.getRefreshTime();
    }

    public void initialize(){

    }

    @Override
    public long getId() {
        return monsterId;
    }
}
