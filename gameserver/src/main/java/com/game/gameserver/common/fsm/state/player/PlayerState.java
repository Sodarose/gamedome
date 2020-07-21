package com.game.gameserver.common.fsm.state.player;

import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.fsm.State;
import com.game.gameserver.module.player.model.Player;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/7/20 11:23
 */
@Component
public enum PlayerState implements State<Player> {
    /** 角色存活状态 */
    PLAYER_LIVE{
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
    /** 角色攻击状态 */
    PLAYER_ATTACK{
        @Override
        public void enter(Player player) {
            player.setState(PLAYER_ATTACK);
        }

        @Override
        public void update(Player player) {
           Creature target =  player.getTarget();
           if(target==null||target.isDead()){
                player.changeState(PlayerState.PLAYER_LIVE);
                return;
           }
        }

        @Override
        public void exit(Player player) {

        }
    },
    /** 角色脱战状态 */
    PLAYER_TAKE_OFF{
        @Override
        public void enter(Player player) {
            player.setState(PLAYER_TAKE_OFF);
        }

        @Override
        public void update(Player player) {

        }

        @Override
        public void exit(Player player) {

        }
    },
    /** 玩家死亡状态 */
    PLAYER_DEAD{

        @Override
        public void enter(Player player) {
            player.setState(PLAYER_DEAD);
        }

        @Override
        public void update(Player player) {

        }

        @Override
        public void exit(Player player) {

        }
    }
    ;
}
