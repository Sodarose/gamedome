package com.game.gameserver.module.fighter.service.impl;

import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.entity.UnitType;
import com.game.gameserver.module.fighter.service.FighterService;
import com.game.gameserver.module.fighter.type.FighterModeEnum;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.pet.entity.Pet;
import com.game.gameserver.module.pet.manager.PetManager;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.Message;
import com.game.protocol.TipProtocol;
import com.game.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/6/22 10:07
 */
@Service
public class FighterServiceImpl implements FighterService {
    public static FighterService instance;

    public FighterServiceImpl() {
        instance = this;
    }

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private MonsterManager monsterManager;
    @Autowired
    private PetManager petManager;

    /**
     * 玩家主动攻击
     *
     * @param playerId 玩家Id
     * @param targetId 目标Id
     * @param unitType 目标类型
     * @param skillId  技能Id
     * @return void
     */
    @Override
    public void playerAttack(long playerId, long targetId, int unitType, int skillId) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return;
        }
        if (unitType == UnitType.PLAYER) {
            playerAttackPlayer(playerId, targetId, skillId);
        }
        if (unitType == UnitType.MONSTER) {
            playerAttackMonster(playerId, targetId, skillId);
        }
        if (unitType == UnitType.PET) {
            playerAttackPet(playerId, targetId, skillId);
        }
    }

    /**
     * 怪物主动攻击
     *
     * @param monsterId 怪物Id
     * @param targetId  目标Id
     * @param unitType  目标类型
     * @param skillId   技能Id
     * @return void
     */
    @Override
    public void monsterAttack(long monsterId, long targetId, int unitType, int skillId) {
        MonsterObject monsterObject = monsterManager.getMonster(monsterId);
        if (monsterObject == null) {
            return;
        }
        if (unitType == UnitType.PLAYER) {
            monsterAttackPlayer(monsterId, targetId, skillId);
        }
        if (unitType == UnitType.MONSTER) {
            monsterAttackMonster(monsterId, targetId, skillId);
        }
        if (unitType == UnitType.PET) {
            monsterAttackPet(monsterId, targetId, skillId);
        }
    }

    /**
     * 宝宝主动攻击
     *
     * @param petId
     * @param targetId
     * @param unitType
     * @param skillId
     * @return void
     */
    @Override
    public void petAttack(long petId, long targetId, int unitType, int skillId) {
        Pet pet = petManager.getPet(petId);
        if (pet == null) {
            return;
        }
        if (unitType == UnitType.PLAYER) {
            petAttackPlayer(petId, targetId, skillId);
        }
        if (unitType == UnitType.MONSTER) {
            petAttackMonster(petId, targetId, skillId);
        }
        if (unitType == UnitType.PET) {
            petAttackPet(petId, targetId, skillId);
        }
    }


    /**
     * 玩家攻击玩家
     *
     * @param playerId
     * @param targetId
     * @param skillId
     * @return void
     */
    private void playerAttackPlayer(Long playerId, Long targetId, int skillId) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return;
        }
        PlayerObject targetObject = playerManager.getPlayerObject(targetId);
        if (targetObject == null) {
            Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0, TipProtocol.TipMessage
                    .newBuilder().setMsg("目标不存在").build().toByteArray());
            playerObject.getChannel().writeAndFlush(message);
            return;
        }
        // 不在同一场景
        if(playerObject.getPlayer().getSceneId().equals(targetObject.getPlayer().getSceneId())){
            Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0, TipProtocol.TipMessage
                    .newBuilder().setMsg("不在同一场景").build().toByteArray());
            playerObject.getChannel().writeAndFlush(message);
            return;
        }
        // 处于和平模式
        if(playerObject.getFighterModeEnum()== FighterModeEnum.PEACE){
            Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0, TipProtocol.TipMessage
                    .newBuilder().setMsg("您处于和平模式下 无法进行攻击").build().toByteArray());
            playerObject.getChannel().writeAndFlush(message);
            return;
        }
        // 对方处于和平模式
        if(targetObject.getFighterModeEnum()== FighterModeEnum.PEACE){
            Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0, TipProtocol.TipMessage
                    .newBuilder().setMsg("对方处于和平模式 无法进行攻击").build().toByteArray());
            playerObject.getChannel().writeAndFlush(message);
            return;
        }
        // 双方同个队伍
        if(playerObject.getTeamId()!=null&&playerObject.getTeamId().equals(targetObject.getTeamId())){
            Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0, TipProtocol.TipMessage
                    .newBuilder().setMsg("同一个队伍 无法进行攻击").build().toByteArray());
            playerObject.getChannel().writeAndFlush(message);
            return;
        }
        // 获得技能ID
        SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
        // 技能类
        // 伤害技能
        //
    }

    /**
     * 玩家攻击怪物
     *
     * @param playerId
     * @param targetId
     * @param skillId
     * @return void
     */
    private void playerAttackMonster(Long playerId, Long targetId, int skillId) {

    }

    /**
     * 玩家攻击宝宝
     *
     * @param playerId
     * @param targetId
     * @param skillId
     * @return void
     */
    private void playerAttackPet(Long playerId, Long targetId, int skillId) {

    }

    private void monsterAttackPlayer(Long monsterId, Long targetId, int skillId) {

    }

    private void monsterAttackPet(Long monsterId, Long targetId, int skillId) {

    }

    private void monsterAttackMonster(Long monsterId, Long targetId, int skillId) {

    }

    private void petAttackMonster(Long petId, Long targetId, int skillId) {

    }

    private void petAttackPlayer(Long petId, Long targetId, int skillId) {

    }

    private void petAttackPet(Long petId, Long targetId, int skillId) {

    }
}
