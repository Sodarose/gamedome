package com.game.gameserver.module.ai.state.pet;

import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.pet.entity.Pet;

/**
 * @author xuewenkang
 * @date 2020/6/23 10:36
 */
public enum  PetState implements State<Pet> {
    /*** 宠物状态 */
    /** 死亡 */
    DEAD(){
        @Override
        public void enter(Pet pet) {

        }

        @Override
        public void update(Pet pet) {

        }

        @Override
        public void exit(Pet pet) {

        }
    },
    /** 存活状态 */
    LIVE(){
        @Override
        public void enter(Pet pet) {

        }

        @Override
        public void update(Pet pet) {

        }

        @Override
        public void exit(Pet pet) {

        }
    },
    /** 攻击 */
    AUTO_ATTACK(){
        @Override
        public void enter(Pet pet) {

        }

        @Override
        public void update(Pet pet) {

        }

        @Override
        public void exit(Pet pet) {

        }
    },
}
