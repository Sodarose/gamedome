package com.game.gameserver.module.ai.state.monster;

import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.monster.model.MonsterObject;

/**
 * @author xuewenkang
 * @date 2020/6/22 21:31
 */
public enum MonsterState implements State<MonsterObject> {
    /** 怪物状态 */
    /** 正常 */
    LIVE(){
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
    /** 狂暴*/
    RAGE(){
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
    /** 死亡 */
    DEAD(){

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

    /** 行为状态*/
    /** 巡逻 */
    PATROL(){
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
    /** 站立 */
    STAND(){
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
    /** 攻击*/
    ATTACK(){
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
