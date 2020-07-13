/*
package com.game.gameserver.module.ai.state.pet;

import com.game.gameserver.common.config.PetConfig;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.ai.state.player.PlayerState;
import com.game.gameserver.module.cooltime.entity.UnitCoolTime;
import com.game.gameserver.module.cooltime.manager.CoolTimeManager;
import com.game.gameserver.module.fighter.service.impl.FighterServiceImpl;
import com.game.gameserver.module.pet.entity.Pet;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

*/
/**
 * @author xuewenkang
 * @date 2020/6/23 10:36
 *//*

public enum PetState implements State<Pet> {
    */
/*** 宝宝状态 *//*

    */
/**
     * 跟随状态
     *//*

    FOLLOW {
        @Override
        public void enter(Pet pet) {

        }

        @Override
        public void update(Pet pet) {
            // 判断主人状态  如果主人在攻击状态 则切换为攻击状态 并将仇恨目标转换为与主人相同的目标
            long masterId = pet.getPlayerId();
            Player player = PlayerManager.instance.getPlayer(masterId);
            if (player == null) {
                return;
            }
            // 主人是否处于攻击状态
            if (player.getCurrState().equals(PlayerState.ATTACK)) {
                // 得到主人的攻击目标
                Unit unit = player.getAttackTarget();
                if (unit == null) {
                    return;
                }
                pet.setHateUnit(unit);
                pet.changeState(PetState.ATTACK);
            }
        }

        @Override
        public void exit(Pet pet) {

        }
    },
    // 攻击状态
    ATTACK {
        */
/** 下次攻击时间 *//*

        private final static String NEX_ATTACK_TIME = "NEX_ATTACK_TIME";

        @Override
        public void enter(Pet pet) {
            // 设定第一次攻击时间
            long currTime = System.currentTimeMillis();
            long attackTime = currTime + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            pet.getTempData().put(NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void update(Pet pet) {
            // 判断目标仇恨目标是否存在
            Unit unit = pet.getHateUnit();
            if (unit == null||unit.isDead()) {
                pet.changeState(PetState.TAKEOFF);
                return;
            }
            // 是否达到攻击事件
            long currTime = System.currentTimeMillis();
            if (pet.getTempData().get(NEX_ATTACK_TIME) == null) {
                // 攻击时间丢失
                pet.changeState(PetState.TAKEOFF);
                return;
            }
            long attackTime = (long) pet.getTempData().get(NEX_ATTACK_TIME);
            // 还未到达攻击的时间
            if (currTime < attackTime) {
                return;
            }
            // 获取怪物基础对象
            PetConfig petConfig = pet.getPetConfig();
            if (petConfig == null) {
                return;
            }
            List<Integer> skills = new ArrayList<>(petConfig.getSkills());
            // 移除正在CD 中的技能
            UnitCoolTime unitCoolTime = CoolTimeManager.instance.getUnitCoolTime(pet.getId());
            if (unitCoolTime != null) {
                skills.removeIf(unitCoolTime::hasSkillCoolTime);
            }
            // 随机抽取一个不在CD下的技能Id
            int id = new Random().nextInt(skills.size());
            // 攻击目标
            FighterServiceImpl.instance.petUseSkill(pet.getId(), unit.getUnitId(), unit.getUnitType(), id);
            // 计算下一次攻击时间
            currTime = System.currentTimeMillis();
            attackTime = currTime + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            pet.getTempData().put(NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void exit(Pet pet) {

        }
    },
    TAKEOFF {
        */
/** 固定回复的数值 *//*

        private final static int HP = 100;
        private final static int MP = 100;
        */
/** 回复期间  单位秒 *//*

        private final static int DURATION = 1;
        */
/** 下一次的回复时间*//*

        private final static String NEXT_REPLY_TIME = "NEXT_REPLY_TIME";

        @Override
        public void enter(Pet pet) {
            // 设置第一次回复的时间
            long currTime = System.currentTimeMillis();
            long nextTime = currTime+TimeUnit.MILLISECONDS.convert(DURATION,TimeUnit.SECONDS);
            pet.getTempData().put(NEXT_REPLY_TIME,nextTime);
        }

        @Override
        public void update(Pet pet) {
            long currTime = System.currentTimeMillis();
            if (pet.getTempData().get(NEXT_REPLY_TIME) == null) {
                return;
            }
            // 时间未到
            long nextTime = (long) pet.getTempData().get(NEXT_REPLY_TIME);
            if (currTime < nextTime) {
                return;
            }
            pet.addCurrHp(HP);
            // 恢复满了 脱离该状态
            if(pet.getCurrHp()==pet.getHp()){
                pet.changeState(PetState.FOLLOW);
            }
        }

        @Override
        public void exit(Pet pet) {

        }
    },
    DEAD {
        @Override
        public void enter(Pet pet) {

        }

        @Override
        public void update(Pet pet) {

        }

        @Override
        public void exit(Pet pet) {

        }
    }
}
*/
