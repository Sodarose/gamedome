package com.game.gameserver.common.fsm.state.monster;

import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.fsm.State;
/*import com.game.gameserver.module.fighter.service.FighterService;*/
import com.game.gameserver.common.fsm.state.pet.PetState;
import com.game.gameserver.module.fighter.service.FighterService;
import com.game.gameserver.module.instance.model.Instance;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.skill.model.Skill;
import com.game.gameserver.module.skill.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/6/22 21:31
 */
public enum MonsterState implements State<Monster> {
    /**
     * 怪物状态
     */
    // 巡逻状态
    MONSTER_PATROL {

        private final static String MONSTER_PATROL_TIME = "MONSTER_PATROL_TIME";

        @Override
        public void enter(Monster monster) {
            // 进入该状态 随机给一个巡逻时间
            int seconds = new Random().nextInt(10);
            long patrolTime = System.currentTimeMillis()+TimeUnit.MILLISECONDS.convert(
                    seconds,TimeUnit.SECONDS
            );
            monster.getTempData().put(MONSTER_PATROL_TIME,patrolTime);
            monster.setState(MonsterState.MONSTER_PATROL);
        }

        @Override
        public void update(Monster monster) {
            long currTime = System.currentTimeMillis();
            if (monster.getTempData().get(MONSTER_PATROL_TIME) == null) {
                return;
            }
            long attackTime = (long) monster.getTempData().get(MONSTER_PATROL_TIME);
            // 还未到达状态转换
            if (currTime < attackTime) {
                return;
            }
            // 转换成驻守状态
            monster.changeState(MonsterState.MONSTER_DEFEND);
        }

        @Override
        public void exit(Monster monster) {

        }
    },
    // 驻守状态
    MONSTER_DEFEND {
        private final static String MONSTER_DEFEND_TIME = "MONSTER_DEFEND_TIME";

        @Override
        public void enter(Monster monster) {
            // 进入该状态 随机给一个驻守时间
            int seconds = new Random().nextInt(10);
            long patrolTime = System.currentTimeMillis()+TimeUnit.MILLISECONDS.convert(
                    seconds,TimeUnit.SECONDS
            );
            monster.getTempData().put(MONSTER_DEFEND_TIME,patrolTime);
            monster.setState(MonsterState.MONSTER_DEFEND);
        }

        @Override
        public void update(Monster monster) {
            long currTime = System.currentTimeMillis();
            if (monster.getTempData().get(MONSTER_DEFEND_TIME) == null) {
                return;
            }
            long attackTime = (long) monster.getTempData().get(MONSTER_DEFEND_TIME);
            // 还未到达状态转换
            if (currTime < attackTime) {
                return;
            }
            // 转换成巡逻状态
            monster.changeState(MonsterState.MONSTER_PATROL);
        }

        @Override
        public void exit(Monster monster) {

        }
    },
    // 怪物攻击状态
    MONSTER_ATTACK {
        /** 下次攻击时间 */
        private final static String NEX_ATTACK_TIME = "NEX_ATTACK_TIME";

        private SkillService skillService = SkillService.instance;

        private FighterService fighterService = FighterService.instance;

        @Override
        public void enter(Monster monster) {
            // 设定第一次攻击时间
            long currTime = System.currentTimeMillis();
            // 固定时间  两秒攻击一次
            long attackTime = currTime + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            monster.getTempData().put(NEX_ATTACK_TIME, attackTime);
            monster.setState(MonsterState.MONSTER_ATTACK);
        }

        @Override
        public void update(Monster monster) {
            // 判断目标仇恨目标是否存在
            Creature target = monster.getTarget();
            if(target==null){
                monster.setTarget(null);
                monster.changeState(MonsterState.MONSTER_TAKEOFF);
                return;
            }
            long currTime = System.currentTimeMillis();
            if (monster.getTempData().get(NEX_ATTACK_TIME) == null) {
                // 攻击时间丢失
                monster.changeState(MonsterState.MONSTER_DEFEND);
                return;
            }
            long attackTime = (long) monster.getTempData().get(NEX_ATTACK_TIME);
            // 还未到达攻击的时间
            if (currTime < attackTime) {
                return;
            }
            // 获取可用技能
            List<Skill> useAbleSkill = skillService.getUseAbleSkill(monster);
            // 随机抽取一个不在CD下的技能Id
            int id = new Random().nextInt(useAbleSkill.size());
            Skill skill = useAbleSkill.get(id);
            fighterService.monsterFight(monster,target.getId(),target.getType().getType(),skill.getSkillId());
            currTime = System.currentTimeMillis();
            attackTime = currTime + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            monster.getTempData().put(NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void exit(Monster monster) {
            // 退出攻击状态时 清空仇恨值
            monster.setTarget(null);
        }
    },
    // 怪物脱战状态
    MONSTER_TAKEOFF {
        /** 固定回复的数值 */
        private final static int HP = 100;
        private final static int MP = 100;
        /** 回复期间  单位秒 */
        private final static int DURATION = 2;
        /** 下一次的回复时间*/
        private final static String NEXT_REPLY_TIME = "NEXT_REPLY_TIME";

        @Override
        public void enter(Monster monster) {
            // 设置第一次回复的时间
            long currTime = System.currentTimeMillis();
            long nextTime = currTime + TimeUnit.MILLISECONDS.convert(DURATION, TimeUnit.SECONDS);
            monster.getTempData().put(NEXT_REPLY_TIME, nextTime);
            monster.setState(MonsterState.MONSTER_TAKEOFF);
        }

        @Override
        public void update(Monster monster) {
            long currTime = System.currentTimeMillis();
            if (monster.getTempData().get(NEXT_REPLY_TIME) == null) {
                return;
            }
            // 时间未到
            long nextTime = (long) monster.getTempData().get(NEXT_REPLY_TIME);
            if (currTime < nextTime) {
                return;
            }
            monster.setCurrHp(monster.getCurrHp()+HP);
            if (monster.getCurrHp() == monster.getHp()) {
                monster.changeState(MonsterState.MONSTER_DEFEND);
            }
        }

        @Override
        public void exit(Monster monster) {

        }
    },

    // 死亡状态
    MONSTER_DEAD {
        /** 死亡时间 */
        private final static String REFRESH_TIME = "NEXT_REPLY_TIME";

        @Override
        public void enter(Monster monster) {
            monster.setState(MonsterState.MONSTER_DEAD);
            long currTime = System.currentTimeMillis();
            long refreshTime = currTime + TimeUnit.MILLISECONDS.convert(monster.getRefreshTime(),
                    TimeUnit.MILLISECONDS);
            monster.getTempData().put(REFRESH_TIME, refreshTime);
        }

        @Override
        public void update(Monster monster) {
            if(monster.getScene() instanceof Instance){
                return;
            }
            long currTime = System.currentTimeMillis();
            if (monster.getTempData().get(REFRESH_TIME) == null) {
                return;
            }
            // 时间未到
            long refreshTime = (long) monster.getTempData().get(REFRESH_TIME);
            if (currTime < refreshTime) {
                return;
            }
            // 加满血量 mp
            monster.setCurrHp(monster.getHp());
            monster.setCurrMp(monster.getMp());
            monster.changeState(MonsterState.MONSTER_DEFEND);
        }

        @Override
        public void exit(Monster monster) {

        }
    }

}
