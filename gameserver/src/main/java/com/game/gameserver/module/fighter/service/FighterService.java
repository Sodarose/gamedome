package com.game.gameserver.module.fighter.service;

import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.entity.CreatureType;
import com.game.gameserver.common.fsm.state.monster.MonsterState;
import com.game.gameserver.common.fsm.state.player.PlayerState;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.event.MonsterDeadEvent;
import com.game.gameserver.module.fighter.type.FighterModeEnum;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.pet.model.Pet;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.skill.model.PlayerSkill;
import com.game.gameserver.module.skill.model.Skill;
import com.game.gameserver.module.skill.service.SkillService;
import com.game.gameserver.module.skill.type.SkillRange;
import com.game.gameserver.module.skill.type.SkillType;
import com.game.gameserver.module.team.model.Team;
import com.game.gameserver.module.team.service.TeamService;
import com.game.gameserver.net.modelhandler.skill.SkillCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 战斗服务
 *
 * @author xuewenkang
 * @date 2020/7/17 10:53
 */
@Service
public class FighterService {

    @Autowired
    private TeamService teamService;
    @Autowired
    private SkillService skillService;

    public static FighterService instance;

    public FighterService(){
        instance = this;
    }
    /**
     * 更改状态模式
     *
     * @param player
     * @param model
     * @return void
     */
    public void changeFighterMode(Player player,int model){
        FighterModeEnum fighterModeEnum = null;
        for(FighterModeEnum fighterMode:FighterModeEnum.values()){
            if(fighterMode.getType()==model){
                fighterModeEnum= fighterMode;
            }
        }
        if(fighterModeEnum==null){
            NotificationHelper.notifyPlayer(player,"不存在该模式");
            return;
        }
        player.setFighterModeEnum(fighterModeEnum);
    }


    /**
     * 玩家发动攻击
     *
     * @param player
     * @param targetId
     * @param targetType
     * @param skillId
     * @return void
     */
    public void playerFight(Player player,long targetId,int targetType,int skillId){
        // 验证自身状态
        if(player.isDead()){
            NotificationHelper.notifyPlayer(player,"您已经死亡，不能释放技能");
            return;
        }
        // 搜索技能
        // 技能获取与检测
        Skill skill = player.getSkill(skillId);
        if (skill == null) {
            NotificationHelper.notifyPlayer(player, "你没有该技能");
            return;
        }
        if (player.getSkillCdMap().get(skillId) != null || player.getMp() < skill.getConsume()) {
            NotificationHelper.notifyPlayer(player, "技能蓝量不足");
            return;
        }

        Scene scene = player.getScene();
        if(scene==null){
            NotificationHelper.notifyPlayer(player,"获取场景失败");
            return;
        }
        // 确定目标
        // 寻敌检测
        if (skill.getRange() == SkillRange.NONE.getType()) {
            // 释放技能  无目标类型技能
            playerUseNoneSkill(player,skill);
        }
        // AOE技能
        if (skill.getRange() == SkillRange.AOE.getType()) {
            // 释放技能  使用AOE技能
            playerUseAoeSkill(player,skill);
        }
        // 玩家使用单体技能
        if (skill.getRange() == SkillRange.INDIVIDUAL.getType()) {
            // 目标是玩家 则攻击玩家
            if(targetType==CreatureType.PLAYER.getType()){
                Player target = scene.getPlayerMap().get(targetId);
                if(target==null||target.isDead()){
                    NotificationHelper.notifyPlayer(player,"目标不存在或者已死亡");
                    return;
                }
                playerAttackPlayer(player,target,skill);
            }

            // 目标是怪物 则攻击怪物
            if(targetType==CreatureType.MONSTER.getType()){
                Monster monster = scene.getMonsterMap().get(targetId);
                if(monster==null||monster.isDead()){
                    NotificationHelper.notifyPlayer(player,"目标不存在或者已死亡");
                    return;
                }
                playerAttackMonster(player,monster,skill);
            }

            // 目标是召唤物 则攻击召唤物
            if(targetType==CreatureType.PET.getType()){
                Pet pet = scene.getPetMap().get(targetId);
                if(pet==null||pet.isDead()){
                    NotificationHelper.notifyPlayer(player,"目标不存在或者已死亡");
                    return;
                }
                playerAttackPet(player,pet,skill);
            }
        }
    }

    /**
     * 怪物攻击
     *
     * @param monster
     * @param targetId
     * @param targetType
     * @param skillId
     * @return void
     */
    public void monsterFight(Monster monster,long targetId,int targetType,int skillId){
        if(monster.isDead()){
            return;
        }
        Skill skill = monster.getSkill(skillId);
        if(skill==null){
            return;
        }

        if (monster.getSkillCdMap().get(skillId) != null) {
            return;
        }

        Scene scene =  monster.getScene();
        if(scene==null){
            return;
        }
        // 寻敌检测
        if (skill.getRange() == SkillRange.NONE.getType()) {
        }
        // AOE技能
        if (skill.getRange() == SkillRange.AOE.getType()) {
            monsterUseAOESkill(monster,skill);
        }
        // 玩家使用单体技能
        if (skill.getRange() == SkillRange.INDIVIDUAL.getType()) {
            // 目标是玩家 则攻击玩家
            if(targetType==CreatureType.PLAYER.getType()){
                Player player = scene.getPlayerMap().get(targetId);
                if(player==null){
                    return;
                }
                monsterAttackPlayer(monster,player,skill);
            }

            // 目标是怪物 则攻击怪物
            if(targetType==CreatureType.MONSTER.getType()){
                return;
            }

            // 目标是召唤物 则攻击召唤物
            if(targetType==CreatureType.PET.getType()){
                Pet pet = scene.getPetMap().get(targetId);
                if(pet==null){
                    return;
                }
                monsterAttackPet(monster,pet,skill);
            }
        }
    }

    private void monsterAttackPlayer(Monster monster,Player target,Skill skill){
        skillService.castSkill(monster,target,skill);
        if(target.isDead()){

        }
        // 技能进入CD
    }

    private void monsterAttackPet(Monster monster,Pet pet,Skill skill){
        skillService.castSkill(monster,pet,skill);
        if(pet.isDead()){

        }
    }

    private void monsterAttackMonster(Monster monster,Pet pet,Skill skill){

    }

    private void monsterUseAOESkill(Monster monster,Skill skill){
        Scene scene = monster.getScene();
        if(scene==null){
            return;
        }
        // 尝试攻击场景中的所有生物
        scene.getPlayerMap().values().forEach(player -> {
            monsterAttackPlayer(monster,player,skill);
        });

        scene.getPetMap().values().forEach(pet -> {
            monsterAttackPet(monster,pet,skill);
        });
    }


    /**
     * @param initiator
     * @param skill
     * @return void
     */
    private void playerUseNoneSkill(Player initiator, Skill skill) {
        skillService.castSkill(initiator,null,skill);
    }

    /**
     * @param initiator
     * @param skill
     * @return void
     */
    private void playerUseAoeSkill(Player initiator, Skill skill) {
        Scene scene = initiator.getScene();
        if(scene==null){
            NotificationHelper.notifyPlayer(initiator,"场景数据错误");
            return;
        }
        // 尝试攻击场景中的所有生物
        scene.getPlayerMap().values().forEach(player -> {
            playerAttackPlayer(initiator,player,skill);
        });

        scene.getMonsterMap().values().forEach(monster -> {
            playerAttackMonster(initiator,monster,skill);
        });

        //
        scene.getPetMap().values().forEach(pet -> {
            playerAttackPet(initiator,pet,skill);
        });
    }

    /**
     * 玩家攻击玩家
     *
     * @param initiator
     * @param target
     * @param skill
     * @return void
     */
    private void playerAttackPlayer(Player initiator,Player target,Skill skill){
        // 验证该玩家是否可以被空寂
        if(!verifyAttackAblePlayer(initiator,target)){
            return;
        }
        // 对玩家释放技能
        skillService.castSkill(initiator,target,skill);
        // 判断目标是否死亡
        if(target.isDead()){

        }
    }

    private boolean verifyAttackAblePlayer(Player player,Player target){
        // 验证此时玩家的攻击模式
        if(player.getFighterModeEnum()==FighterModeEnum.PEACE){
            NotificationHelper.notifyPlayer(player,"处于组队模式 无法攻击玩家");
            return false;
        }
        if(player.getFighterModeEnum()==FighterModeEnum.TEAM){
            // 验证对方是否是队伍中的人
            if(player.getTeamId()==null){
                NotificationHelper.notifyPlayer(player,"组队模式出现数据错误");
                return false;
            }
            Team team = teamService.getTeam(player.getTeamId());
            if(team==null){
                NotificationHelper.notifyPlayer(player,"组队模式出现数据错误");
                return false;
            }
            if(team.getMemberMap().get(target.getId())!=null){
                NotificationHelper.notifyPlayer(player,"对方是你的队友 无法攻击对方");
                return false;
            }
        }
        if(player.getFighterModeEnum()==FighterModeEnum.EVIL){
            return false;
        }
        if(player.getFighterModeEnum()==FighterModeEnum.ALL){
            return true;
        }
        return false;
    }

    /**
     * 玩家攻击怪物
     *
     * @param player
     * @param monster
     * @param skill
     * @return void
     */
    private void playerAttackMonster(Player player,Monster monster,Skill skill){
        // 对玩家释放技能
        skillService.castSkill(player,monster,skill);
        // 判断目标是否死亡
        if(monster.isDead()){
            MonsterDeadEvent monsterDeadEvent = new MonsterDeadEvent(player,monster);
            EventBus.EVENT_BUS.fire(monsterDeadEvent);
        }else{
            if(monster.getTarget()==null){
                monster.setTarget(player);
                monster.changeState(MonsterState.MONSTER_ATTACK);
            }
           if(player.getTarget()==null){
               player.setTarget(monster);
               player.changeState(PlayerState.PLAYER_ATTACK);
           }
        }
    }

    /**
     * 玩家攻击召唤物
     *
     * @param player
     * @param pet
     * @param skill
     * @return void
     */
    private void playerAttackPet(Player player,Pet pet,Skill skill){
        // 获取召唤物主人，通过判断主人是否能够攻击再进行攻击
        Player target = pet.getMaster();
        if(target==null){
            NotificationHelper.notifyPlayer(player,"数据错误，召唤物没有召唤者");
            return;
        }
        // 战争主人是否被攻击
        if(!verifyAttackAblePlayer(player,target)){
            return;
        }
        // 对宠物释放技能
        skillService.castSkill(player,pet,skill);
        if(pet.isDead()){

        }
    }


    /**
     * 召唤物发动战斗
     *
     * @param pet
     * @param targetId
     * @param skillId
     * @return void
     */
    public void petFight(Pet pet, long targetId,int targetType, int skillId) {
        // 验证自身状态
        if(pet.isDead()){
            return;
        }
        // 搜索技能
        // 技能获取与检测
        Skill skill = pet.getSkill(skillId);
        if (skill == null) {
            return;
        }
        if (pet.getSkillCdMap().get(skillId) != null || pet.getMp() < skill.getConsume()) {
            return;
        }

        Scene scene = pet.getScene();
        if(scene==null){
            return;
        }
        // 确定目标范围
        // 寻敌检测
        if (skill.getRange() == SkillRange.NONE.getType()) {
            // 释放技能  无目标类型技能
        }
        // AOE技能
        if (skill.getRange() == SkillRange.AOE.getType()) {
            // 释放技能  使用AOE技能
            petUseAoeSkill(pet,skill);
        }
        // 召唤物使用单体技能
        if (skill.getRange() == SkillRange.INDIVIDUAL.getType()) {
            // 目标是玩家 则攻击玩家
            if(targetType==CreatureType.PLAYER.getType()){
                Player target = scene.getPlayerMap().get(targetId);
                if(target==null||target.isDead()){
                    return;
                }
                petAttackPlayer(pet,target,skill);
            }

            // 目标是怪物 则攻击怪物
            if(targetType==CreatureType.MONSTER.getType()){
                Monster monster = scene.getMonsterMap().get(targetId);
                if(monster==null||monster.isDead()){
                    return;
                }
                petAttackMonster(pet,monster,skill);
            }

            // 目标是召唤物 则攻击召唤物
            if(targetType==CreatureType.PET.getType()){
                Pet target = scene.getPetMap().get(targetId);
                if(target==null||target.isDead()){
                    return;
                }
                petAttackPet(pet,target,skill);
            }
        }
    }

    private void petUseAoeSkill(Pet pet,Skill skill){
        Scene scene = pet.getScene();
        if(scene==null){
            return;
        }
        // 尝试攻击场景中的所有生物
        scene.getPlayerMap().values().forEach(player -> {
            petAttackPlayer(pet,player,skill);
        });

        scene.getMonsterMap().values().forEach(monster -> {
            petAttackMonster(pet,monster,skill);
        });

        //
        scene.getPetMap().values().forEach(target -> {
            petAttackPet(pet,target,skill);
        });
    }

    private void petAttackPlayer(Pet pet,Player player,Skill skill){
        skillService.castSkill(pet,player,skill);
        if(player.isDead()){

        }
    }

    private void petAttackMonster(Pet pet,Monster monster,Skill skill){
        skillService.castSkill(pet,monster,skill);
        if(monster.isDead()){

        }
    }

    private void petAttackPet(Pet pet,Pet target,Skill skill){
        skillService.castSkill(pet,target,skill);
        if(target.isDead()){

        }
    }
}
