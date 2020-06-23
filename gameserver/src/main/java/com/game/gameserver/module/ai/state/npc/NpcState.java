package com.game.gameserver.module.ai.state.npc;

import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.npc.model.NpcObject;

/**
 * @author xuewenkang
 * @date 2020/6/22 21:47
 */
public enum  NpcState implements State<NpcObject> {
    /** 来回走动*/
    WALK(){
        @Override
        public void enter(NpcObject npcObject) {

        }

        @Override
        public void update(NpcObject npcObject) {

        }

        @Override
        public void exit(NpcObject npcObject) {

        }
    },
    /** 站立不动 */
    STAND(){
        @Override
        public void enter(NpcObject npcObject) {

        }

        @Override
        public void update(NpcObject npcObject) {

        }

        @Override
        public void exit(NpcObject npcObject) {

        }
    }
}
