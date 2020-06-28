package com.game.gameserver.module.ai.state.monster;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.cooltime.entity.UnitCoolTime;
import com.game.gameserver.module.cooltime.manager.CoolTimeManager;
import com.game.gameserver.module.fighter.service.FighterService;
import com.game.gameserver.module.fighter.service.impl.FighterServiceImpl;
import com.game.gameserver.module.monster.model.MonsterObject;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/6/22 21:31
 */
public enum MonsterState implements State<MonsterObject> {
    /**
     * 怪物状态
     */
    // 巡逻状态
    PATROL {
        @Override
        public void enter(MonsterObject monsterObject) {

        }

        @Override
        public void update(MonsterObject monsterObject) {

        }

        @Override
        public void exit(MonsterObject monsterObject) {

        }
    },
    // 驻守状态
    DEFEND {
        @Override
        public void enter(MonsterObject monsterObject) {

        }

        @Override
        public void update(MonsterObject monsterObject) {

        }

        @Override
        public void exit(MonsterObject monsterObject) {

        }
    },
    ATTACK {
        /** 下次攻击时间 */
        private final static String NEX_ATTACK_TIME = "NEX_ATTACK_TIME";

        @Override
        public void enter(MonsterObject monsterObject) {
            // 设定第一次攻击时间
            long currTime = System.currentTimeMillis();
            long attackTime = currTime + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            monsterObject.getTempData().put(NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void update(MonsterObject monsterObject) {
            // 判断目标仇恨目标是否存在
            Unit unit = monsterObject.getHateUnit();
            if (unit == null || unit.isDead()) {
                monsterObject.changeState(MonsterState.TAKEOFF);
                return;
            }
            // 是否达到攻击事件
            long currTime = System.currentTimeMillis();
            if (monsterObject.getTempData().get(NEX_ATTACK_TIME) == null) {
                // 攻击时间丢失
                monsterObject.changeState(MonsterState.TAKEOFF);
                return;
            }
            long attackTime = (long) monsterObject.getTempData().get(NEX_ATTACK_TIME);
            // 还未到达攻击的时间
            if (currTime < attackTime) {
                return;
            }
            // 获取怪物基础对象
            MonsterConfig monsterConfig = monsterObject.getMonsterConfig();
            List<Integer> skills = new ArrayList<>(monsterConfig.getSkills());
            // 移除正在CD 中的技能
            UnitCoolTime unitCoolTime = CoolTimeManager.instance.getUnitCoolTime(monsterObject.getId());
            if (unitCoolTime != null) {
                Iterator<Integer> iterator = skills.iterator();
                while (iterator.hasNext()) {
                    int skillId = iterator.next();
                    if (unitCoolTime.hasSkillCoolTime(skillId)) {
                        iterator.remove();
                    }
                }
            }
            // 随机抽取一个不再CD下的技能Id
            int id = new Random().nextInt(skills.size());
            // 攻击目标
            FighterServiceImpl.instance.monsterUseSkill(monsterObject.getId(), unit.getUnitId(), unit.getUnitType()
                    , skills.get(id));
            // 计算下一次攻击时间
            currTime = System.currentTimeMillis();
            attackTime = currTime + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            monsterObject.getTempData().put(NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void exit(MonsterObject monsterObject) {
            // 退出攻击状态时 清空仇恨值
            monsterObject.setHateUnit(null);
        }
    },
    TAKEOFF {
        /** 固定回复的数值 */
        private final static int HP = 100;
        private final static int MP = 100;
        /** 回复期间  单位秒 */
        private final static int DURATION = 1;
        /** 下一次的回复时间*/
        private final static String NEXT_REPLY_TIME = "NEXT_REPLY_TIME";

        @Override
        public void enter(MonsterObject monsterObject) {
            // 设置第一次回复的时间
            long currTime = System.currentTimeMillis();
            long nextTime = currTime + TimeUnit.MILLISECONDS.convert(DURATION, TimeUnit.SECONDS);
            monsterObject.getTempData().put(NEXT_REPLY_TIME, nextTime);
        }

        @Override
        public void update(MonsterObject monsterObject) {
            long currTime = System.currentTimeMillis();
            if (monsterObject.getTempData().get(NEXT_REPLY_TIME) == null) {
                return;
            }
            // 时间未到
            long nextTime = (long) monsterObject.getTempData().get(NEXT_REPLY_TIME);
            if (currTime < nextTime) {
                return;
            }
            monsterObject.addCurrHp(HP);
            if (monsterObject.getCurrHp() == monsterObject.getHp()) {
                monsterObject.changeState(MonsterState.DEFEND);
            }
        }

        @Override
        public void exit(MonsterObject monsterObject) {

        }
    },
    // 死亡状态
    DEAD {
        @Override
        public void enter(MonsterObject monsterObject) {

        }

        @Override
        public void update(MonsterObject monsterObject) {

        }

        @Override
        public void exit(MonsterObject monsterObject) {

        }
    }

}
