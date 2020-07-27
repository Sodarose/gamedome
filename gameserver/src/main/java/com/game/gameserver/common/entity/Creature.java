package com.game.gameserver.common.entity;

import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.pet.model.Pet;
import com.game.gameserver.module.scene.model.GameScene;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.skill.entity.SkillEntity;
import com.game.gameserver.module.skill.model.Skill;

import java.util.List;
import java.util.Map;

/**
 * 活物接口
 *
 * @author xuewenkang
 * @date 2020/7/10 2:29
 */
public interface Creature {
    long getId();

    String getName();

    /** hp */
    int getHp();
    void setHp(int value);

    /** 当前hp */
    int getCurrHp();
    void setCurrHp(int value);
    void changeCurrHp(int value);

    /** mp */
    int getMp();
    void setMp(int value);

    /** 当前mp */
    int getCurrMp();
    void setCurrMp(int value);
    void changeCurrMp(int value);

    /** 攻击力 */
    int getAttack();
    void setAttack(int attack);

    /** 防御力 */
    int getDefense();
    void setDefense(int defense);

    /** buffer */
    List<Buffer> getBuffers();

    /** 类型 */
    CreatureType getType();

    /**得到进入CD 的技能 */
    Map<Integer, Skill> getInCdSkill();

    /** 得到当前场景 */
    Scene getScene();

    Skill getSkill(int skillId);
    Map<Integer,Skill> getSkillMap();

    boolean isDead();
    void setDead(boolean dead);

    /** 得到攻击目标 AI使用 */
    Creature getTarget();

    /** 更新自身状态 AI使用 */
    void update();

}
