package com.game.gameserver.common.fsm.state.npc;

import com.game.gameserver.common.fsm.State;
import com.game.gameserver.module.npc.model.Npc;

/**
 * @author xuewenkang
 * @date 2020/6/22 21:47
 */
public enum  NpcState implements State<Npc> {
    /** 来回走动*/
    WALK(){
        @Override
        public void enter(Npc npc) {

        }

        @Override
        public void update(Npc npc) {

        }

        @Override
        public void exit(Npc npc) {

        }
    },
    /** 站立不动 */
    STAND(){
        @Override
        public void enter(Npc npc) {

        }

        @Override
        public void update(Npc npc) {

        }

        @Override
        public void exit(Npc npc) {

        }
    }
}
