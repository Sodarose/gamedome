package com.game.gameserver.module.ai.state.player;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.cooltime.entity.UnitCoolTime;
import com.game.gameserver.module.cooltime.manager.CoolTimeManager;
import com.game.gameserver.module.fighter.service.impl.FighterServiceImpl;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.type.PlayerStaticData;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/6/22 21:50
 */
public enum PlayerState implements State<Player> {
    /*** 角色状态 */
    NORMAL() {
        @Override
        public void enter(Player player) {
            // 设置成非死亡
        }

        @Override
        public void update(Player player) {

        }

        @Override
        public void exit(Player player) {

        }
    },
    /**
     * 死亡状态  10S 后满血复活
     */
    DEAD() {
        /** 死亡时间 */
        private final static String DEAD_TIME = "DEAD_TIME";
        /** 复活时间 */
        private final static String REVIVE = "REVIVE_TIME";
        /** 等待时间*/
        private final static int DURATION = 10;

        @Override
        public void enter(Player player) {
            // 设置死亡时间和复活时间
            player.getTempData().put(DEAD_TIME, System.currentTimeMillis());
            player.getTempData().put(REVIVE, System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(
                    DURATION, TimeUnit.SECONDS
            ));

        }

        @Override
        public void update(Player player) {
            long currTime = System.currentTimeMillis();
            if (player.getTempData().get(REVIVE) == null) {
                player.changeState(NORMAL);
                return;
            }
            long reviveTime = (long) player.getTempData().get(REVIVE);
            if (currTime >= reviveTime) {
                player.changeState(NORMAL);
            }
        }

        @Override
        public void exit(Player player) {
            // 移除数据
            // playerObject.getTempData().remove(DEAD_TIME);
            // playerObject.getTempData().remove(REVIVE);
            // 退出死亡状态时 清除所有异常状态 恢复血量
            player.revert();
        }
    },
    // 攻击 处于攻击状态的单位自动攻击选定的目标
    ATTACK() {
        /** 设定第一次攻击时间*/
        @Override
        public void enter(Player player) {
            // 第一次攻击的时间 根据设定的攻击速度计算
            long attackTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            // 设定下次攻击的时间
            player.getTempData().put(PlayerStaticData.NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void update(Player player) {
            // 当前时间
            long currTime = System.currentTimeMillis();
            // 攻击目标丢失 切换脱战状态
            Unit unit = player.getAttackTarget();
            if(unit==null||unit.isDead()){
                player.changeState(PlayerState.TAKEOFF);
            }
            // 攻击时间丢失
            if (player.getTempData().get(PlayerStaticData.NEX_ATTACK_TIME) == null) {
                player.changeState(PlayerState.TAKEOFF);
                return;
            }
            // 获取本次攻击的时间
            Long attackTime = (Long) player.getTempData().get(PlayerStaticData.NEX_ATTACK_TIME);
            if (currTime < attackTime) {
                return;
            }

            // 获取职业信息
            CareerConfig careerConfig = StaticConfigManager.getInstance().getCareerConfigMap().get(
                    player.getCareerId());
            List<Integer> skillIds = careerConfig.getSkillIds();

            // 获取可用技能
            List<Integer> skills = new ArrayList<>(skillIds);
            // 移除正在冷却的技能
            UnitCoolTime unitCoolTime = CoolTimeManager.instance.getUnitCoolTime(player.getId());
            if (unitCoolTime != null) {
                skills.removeIf(unitCoolTime::hasSkillCoolTime);
            }
            if (skills.size() == 0) {
                return;
            }
            // 随机获取一个技能
            int id = new Random().nextInt(skills.size());
            int skillId = skills.get(id);
            // 攻击怪物
            FighterServiceImpl.instance.playerUseSkill(
                    player.getId(),
                    unit.getUnitId(),
                    unit.getUnitType(),
                    skillId
            );
            // 计算下一次攻击时间
            attackTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS
                    .convert(2, TimeUnit.SECONDS);
            player.getTempData().put(PlayerStaticData.NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void exit(Player player) {
            // 清空仇恨值
            player.setAttackTarget(null);
            // 移除一些数据
            //playerObject.getTempData().remove(PlayerStaticData.NEX_ATTACK_TIME);
        }
    },

    // 脱战 脱战后缓慢恢复HP和MP
    TAKEOFF() {
        /** 固定回复的数值 */
        private final static int HP = 100;
        private final static int MP = 100;
        /** 回复期间  单位秒 */
        private final static int DURATION = 1;
        /** 下一次的回复时间*/
        private final static String NEXT_REPLY_TIME = "NEXT_REPLY_TIME";

        @Override
        public void enter(Player player) {
            // 设置第一次回复的时间
            long currTime = System.currentTimeMillis();
            long nextTime = currTime + TimeUnit.MILLISECONDS.convert(DURATION, TimeUnit.SECONDS);
            player.getTempData().put(NEXT_REPLY_TIME, nextTime);
        }

        @Override
        public void update(Player player) {
            long currTime = System.currentTimeMillis();
            if (player.getTempData().get(NEXT_REPLY_TIME) == null) {
                return;
            }
            long nextTime = (long) player.getTempData().get(NEXT_REPLY_TIME);
            if (currTime < nextTime) {
                return;
            }
            // 获取战斗属性
            // 恢复HP和MP
            // 当HP和MP满的时候 退出该状态
            player.addCurrHp(HP);
            player.addCurrMp(MP);
            // 退出脱战状态
            if (player.getCurrHp() == player.getHp() && player.getCurrMp() == player.getMp()) {
                player.changeState(PlayerState.NORMAL);
            }
        }

        @Override
        public void exit(Player player) {

        }
    }
}
