package com.game.gameserver.module.ai.state.player;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.cooltime.entity.UnitCoolTime;
import com.game.gameserver.module.cooltime.manager.CoolTimeManager;
import com.game.gameserver.module.fighter.service.impl.FighterServiceImpl;
import com.game.gameserver.module.player.entity.PlayerBattle;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.type.PlayerStaticData;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/6/22 21:50
 */
public enum PlayerState implements State<PlayerObject> {
    /*** 角色状态 */
    NORMAL() {
        @Override
        public void enter(PlayerObject playerObject) {
            // 设置成非死亡
        }

        @Override
        public void update(PlayerObject playerObject) {

        }

        @Override
        public void exit(PlayerObject playerObject) {

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
        public void enter(PlayerObject playerObject) {
            // 设置死亡时间和复活时间
            playerObject.getTempData().put(DEAD_TIME, System.currentTimeMillis());
            playerObject.getTempData().put(REVIVE, System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(
                    DURATION, TimeUnit.SECONDS
            ));

        }

        @Override
        public void update(PlayerObject playerObject) {
            long currTime = System.currentTimeMillis();
            if (playerObject.getTempData().get(REVIVE) == null) {
                playerObject.changeState(NORMAL);
                return;
            }
            long reviveTime = (long) playerObject.getTempData().get(REVIVE);
            if (currTime >= reviveTime) {
                playerObject.changeState(NORMAL);
            }
        }

        @Override
        public void exit(PlayerObject playerObject) {
            // 移除数据
            // playerObject.getTempData().remove(DEAD_TIME);
            // playerObject.getTempData().remove(REVIVE);
            // 退出死亡状态时 清除所有异常状态 恢复血量
            playerObject.revert();
        }
    },
    // 攻击 处于攻击状态的单位自动攻击选定的目标
    ATTACK() {
        /** 设定第一次攻击时间*/
        @Override
        public void enter(PlayerObject playerObject) {
            // 第一次攻击的时间 根据设定的攻击速度计算
            long attackTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(playerObject
                    .getPlayerBattle()
                    .getAttackSpeed(), TimeUnit.SECONDS);
            // 设定下次攻击的时间
            playerObject.getTempData().put(PlayerStaticData.NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void update(PlayerObject playerObject) {
            // 当前时间
            long currTime = System.currentTimeMillis();
            // 攻击目标丢失 切换脱战状态
            Unit unit = playerObject.getAttackTarget();
            if(unit==null||unit.isDead()){
                playerObject.changeState(PlayerState.TAKEOFF);
            }
            // 攻击时间丢失
            if (playerObject.getTempData().get(PlayerStaticData.NEX_ATTACK_TIME) == null) {
                playerObject.changeState(PlayerState.TAKEOFF);
                return;
            }
            // 获取本次攻击的时间
            Long attackTime = (Long) playerObject.getTempData().get(PlayerStaticData.NEX_ATTACK_TIME);
            if (currTime < attackTime) {
                return;
            }

            // 获取职业信息
            CareerConfig careerConfig = StaticConfigManager.getInstance().getCareerConfigMap().get(playerObject.getPlayer().getCareerId());
            List<Integer> skillIds = careerConfig.getSkillIds();

            // 获取可用技能
            List<Integer> skills = new ArrayList<>(skillIds);
            // 移除正在冷却的技能
            UnitCoolTime unitCoolTime = CoolTimeManager.instance.getUnitCoolTime(playerObject.getPlayer().getId());
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
                    playerObject.getPlayer().getId(),
                    unit.getUnitId(),
                    unit.getUnitType(),
                    skillId
            );
            // 计算下一次攻击时间
            attackTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(playerObject
                    .getPlayerBattle()
                    .getAttackSpeed(), TimeUnit.SECONDS);
            playerObject.getTempData().put(PlayerStaticData.NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void exit(PlayerObject playerObject) {
            // 清空仇恨值
            playerObject.setAttackTarget(null);
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
        public void enter(PlayerObject playerObject) {
            // 设置第一次回复的时间
            long currTime = System.currentTimeMillis();
            long nextTime = currTime + TimeUnit.MILLISECONDS.convert(DURATION, TimeUnit.SECONDS);
            playerObject.getTempData().put(NEXT_REPLY_TIME, nextTime);
        }

        @Override
        public void update(PlayerObject playerObject) {
            long currTime = System.currentTimeMillis();
            if (playerObject.getTempData().get(NEXT_REPLY_TIME) == null) {
                return;
            }
            long nextTime = (long) playerObject.getTempData().get(NEXT_REPLY_TIME);
            if (currTime < nextTime) {
                return;
            }
            // 获取战斗属性
            PlayerBattle playerBattle = playerObject.getPlayerBattle();
            // 恢复HP和MP
            // 当HP和MP满的时候 退出该状态
            playerBattle.addCurrHp(HP);
            playerBattle.addCurrMp(MP);
            // 退出脱战状态
            if (playerBattle.getCurrHp() == playerBattle.getHp() && playerBattle.getCurrMp() == playerBattle.getMp()) {
                playerObject.changeState(PlayerState.NORMAL);
            }
        }

        @Override
        public void exit(PlayerObject playerObject) {

        }
    }
}
