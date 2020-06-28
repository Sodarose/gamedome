package com.game.gameserver.module.fighter.service.impl;

import com.game.gameserver.common.config.PetConfig;
import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.entity.UnitType;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.module.ai.state.monster.MonsterState;
import com.game.gameserver.module.ai.state.pet.PetState;
import com.game.gameserver.module.ai.state.player.PlayerState;
import com.game.gameserver.module.cooltime.entity.CoolTime;
import com.game.gameserver.module.cooltime.entity.UnitCoolTime;
import com.game.gameserver.module.cooltime.manager.CoolTimeManager;
import com.game.gameserver.module.fighter.entity.FighterContext;
import com.game.gameserver.module.fighter.service.FighterService;
import com.game.gameserver.module.fighter.type.FighterModeEnum;
import com.game.gameserver.module.instance.manager.InstanceManager;
import com.game.gameserver.module.instance.model.InstanceObject;
import com.game.gameserver.module.monster.event.MonsterDeadEvent;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.monster.type.MonsterType;
import com.game.gameserver.module.pet.entity.Pet;
import com.game.gameserver.module.pet.manager.PetManager;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.model.SceneObject;
import com.game.gameserver.module.skill.type.SkillType;
import com.game.gameserver.module.team.entity.Team;
import com.game.gameserver.module.team.manager.TeamManager;
import com.game.protocol.FighterProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/6/22 10:07
 */
@Service
public class FighterServiceImpl implements FighterService {


    private final static Logger logger = LoggerFactory.getLogger(FighterServiceImpl.class);

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

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
    @Autowired
    private TeamManager teamManager;
    @Autowired
    private SceneManager sceneManager;
    @Autowired
    private InstanceManager instanceManager;
    @Autowired
    private CoolTimeManager coolTimeManager;


    /**
     * 玩家请求发起攻击
     *
     * @param playerId
     * @param targetId
     * @param unitType
     * @return com.game.protocol.FighterProtocol.AttackRes
     */
    @Override
    public FighterProtocol.AttackRes playerAttackReq(long playerId, long targetId, int unitType) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return FighterProtocol.AttackRes.newBuilder().setCode(1000).setMsg("非法用户").build();
        }
        if (playerObject.isDead()) {
            return FighterProtocol.AttackRes.newBuilder().setCode(1001).setMsg("您已经死亡 请等待复活").build();
        }
        Unit unit = null;
        // 设置目标为攻击目标
        if (unitType == UnitType.PLAYER) {
            unit = playerManager.getPlayerObject(targetId);
        }
        if (unitType == UnitType.MONSTER) {
            unit = monsterManager.getMonster(targetId);
        }
        if (unitType == UnitType.PET) {
            unit = petManager.getPet(targetId);
        }
        if (unit == null) {
            return FighterProtocol.AttackRes.newBuilder().setCode(1003).setMsg("目标丢失").build();
        }
        if (unit.isDead()) {
            return FighterProtocol.AttackRes.newBuilder().setCode(1004).setMsg("该单位已经死亡了").build();
        }
        playerObject.setAttackTarget(unit);
        // 转换成攻击状态
        if (!playerObject.getCurrState().equals(PlayerState.ATTACK)) {
            playerObject.changeState(PlayerState.ATTACK);
        }
        return FighterProtocol.AttackRes.newBuilder().setCode(0).setMsg("success").build();
    }

    /**
     * 玩家释放技能
     *
     * @param playerId 玩家Id
     * @param targetId 目标Id
     * @param unitType 目标类型
     * @return void
     */
    @Override
    public void playerUseSkill(long playerId, long targetId, int unitType, int skillId) {
        // 玩家是否存在
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null || playerObject.isDead()) {
            return;
        }
        // 获得技能基础对象
        SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
        if (skillConfig == null) {
            return;
        }
        // 验证技能CD 时间
        if (verifySkillCoolTime(playerId, skillConfig)) {
            logger.info("{} 释放技能{} 失败，该技能冷却中",playerObject.getPlayer().getName(),skillConfig.getName());
            return;
        }
        // 玩家释放攻击性技能
        if (skillConfig.getSkillType() == SkillType.DAMAGE) {
            playerCastDamageSkill(playerObject, targetId, unitType, skillConfig);
        }
        // 玩家释放辅助性技能
        if (skillConfig.getSkillType() == SkillType.ASSIST) {
            playerCastAssistSkill(playerObject, targetId, unitType, skillConfig);
        }
        // 玩家释放召唤系技能
        if (skillConfig.getSkillType() == SkillType.SUMMONER) {
            playerCastSummonerSkill(playerObject, skillConfig);
        }
    }


    /**
     * 玩家释放伤害型技能
     *
     * @param playerObject
     * @param targetId
     * @param unitType
     * @param skillConfig
     * @return void
     */
    private void playerCastDamageSkill(PlayerObject playerObject, long targetId, int unitType, SkillConfig skillConfig) {
        // 玩家释放范围性伤害技能
        if (skillConfig.getSkillRange() == SkillType.AREA) {
            playerCastAreaDamageSkill(playerObject, skillConfig);
        }

        // 玩家释放单体伤害技能
        if (skillConfig.getSkillRange() == SkillType.TARGET) {
            // 判断技能目标
            // 对玩家释放
            if (unitType == UnitType.PLAYER) {
                PlayerObject target = playerManager.getPlayerObject(targetId);
                if (target == null||target.isDead()) {
                    return;
                }
                playerCastDamageSkill2Player(playerObject, target, skillConfig);
            }
            // 对怪物释放
            if (unitType == UnitType.MONSTER) {
                MonsterObject monsterObject = monsterManager.getMonster(targetId);
                if (monsterObject == null||monsterObject.isDead()) {
                    return;
                }
                playerCastDamageSkill2Monster(playerObject, monsterObject, skillConfig);
            }
            // 对宝宝释放
            if (unitType == UnitType.PET) {
                Pet pet = petManager.getPet(targetId);
                if (pet == null||pet.isDead()) {
                    return;
                }
                playerCastDamageSkill2Pet(playerObject, pet, skillConfig);
            }
        }
        // 技能进入CD
        skillEntryCoolTime(playerObject.getUnitId(), skillConfig);
    }

    /**
     * 玩家释放范围性伤害技能(目前范围型技能只能攻击怪物)
     *
     * @param playerObject
     * @param skillConfig
     * @return void
     */
    private void playerCastAreaDamageSkill(PlayerObject playerObject, SkillConfig skillConfig) {
        if (playerObject == null || playerObject.isDead()) {
            return;
        }
        // 技能范围内目标(当前场景中所有怪物)
        List<MonsterObject> targets = new ArrayList<>();
        // 获取技能范围内的敌人
        // 范围内怪物
        if (playerObject.getInstanceId() == null) {
            // 一般场景中 获取场景中的所有怪物
            SceneObject sceneObject = sceneManager.getSceneObject(playerObject.getPlayer().getSceneId());
            if (sceneObject == null) {
                return;
            }
            Map<Long, Long> map = sceneObject.getMonsterObjectMap();
            for (Map.Entry<Long, Long> entry : map.entrySet()) {
                MonsterObject monsterObject = monsterManager.getMonster(entry.getValue());
                // 排除死亡的怪物和已经回收的怪物
                if (monsterObject == null || monsterObject.isDead()) {
                    continue;
                }
                targets.add(monsterObject);
            }
        } else {
            // 玩家在副本中 获取副本所有怪物
            InstanceObject instanceObject = instanceManager.getInstance(playerObject.getInstanceId());
            if (instanceObject == null) {
                return;
            }
            List<Long> currMonsters = instanceObject.getCurrMonsters();
            for (long monsterId : currMonsters) {
                MonsterObject monsterObject = monsterManager.getMonster(monsterId);
                if (monsterObject == null || monsterObject.isDead()) {
                    continue;
                }
                targets.add(monsterObject);
            }
        }
        // 范围内玩家
        // 范围内宝宝
        // 攻击怪物
        for (MonsterObject monsterObject : targets) {
            FighterContext context = playerAttackMonster(playerObject, monsterObject, skillConfig);
            // 推送战报
            pushFighterContext(context);
        }
    }

    /**
     * 玩家释放伤害技能攻击玩家
     *
     * @param playerObject
     * @param target
     * @param skillConfig
     * @return void
     */
    private void playerCastDamageSkill2Player(PlayerObject playerObject, PlayerObject target, SkillConfig skillConfig) {
        if (playerObject == null || playerObject.isDead()) {
            return;
        }
        if (target == null || target.isDead()) {
            return;
        }
        // 验证该玩家是否可以被攻击
        if (!verifyAttackPlayer(playerObject, target)) {
            return;
        }
        FighterContext fighterContext = playerAttackPlayer(playerObject, target, skillConfig);
        // 推送战报
        pushFighterContext(fighterContext);
    }

    /**
     * 玩家释放伤害技能攻击怪物
     *
     * @param playerObject
     * @param target
     * @param skillConfig
     * @return void
     */
    private void playerCastDamageSkill2Monster(PlayerObject playerObject, MonsterObject target, SkillConfig skillConfig) {
        // 校验是否能够攻击该怪物
        if (!verifyAttackMonster(playerObject, target)) {
            return;
        }
        FighterContext fighterContext = playerAttackMonster(playerObject, target, skillConfig);
        // 推送战报
        pushFighterContext(fighterContext);
    }

    /**
     * 玩家攻击怪物
     *
     * @param playerObject
     * @param monsterObject
     * @param skillConfig
     * @return com.game.gameserver.module.fighter.entity.FighterContext 战报
     */
    private FighterContext playerAttackMonster(PlayerObject playerObject,
                                               MonsterObject monsterObject, SkillConfig skillConfig) {
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", playerObject.getPlayerBattle().getAttack() + "");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        int hurt = 0;
        try {
            hurt = (int) Double.parseDouble(jse.eval(ruleFormula) + "");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // 真正的伤害
        int ruleHurt = hurt - monsterObject.getDefense();
        // 无法击破防御
        if (ruleHurt <= 0) {
            ruleHurt = 1;
        }
        // 测试用
        ruleHurt = 50;
        // 扣除怪物血量
        monsterObject.reduceCurrHp(ruleHurt);
        // 构建战报
        FighterContext fighterContext = new FighterContext();
        // 怪物血量为空
        if (monsterObject.isCurrHpEmpty()) {
            // 设置怪物为死亡状态
            monsterObject.changeState(MonsterState.DEAD);
            // 玩家切换状态
            playerObject.changeState(PlayerState.TAKEOFF);
            // 发出怪物死亡事件
            MonsterDeadEvent event = new MonsterDeadEvent(monsterObject.getId());
            EventBus.EVENT_BUS.fire(event);
        }
        logger.info("{} 使用 {} 对 {} 造成 {} 伤害", playerObject.getPlayer().getName(), skillConfig.getName()
                , monsterObject.getMonsterConfig().getName(), ruleHurt);
        // 怪物未死亡 进入攻击状态
        if (!monsterObject.isDead() && !monsterObject.getCurrState().equals(MonsterState.ATTACK)) {
            // 设置怪物仇恨
            monsterObject.setHateUnit(playerObject);
            // 切换为攻击状态
            monsterObject.changeState(MonsterState.ATTACK);
        }
        return fighterContext;
    }

    /**
     * 玩家攻击玩家
     *
     * @param playerObject
     * @param target
     * @param skillConfig
     * @return com.game.gameserver.module.fighter.entity.FighterContext
     */
    private FighterContext playerAttackPlayer(PlayerObject playerObject, PlayerObject target, SkillConfig skillConfig) {
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", playerObject.getPlayerBattle().getAttack() + "");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        // 计算伤害
        int hurt = 0;
        try {
            hurt = (int) Double.parseDouble(jse.eval(ruleFormula) + "");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // 真正的伤害 伤害-防御
        int ruleHurt = hurt - target.getPlayerBattle().getDefense();
        if (ruleHurt <= 0) {
            ruleHurt = 1;
        }
        // 测试 强制伤害为1
        ruleHurt = 1;
        // 扣除敌方血量
        target.getPlayerBattle().subtractCurrHp(ruleHurt);
        // 判断血量是否为空
        boolean result = target.getPlayerBattle().isCurrHpEmpty();
        if (result) {
            // 设置对方死亡
            target.changeState(PlayerState.DEAD);
            // 清空攻击目标
            playerObject.setAttackTarget(null);
            // 进入脱战状态
            playerObject.changeState(PlayerState.TAKEOFF);
        }
        // 测试用 被攻击者进入战斗状态
        if (!target.isDead() && !target.getCurrState().equals(PlayerState.ATTACK)) {
            // 对方将你列入攻击目标
            target.setAttackTarget(playerObject);
            // 对方进入攻击状态
            target.changeState(PlayerState.ATTACK);
        }
        // 返回战斗上下文
        FighterContext fighterContext = new FighterContext();
        logger.info("{} 使用 {} 攻击 {} 造成伤害 {}", playerObject.getPlayer().getName(), skillConfig.getName(),
                target.getPlayer().getName(), ruleHurt);
        return fighterContext;
    }


    /**
     * 玩家释放伤害技能攻击宝宝
     *
     * @param playerObject
     * @param pet
     * @param skillConfig
     * @return void
     */
    private void playerCastDamageSkill2Pet(PlayerObject playerObject, Pet pet, SkillConfig skillConfig) {
        if (verifyAttackPet(playerObject, pet)) {
            return;
        }
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", playerObject.getPlayerBattle().getAttack() + "");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        int hurt = 0;
        try {
            hurt = (int) Double.parseDouble(jse.eval(ruleFormula) + "");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // 真正的伤害
        int ruleHurt = hurt - pet.getDefense();
        // 无法击破防御
        if (ruleHurt <= 0) {
            ruleHurt = 1;
        }
        // 扣除宝宝血量
        pet.reduceCurrHp(ruleHurt);
        // 构建战报
        FighterContext fighterContext = new FighterContext();
        // 宝宝血量为空
        if (pet.isCurrHpEmpty()) {
            // 设置宝宝为死亡状态
            pet.changeState(PetState.DEAD);
        }
        logger.info("{} 使用 {} 对 {} 造成 {} 伤害", playerObject.getPlayer().getName(), skillConfig.getName()
                , pet.getPetConfigId(), ruleHurt);
        // 下面为测试
        if (!pet.isDead() && !pet.getCurrState().equals(MonsterState.ATTACK)) {
            // 设置宝宝仇恨对象
            pet.setHateUnit(playerObject);
            // 进入攻击状态
            pet.changeState(PetState.ATTACK);
        }
    }


    /**
     * 验证宝宝是否可以被攻击
     *
     * @param playerObject
     * @param pet
     * @return boolean
     */
    private boolean verifyAttackPet(PlayerObject playerObject, Pet pet) {
        return true;
    }

    /**
     * 玩家释放辅助性技能
     *
     * @param playerObject
     * @param targetId
     * @param unitType
     * @param skillConfig
     * @return void
     */
    private void playerCastAssistSkill(PlayerObject playerObject, long targetId, int unitType, SkillConfig skillConfig) {
        // 技能目标
        List<PlayerObject> targets = new ArrayList<>();
        // 判断技能范围
        if (skillConfig.getSkillRange() == SkillType.AREA) {
            // 获取在同一场景下的队友
            Team team = teamManager.getTeamObject(playerObject.getTeamId());
            // 队伍不存在
            if (team == null) {
                targets.add(playerObject);
            } else {
                for (Long playerId : team.getMembers()) {
                    PlayerObject temp = playerManager.getPlayerObject(playerId);
                    if (temp == null) {
                        continue;
                    }
                    // 判断是否在同一个场景下
                    if (playerObject.getInstanceId() == null) {
                        // 同一场景
                        if (playerObject.getPlayer().getSceneId().equals(temp.getPlayer().getSceneId())) {
                            targets.add(temp);
                        }
                    } else {
                        // 同一副本
                        if (playerObject.getInstanceId().equals(temp.getInstanceId())) {
                            targets.add(temp);
                        }
                    }
                }
            }
        } else {
            // 单体辅助技能
            PlayerObject target = playerManager.getPlayerObject(targetId);
            if (target == null) {
                return;
            }
            targets.add(target);
        }
        // 治疗
        for (PlayerObject target : targets) {
            FighterContext fighterContext = playerAssistPlayer(playerObject, target, skillConfig);
            pushFighterContext(fighterContext);
        }
    }

    private FighterContext playerAssistPlayer(PlayerObject playerObject, PlayerObject target, SkillConfig skillConfig) {
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String[] ruleFormula = formula.split("\\|");
        if (ruleFormula.length < 2) {
            logger.info("{} 技能解析出错", skillConfig.getName());
            return null;
        }
        // 判断技能类型
        String type = ruleFormula[0];
        // 增幅的数值
        int value = Integer.parseInt(ruleFormula[1]);
        // 加血
        if (SkillType.HP.equals(type)) {
            target.getPlayerBattle().addCurrHp(value);
        }
        // 加蓝
        if (SkillType.MP.equals(type)) {
            target.getPlayerBattle().addCurrMp(value);
        }
        FighterContext fighterContext = new FighterContext();
        logger.info("{} 对 {} 释放技能 {} ，{} 的 {} 增加 {}",
                playerObject.getPlayer().getName(),
                target.getPlayer().getName(),
                skillConfig.getName(),
                target.getPlayer().getName(),
                type,
                value);
        return fighterContext;
    }

    /**
     * 玩家释放召唤性技能
     *
     * @param playerObject
     * @param skillConfig
     * @return void
     */
    private void playerCastSummonerSkill(PlayerObject playerObject, SkillConfig skillConfig) {
        // 解析技能
        String formula = skillConfig.getFormula();
        // 生成召唤物
        int petConfigId = Integer.parseInt(formula);
        // 获得宝宝基础配置
        PetConfig petConfig = StaticConfigManager.getInstance().getPetConfigMap().get(petConfigId);
        if(petConfig==null){
            return;
        }
        // 放入场景中
        if(playerObject.getInstanceId()==null){
            SceneObject sceneObject = sceneManager.getSceneObject(playerObject.getPlayer().getSceneId());
            if(sceneObject==null){
                return;
            }
            // 生成宝宝
            Pet pet = petManager.createPet(playerObject.getPlayer().getId(),petConfigId);
            // 放入场景
            sceneObject.getPetMap().put(pet.getId(),pet);
        }else{
            // 放入副本中
            InstanceObject instanceObject = instanceManager.getInstance(playerObject.getInstanceId());
            if(instanceObject==null){
                return;
            }
            Pet pet = petManager.createPet(playerObject.getPlayer().getId(),petConfigId);
            instanceObject.addPet(pet.getId());
        }
    }

    /**
     * 怪物释放技能
     *
     * @param monsterId 怪物Id
     * @param targetId  目标Id
     * @param unitType  目标类型
     * @param skillId   技能Id
     * @return void
     */
    @Override
    public void monsterUseSkill(long monsterId, long targetId, int unitType, int skillId) {
        MonsterObject monsterObject = monsterManager.getMonster(monsterId);
        if (monsterManager == null) {
            return;
        }
        // 获取技能基本数据
        SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
        if (skillConfig == null) {
            return;
        }
        // 技能是否进入CD
        if (verifySkillCoolTime(monsterId, skillConfig)) {
            return;
        }
        // 判断技能类型
        if (skillConfig.getSkillType() == SkillType.DAMAGE) {
            monsterCastDamageSkill(monsterObject, targetId, unitType, skillConfig);
        }
    }

    /**
     * 怪物释放伤害型技能
     *
     * @param monsterObject
     * @param targetId
     * @param unitType
     * @param skillConfig
     * @return void
     */
    private void monsterCastDamageSkill(MonsterObject monsterObject, long targetId, int unitType, SkillConfig skillConfig) {
        // 怪物释放范围性技能
        if (skillConfig.getSkillRange() == SkillType.AREA) {
            monsterCastAreaDamageSkill(monsterObject, skillConfig);
        }
        // 单体技能
        if (SkillType.TARGET == skillConfig.getSkillRange()) {
            // 攻击玩家
            if (unitType == UnitType.PLAYER) {
                PlayerObject playerObject = playerManager.getPlayerObject(targetId);
                if (playerObject == null || playerObject.isDead()) {
                    return;
                }
                monsterCastSkill2Player(monsterObject, playerObject, skillConfig);
            }
            // 攻击宝宝
            if (unitType == UnitType.MONSTER) {
                Pet pet = petManager.getPet(targetId);
                if (pet == null || pet.isDead()) {
                    return;
                }
                monsterCastSkill2Pet(monsterObject, pet, skillConfig);
            }
        }
        // 怪物技能进入CD
        skillEntryCoolTime(monsterObject.getUnitId(), skillConfig);
    }

    /**
     * 怪物释放范围性攻击技能
     *
     * @param monsterObject
     * @param skillConfig
     * @return void
     */
    private void monsterCastAreaDamageSkill(MonsterObject monsterObject, SkillConfig skillConfig) {
        // 被攻击的玩家列表
        List<PlayerObject> targets = new ArrayList<>();
        // 如果是场景怪物
        if (monsterObject.getMonsterType() == MonsterType.SCENE_MONSTER) {
            SceneObject sceneObject = sceneManager.getSceneObject(monsterObject.getAddrId());
            // 获取场景内玩家
            Map<Long, PlayerObject> playerObjectMap = sceneObject.getPlayerObjectMap();
            for (Map.Entry<Long, PlayerObject> entry : playerObjectMap.entrySet()) {
                if (entry.getValue().isDead()) {
                    continue;
                }
                targets.add(entry.getValue());
            }
        }
        // 如果是副本怪物
        if (monsterObject.getMonsterType() == MonsterType.INSTANCE_MONSTER) {
            InstanceObject instanceObject = instanceManager.getInstance(monsterObject.getAddrId());
            // 获取副本内玩家
            List<Long> playerIds = instanceObject.getCurrPlayers();
            for (Long playerId : playerIds) {
                PlayerObject playerObject = playerManager.getPlayerObject(playerId);
                if (playerObject == null || playerObject.isDead()) {
                    continue;
                }
                targets.add(playerObject);
            }
        }
        // 攻击玩家
        for (PlayerObject target : targets) {
            FighterContext fighterContext = monsterCastSkill2Player(monsterObject, target, skillConfig);
            pushFighterContext(fighterContext);
        }
    }

    /**
     * 怪物攻击玩家
     *
     * @param monsterObject
     * @param target
     * @param skillConfig
     * @return void
     */
    private FighterContext monsterCastSkill2Player(MonsterObject monsterObject, PlayerObject target, SkillConfig skillConfig) {
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", monsterObject.getAttack() + "");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        int hurt = 0;
        try {
            hurt = (int) Double.parseDouble(jse.eval(ruleFormula) + "");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // 真正的伤害
        int ruleHurt = hurt - target.getPlayerBattle().getDefense();
        // 无法击破防御
        if (ruleHurt <= 0) {
            ruleHurt = 1;
        }
        // 扣除玩家血量
        target.getPlayerBattle().subtractCurrHp(ruleHurt);
        // 构建战报
        FighterContext fighterContext = new FighterContext();
        // 玩家血量为空 玩家是否死亡
        if (target.getPlayerBattle().isCurrHpEmpty()) {
            // 设置玩家为死亡状态
            target.changeState(PlayerState.DEAD);
        }
        logger.info("{} 使用 {} 对 {} 造成 {} 伤害", monsterObject.getMonsterConfig().getName(), skillConfig.getName()
                , target.getPlayer().getName(), ruleHurt);
        return fighterContext;
    }

    /**
     * 怪物攻击宝宝
     *
     * @param monsterObject
     * @param target
     * @param skillConfig
     * @return void
     */
    private FighterContext monsterCastSkill2Pet(MonsterObject monsterObject, Pet target, SkillConfig skillConfig) {
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", monsterObject.getAttack() + "");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        int hurt = 0;
        try {
            hurt = (int) Double.parseDouble(jse.eval(ruleFormula) + "");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // 真正的伤害
        int ruleHurt = hurt - target.getDefense();
        // 无法击破防御
        if (ruleHurt <= 0) {
            ruleHurt = 1;
        }
        // 扣除宝宝血量
        target.reduceCurrHp(ruleHurt);
        // 构建战报
        FighterContext fighterContext = new FighterContext();
        // 宝宝血量是否已经空了
        if (target.isCurrHpEmpty()) {
            // 切换为死亡状态
            target.changeState(PetState.DEAD);
        }
        logger.info("{} 使用 {} 对 {} 造成 {} 伤害", monsterObject.getMonsterConfig().getName(), skillConfig.getName()
                , target.getId(), ruleHurt);
        return fighterContext;
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
    public void petUseSkill(long petId, long targetId, int unitType, int skillId) {
        Pet pet = petManager.getPet(petId);
        if (pet == null) {
            return;
        }
        // 宝宝释放伤害型技能

        // 宝宝释放辅助型技能
    }


    /**
     * 推送战报
     *
     * @param fighterContext
     * @return void
     */
    private void pushFighterContext(FighterContext fighterContext) {

    }

    /**
     * 验证单位的技能冷却时间
     *
     * @param unitId      单位Id
     * @param skillConfig 技能配置
     * @return boolean
     */
    private boolean verifySkillCoolTime(long unitId, SkillConfig skillConfig) {
        // 获取该单位的CD 实体
        UnitCoolTime unitCoolTime = coolTimeManager.getUnitCoolTime(unitId);
        // 该单位无任何CD
        if (unitCoolTime == null) {
            return false;
        }
        // 返回是否有该技能的CD
        return unitCoolTime.hasSkillCoolTime(skillConfig.getId());
    }

    /**
     * 技能进入冷却时间
     *
     * @param unitId      单位Id
     * @param skillConfig 技能配置
     * @return void
     */
    private void skillEntryCoolTime(long unitId, SkillConfig skillConfig) {
        UnitCoolTime unitCoolTime = coolTimeManager.getUnitCoolTime(unitId);
        if (unitCoolTime == null) {
            unitCoolTime = new UnitCoolTime();
            coolTimeManager.putUnitCoolTime(unitId, unitCoolTime);
        }
        // 判断技能CD
        if (skillConfig.getCoolTime() == 0) {
            // 无CD 技能直接返回
            return;
        }
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(skillConfig.getCoolTime(),
                TimeUnit.SECONDS);
        CoolTime coolTime = new CoolTime(startTime, endTime);
        unitCoolTime.putCoolTimeMap(skillConfig.getId(), coolTime);
    }

    /**
     * 判断该玩家是否能够被攻击
     *
     * @param playerObject
     * @param target
     * @return boolean
     */
    private boolean verifyAttackPlayer(PlayerObject playerObject, PlayerObject target) {
        if (target == null) {
            return false;
        }
        if (target.isDead()) {
            return false;
        }
        // 位置判断
        if (playerObject.getInstanceId() == null) {
            if (!playerObject.getPlayer().getSceneId().equals(target.getPlayer().getSceneId())) {
                return false;
            }
        } else {
            if (!playerObject.getInstanceId().equals(target.getInstanceId())) {
                return false;
            }
        }
        // 攻击模式判断
        // 和平
        if (playerObject.getFighterModeEnum() == FighterModeEnum.PEACE) {
            return false;
        }
        // 组队
        if (playerObject.getFighterModeEnum() == FighterModeEnum.TEAM) {
            if (playerObject.getTeamId() == null) {
                return true;
            }
            return !playerObject.getTeamId().equals(target.getTeamId());
        }
        // 全体
        if (playerObject.getFighterModeEnum() == FighterModeEnum.ALL) {
            return true;
        }
        return false;
    }


    /**
     * 验证怪物是否可以被攻击
     */
    public boolean verifyAttackMonster(PlayerObject playerObject, MonsterObject monsterObject) {
        if (monsterObject == null) {
            return false;
        }
        // 验证场景位置
        if (playerObject.getInstanceId() == null) {
            // 是否在同一个场景
            if (!playerObject.getPlayer().getSceneId().equals(monsterObject.getAddrId())) {
                return false;
            }
        } else {
            // 是否在同一个副本中
            if (!playerObject.getInstanceId().equals(monsterObject.getAddrId())) {
                return false;
            }
        }
        return true;
    }
}
