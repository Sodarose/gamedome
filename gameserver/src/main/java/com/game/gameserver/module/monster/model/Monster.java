package com.game.gameserver.module.monster.model;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.entity.CreatureType;
import com.game.gameserver.common.fsm.StateMachine;
import com.game.gameserver.common.fsm.state.monster.MonsterState;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.scene.model.GameScene;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.skill.entity.SkillEntity;
import com.game.gameserver.module.skill.model.Skill;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 怪物模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:18
 */
@Data
public class Monster implements Creature {

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

    /*** 怪物刷新时间*/
    private long refreshTime;

    /** 怪物当前场景 */
    private Scene currScene;

    /** 怪物状态 */
    private MonsterState state;

    private StateMachine<Monster,MonsterState> stateMachine;

    /*** 攻击目标*/
    private Creature target;

    /*** 怪物Buffer*/
    private List<Buffer> buffers = new CopyOnWriteArrayList<>();

    /*** 技能CD表*/
    private Map<Integer, Skill> skillCdMap = new ConcurrentHashMap<>();

    /** 怪物技能表 */
    private Map<Integer,Skill> skillMap = new HashMap<>();

    /** 临时数据存放地 */
    private Map<String,Object> tempData = new HashMap<>();

    public Monster() {

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
    public int getCurrHp() {
        return currHp;
    }

    @Override
    public void setCurrHp(int value) {
        this.currHp = value;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int value) {
        this.hp = value;
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
    public int getAttack() {
        return this.attack;
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public int getDefense() {
        return this.defense;
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
        return CreatureType.MONSTER;
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
        return state==MonsterState.MONSTER_DEAD;
    }

    @Override
    public void setDead(boolean dead) {
        if(dead){
            if(stateMachine!=null){
                stateMachine.changeState(MonsterState.MONSTER_DEAD);
            }
        }
    }

    /**
     * 得到攻击目标 AI使用
     */
    @Override
    public Creature getTarget() {
        return target;
    }

    @Override
    public void update() {
        if(stateMachine!=null){
            stateMachine.update();
        }
    }

    public void changeState(MonsterState monsterState){
        if(stateMachine!=null){
            stateMachine.changeState(monsterState);
        }
    }
}
