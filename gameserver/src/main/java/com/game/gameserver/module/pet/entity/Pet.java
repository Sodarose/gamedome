package com.game.gameserver.module.pet.entity;

import com.game.gameserver.common.config.PetConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.entity.UnitType;
import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.ai.fsm.StateMachine;
import com.game.gameserver.module.ai.state.pet.PetState;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

/**
 * 宝宝实体
 *
 * @author xuewenkang
 * @date 2020/6/23 9:27
 */
@Data
public class Pet implements Unit {
    /** 唯一id */
    private final long id;
    /** 怪物静态数据 */
    private final int petConfigId;
    /** 怪物动态属性 */
    private int hp;
    private int currHp;
    private int mp;
    private int currMp;
    private int attack;
    private int defense;

    /** 所在位置ID /场景Id/副本Id */
    private Long addrId;
    /** 状态机 */
    StateMachine<Pet, PetState> stateMachine;
    /** 是否死亡 */
    private volatile boolean dead;

    public Pet(int petConfigId){
        this.id = GameUUID.getInstance().generate();
        this.petConfigId = petConfigId;
        this.stateMachine = new StateMachine<>(this,PetState.AUTO_ATTACK,PetState.LIVE);
    }

    /** 初始化 */
    public void initialize(){
        PetConfig petConfig = StaticConfigManager.getInstance().getPetConfigMap().get(petConfigId);
        if(petConfig==null){
            return;
        }
        this.hp = petConfig.getHp();
        this.mp = petConfig.getMp();
        this.currHp = petConfig.getHp();
        this.currMp = petConfig.getMp();
        this.attack = petConfig.getAttack();
        this.defense = petConfig.getDefense();
    }

    /**
     * 更新
     */
    @Override
    public void update() {
        if(stateMachine!=null){
            stateMachine.update();
        }
    }

    /**
     * 单位类型
     */
    @Override
    public int getUnitType() {
        return UnitType.PET;
    }

    /**
     * 单位Id
     */
    @Override
    public long getUnitId() {
        return id;
    }
}
