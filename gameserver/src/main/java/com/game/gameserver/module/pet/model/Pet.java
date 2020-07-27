package com.game.gameserver.module.pet.model;

import com.game.gameserver.common.config.PetConfig;
import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.entity.CreatureType;
import com.game.gameserver.common.fsm.StateMachine;
import com.game.gameserver.common.fsm.state.pet.PetState;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.type.PlayerState;
import com.game.gameserver.module.scene.model.GameScene;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.skill.model.Skill;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xuewenkang
 * @date 2020/7/17 10:55
 */
@Data
public class Pet implements Creature {

    /*** id*/
    private long id;

    /*** 怪物名称*/
    private String name;

    /*** 怪物等级*/
    private int level;

    /*** 怪物类型*/
    private int type;

    /*** 战斗属性*/
    private int hp;

    private int currHp;

    private int mp;

    private int currMp;

    private int attack;

    private int defense;

    private Player master;

    /**
     * 怪物当前场景
     */
    private Scene currScene;

    /**
     * 召唤物当前状态
     */
    private PetState petState;

    /**
     * 状态机
     */
    private StateMachine<Pet, PetState> stateMachine;

    /*** 攻击目标*/
    private Creature target;

    /*** 怪物Buffer*/
    private List<Buffer> buffers = new CopyOnWriteArrayList<>();

    /*** 技能CD表*/
    private Map<Integer, Skill> skillCdMap = new ConcurrentHashMap<>();

    /**
     * 怪物技能表
     */
    private Map<Integer, Skill> skillMap = new HashMap<>();

    /**
     * 临时数据
     */
    private Map<String, Object> tempData = new HashMap<>();

    private PetConfig petConfig;

    public Pet() {

    }

    public Pet(Player master, PetConfig petConfig) {
        this.id = GameUUID.getInstance().generate();
        this.name = petConfig.getName();
        this.level = petConfig.getLevel();
        this.type = petConfig.getType();
        this.petConfig = petConfig;
        this.hp = petConfig.getHp();
        this.currHp = petConfig.getHp();
        this.mp = petConfig.getMp();
        this.currMp = petConfig.getMp();
        this.attack = petConfig.getAttack();
        this.defense = petConfig.getDefense();
        this.petState = PetState.PET_FOLLOW;
        this.stateMachine = new StateMachine<>(this, PetState.PET_FOLLOW);
        this.master = master;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHp() {
        return this.hp;
    }

    @Override
    public void setHp(int value) {
        this.hp = value;
    }

    @Override
    public int getCurrHp() {
        return this.currHp;
    }

    @Override
    public void setCurrHp(int value) {
        this.currHp = value;
    }

    @Override
    public void changeCurrHp(int value) {
        this.currHp += value;
        if (currHp > hp) {
            currHp = hp;
        }
        if (currHp < 0) {
            currHp = 0;
        }
    }


    @Override
    public int getMp() {
        return mp;
    }

    @Override
    public void setMp(int value) {
        this.mp = value;
    }

    @Override
    public int getCurrMp() {
        return currMp;
    }

    @Override
    public void setCurrMp(int value) {
        this.currMp = value;
    }

    @Override
    public void changeCurrMp(int value) {
        this.currMp += value;
        if (currMp > mp) {
            currMp = mp;
        }
        if (currMp < 0) {
            currMp = 0;
        }
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public List<Buffer> getBuffers() {
        return buffers;
    }

    @Override
    public CreatureType getType() {
        return CreatureType.PET;
    }

    @Override
    public Map<Integer, Skill> getInCdSkill() {
        return skillCdMap;
    }

    @Override
    public Scene getScene() {
        return currScene;
    }

    @Override
    public Skill getSkill(int skillId) {
        return skillMap.get(skillId);
    }

    @Override
    public boolean isDead() {
        return petState == PetState.PET_DEAD;
    }

    @Override
    public void setDead(boolean dead) {
        if (dead) {
            if (stateMachine != null) {
                stateMachine.changeState(PetState.PET_DEAD);
            }
        }
    }

    public void changeState(PetState petState) {
        if (stateMachine != null) {
            stateMachine.changeState(petState);
        }
    }

    @Override
    public void update() {
        if (stateMachine != null) {
            stateMachine.update();
        }
    }


    public Map<Integer, Skill> getSkillCdMap() {
        return skillCdMap;
    }
}
