package com.game.gameserver.module.ai.state.player;

import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.ai.fsm.State;
import com.game.gameserver.module.cooltime.entity.CoolTime;
import com.game.gameserver.module.cooltime.entity.UnitCoolTime;
import com.game.gameserver.module.cooltime.manager.CoolTimeManager;
import com.game.gameserver.module.fighter.service.impl.FighterServiceImpl;
import com.game.gameserver.module.instance.manager.InstanceManager;
import com.game.gameserver.module.instance.model.InstanceObject;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.item.service.impl.ItemServiceImpl;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.model.SceneObject;
import com.game.gameserver.module.skill.dao.SkillMapper;
import com.game.gameserver.module.skill.entity.Skill;
import com.game.gameserver.module.skill.manager.SkillManager;
import com.game.gameserver.module.skill.model.PlayerSkill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author xuewenkang
 * @date 2020/6/22 21:50
 */
public enum  PlayerState implements State<PlayerObject> {
    /*** 角色状态 */
    /** 存活状态 */
    LIVE(){
        @Override
        public void enter(PlayerObject playerObject) {
            playerObject.setDead(false);
            // 回复所有数值
            playerObject.revert();
        }

        @Override
        public void update(PlayerObject playerObject) {
            // 半血以下 自动使用回复药剂

        }

        @Override
        public void exit(PlayerObject playerObject) {

        }
    },
    /** 死亡状态 */
    DEAD(){
        /** 死亡时间 */
        private final static String DEAD_TIME  = "DEAD_TIME";
        /** 复活时间 */
        private final static String REVIVE = "REVIVE_TIME";
        @Override
        public void enter(PlayerObject playerObject) {
            playerObject.setDead(true);
            // 设置死亡时间和复活时间

        }

        @Override
        public void update(PlayerObject playerObject) {

        }

        @Override
        public void exit(PlayerObject playerObject) {

        }
    },

    /** 行为状态 */
    /**AI操控 自动攻击 */
    AI_AUTO_ATTACK(){
        @Override
        public void enter(PlayerObject playerObject) {

        }

        @Override
        public void update(PlayerObject playerObject) {
            // 玩家是否死亡
            if(playerObject.isDead()){
                return;
            }
            Unit unit = null;
            // 是否有攻击目标
            if(playerObject.getUnit()==null){
                // 搜寻攻击目标
                // 玩家在副本
                if(playerObject.getInstanceId()!=null){
                    // 获取场景中的一个怪物
                    InstanceObject instanceObject = InstanceManager.instance.getInstanceMap()
                            .get(playerObject.getInstanceId());
                    if(instanceObject==null){
                        return;
                    }
                    // 副本已经通关
                    if(instanceObject.isRecovery()){
                        return;
                    }
                    // 副本中已经没有怪物了
                    if(instanceObject.isEmptyMonster()){
                        return;
                    }
                    // 可选怪物列表
                    List<Long> monsterIds = instanceObject.getCurrMonsters();
                    for(Long monsterId:monsterIds){
                        MonsterObject monsterObject = MonsterManager.instance.getMonster(monsterId);
                        // 怪物不存在或死亡
                        if(monsterObject!=null&&!monsterObject.isDead()){
                            unit = monsterObject;
                            break;
                        }
                    }
                }
                else{
                    // 玩家在普通场景中
                    SceneObject sceneObject = SceneManager.instance.getSceneObject(playerObject
                            .getPlayer().getSceneId());
                    if(sceneObject==null){
                        return;
                    }
                    // 获取怪物列表
                    Map<Long,Long> map = sceneObject.getMonsterObjectMap();
                    for(Map.Entry<Long,Long> entry:map.entrySet()){
                        MonsterObject monsterObject = MonsterManager.instance.getMonster(entry.getKey());
                        // 怪物不存在或死亡
                        if(monsterObject!=null&&!monsterObject.isDead()){
                            unit = monsterObject;
                            break;
                        }
                    }
                }
            }
            if(unit==null){
                return;
            }
            // 获取可用技能
            PlayerSkill playerSkill = SkillManager.instance.getPlayerSkill(playerObject.getPlayer().getId());
            if(playerSkill==null){
                return;
            }
            // 可用技能列表
            List<Skill> skills = new ArrayList<>();
            // 获取玩家CD容器
            UnitCoolTime unitCoolTime = CoolTimeManager.instance.getPlayerCoolTime(playerObject.getPlayer().getId());
            if(unitCoolTime!=null){
                for(Skill skill:playerSkill.getSkillList()){
                    if(unitCoolTime.getCoolTime(skill.getSkillId())!=null){
                        continue;
                    }
                    skills.add(skill);
                }
            }
            // 随机选取技能
            if(skills.size()==0){
                return;
            }
            Random random = new Random();
            // 技能的ID
            int i = random.nextInt(skills.size()-1);
            // 主动攻击敌人
            Skill skill = skills.get(i);
            FighterServiceImpl.instance.playerAttack(
                    playerObject.getPlayer().getId(),
                    unit.getUnitId(),
                    unit.getUnitType(),
                    skill.getSkillId()
            );
        }

        @Override
        public void exit(PlayerObject playerObject) {

        }
    },
    /** 玩家操控 */
    PLAYER_CONTROL(){
        @Override
        public void enter(PlayerObject playerObject) {

        }
        @Override
        public void update(PlayerObject playerObject) {

        }
        @Override
        public void exit(PlayerObject playerObject) {

        }
    },
    /** 自动攻击 */
    AUTO_ATTACK(){

        @Override
        public void enter(PlayerObject playerObject) {

        }

        @Override
        public void update(PlayerObject playerObject) {
            if(playerObject.getUnit()==null){

            }
        }

        @Override
        public void exit(PlayerObject playerObject) {

        }
    }
}
