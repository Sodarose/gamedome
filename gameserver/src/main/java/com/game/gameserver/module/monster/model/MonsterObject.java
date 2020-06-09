package com.game.gameserver.module.monster.model;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.entity.Property;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.util.GenIdUtil;

/**
 * 怪物模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:18
 */
public class MonsterObject implements Unit {

    /** 唯一id */
    private final int id;
    /** 怪物静态数据 */
    private final MonsterConfig monsterConfig;
    /** 怪物动态属性 */
    private Property property;

    public MonsterObject(MonsterConfig monsterConfig){
        this.id = GenIdUtil.nextId();
        this.monsterConfig = monsterConfig;
    }

    /** 初始化 */
    public void initialize(){
        this.property = new Property(monsterConfig.getHp(),monsterConfig.getMp(),
                monsterConfig.getAttack(),monsterConfig.getDefense());
    }

    @Override
    public void update() {

    }

    public int getId() {
        return id;
    }
}
