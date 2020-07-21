package com.game.gameserver.common.fsm.state.pet;

import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.fsm.State;
import com.game.gameserver.common.fsm.state.player.PlayerState;
import com.game.gameserver.module.fighter.service.FighterService;
import com.game.gameserver.module.pet.model.Pet;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.skill.model.Skill;
import com.game.gameserver.module.skill.service.SkillService;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/7/20 11:02
 */
public enum PetState implements State<Pet> {
    /** 召唤物跟随状态 */
    PET_FOLLOW{
        @Override
        public void enter(Pet pet) {

        }

        @Override
        public void update(Pet pet) {
            // 获取主人信息
            Player player = pet.getMaster();
            // 判断主人当前状态 主人死亡 即召唤兽死亡
            if(player.isDead()){
                pet.changeState(PetState.PET_DEAD);
                return;
            }
            // 如果主人的当前状态为攻击状态 则切换成攻击状态 共同攻击目标
            if(player.getState()== PlayerState.PLAYER_ATTACK){
                Creature target = player.getTarget();
                pet.setTarget(target);
                pet.changeState(PetState.PET_ATTACK);
                return;
            }
            // 如果不再同一个场景 则移动到同一个场景
            if(player.getScene()!=pet.getScene()){
                Scene sourceScene = pet.getScene();
                Scene targetScene = player.getScene();
                // 退出原场景
                sourceScene.getPetMap().remove(pet.getId());
                // 进入新场景
                targetScene.getPetMap().put(pet.getId(),pet);
                return;
            }
            //
        }

        @Override
        public void exit(Pet pet) {

        }
    },
    /** 召唤物攻击状态 */
    PET_ATTACK{

        /** 下一次攻击事时间键 */
        private final static String NEX_ATTACK_TIME = "NEX_ATTACK_TIME";

        private SkillService skillService = SkillService.instance;

        private FighterService fighterService = FighterService.instance;

        @Override
        public void enter(Pet pet) {
            long currTime = System.currentTimeMillis();
            long attackTime = currTime + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            pet.getTempData().put(NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void update(Pet pet) {
            // 获得攻击目标
            Creature target = pet.getTarget();
            if(target==null||target.isDead()){
                pet.setTarget(null);
                pet.changeState(PetState.PET_FOLLOW);
                return;
            }
            // 是否达到攻击事件
            long currTime = System.currentTimeMillis();
            if (pet.getTempData().get(NEX_ATTACK_TIME) == null) {
                // 攻击时间丢失
                pet.changeState(PetState.PET_FOLLOW);
                return;
            }
            long attackTime = (long) pet.getTempData().get(NEX_ATTACK_TIME);
            // 还未到达攻击的时间
            if (currTime < attackTime) {
                return;
            }
            // 获取可用技能
            List<Skill> useAbleSkill = skillService.getUseAbleSkill(pet);
            // 随机抽取一个不在CD下的技能Id
            int id = new Random().nextInt(useAbleSkill.size());
            Skill skill = useAbleSkill.get(id);
            // 攻击对方;
            fighterService.petFight(pet,target.getId(),
                    target.getType().getType(),skill.getSkillId());
            // 计算下一次攻击时间
            currTime = System.currentTimeMillis();
            attackTime = currTime + TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
            pet.getTempData().put(NEX_ATTACK_TIME, attackTime);
        }

        @Override
        public void exit(Pet pet) {

        }
    },
    /** 召唤物死亡状态 */
    PET_DEAD{
        @Override
        public void enter(Pet pet) {
            pet.setPetState(PET_DEAD);
        }

        @Override
        public void update(Pet pet) {
            // 从场景中移除对象
            pet.getScene().getPetMap().remove(pet.getId());
            // 从主人中移除对象

        }

        @Override
        public void exit(Pet pet) {

        }
    }
}
