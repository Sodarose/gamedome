package com.game.gameserver.common.fsm.state.player;

import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.fsm.State;
import com.game.gameserver.common.fsm.state.monster.MonsterState;
import com.game.gameserver.module.instance.model.Instance;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerDataService;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/7/20 11:23
 */
@Component
public enum PlayerState implements State<Player> {
    /**
     * 角色存活状态
     */
    PLAYER_LIVE {
        @Override
        public void enter(Player player) {
            player.setState(PLAYER_LIVE);
        }

        @Override
        public void update(Player player) {

        }

        @Override
        public void exit(Player player) {

        }
    },
    /**
     * 角色攻击状态
     */
    PLAYER_ATTACK {
        @Override
        public void enter(Player player) {
            player.setState(PLAYER_ATTACK);
        }

        @Override
        public void update(Player player) {
            Creature target = player.getTarget();
            if (target == null || target.isDead()) {
                player.changeState(PlayerState.PLAYER_LIVE);
                return;
            }
        }

        @Override
        public void exit(Player player) {

        }
    },
    /**
     * 角色脱战状态
     */
    PLAYER_TAKE_OFF {
        /** 固定回复的数值 */
        private final static int HP = 100;
        private final static int MP = 100;
        /** 回复期间  单位秒 */
        private final static int DURATION = 2;
        /** 下一次的回复时间*/
        private final static String NEXT_REPLY_TIME = "NEXT_REPLY_TIME";

        @Override
        public void enter(Player player) {
            player.setState(PLAYER_TAKE_OFF);
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
            // 时间未到
            long nextTime = (long) player.getTempData().get(NEXT_REPLY_TIME);
            if (currTime < nextTime) {
                return;
            }
            player.changeCurrHp(HP);
            if (player.getCurrHp() == player.getHp()) {
                player.changeState(PlayerState.PLAYER_LIVE);
            }
        }

        @Override
        public void exit(Player player) {

        }
    },
    /**
     * 玩家死亡状态
     */
    PLAYER_DEAD {
        /** 死亡时间 */
        private final static String REFRESH_TIME = "NEXT_REPLY_TIME";
        /** 复活时间10s */
        private final static int REVIVAL_TIME = 30;

        @Override
        public void enter(Player player) {
            player.setState(PLAYER_DEAD);
            NotificationHelper.notifyPlayer(player, "你已经死亡！请等待复活!");
            long currTime = System.currentTimeMillis();
            long revivalTime = currTime + TimeUnit.MILLISECONDS.convert(REVIVAL_TIME,
                    TimeUnit.SECONDS);
            player.getTempData().put(REFRESH_TIME, revivalTime);
        }

        @Override
        public void update(Player player) {
            long currTime = System.currentTimeMillis();
            if (player.getTempData().get(REFRESH_TIME) == null) {
                return;
            }
            // 时间未到
            long revivalTime = (long) player.getTempData().get(REFRESH_TIME);
            if (currTime < revivalTime) {
                NotificationHelper.notifyPlayer(player, MessageFormat
                        .format("复活剩余时间{0}", TimeUnit.SECONDS.convert(
                                revivalTime - currTime, TimeUnit.MILLISECONDS
                        )));
                return;
            }
            // 重新初始化
            PlayerDataService.instance.initProperty(player);
            player.changeState(PlayerState.PLAYER_LIVE);
        }

        @Override
        public void exit(Player player) {

        }
    };
}
