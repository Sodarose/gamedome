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
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.monster.type.MonsterType;
import com.game.gameserver.module.pet.entity.Pet;
import com.game.gameserver.module.pet.manager.PetManager;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.scene.helper.SceneHelper;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.skill.manager.SkillManager;
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
    @Autowired
    private SkillManager skillManager;

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
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return FighterProtocol.AttackRes.newBuilder().setCode(1000).setMsg("非法用户").build();
        }
        if (player.isDead()) {
            return FighterProtocol.AttackRes.newBuilder().setCode(1001).setMsg("您已经死亡 请等待复活").build();
        }
        Unit unit = null;
        // 设置目标为攻击目标
        if (unitType == UnitType.PLAYER) {
            unit = playerManager.getPlayer(targetId);
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
        player.setAttackTarget(unit);
        // 转换成攻击状态
        if (!player.getCurrState().equals(PlayerState.ATTACK)) {
            player.changeState(PlayerState.ATTACK);
        }
        return FighterProtocol.AttackRes.newBuilder().setCode(0).setMsg("success").build();
    }


    @Override
    public FighterProtocol.UseSkillRes useSkill(long playerId, long targetId, int unitType, int skillId) {
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return FighterProtocol.UseSkillRes.newBuilder().setCode(1000).setMsg("非法用户").build();
        }
        // 用户死亡
        if (player.isDead()) {
            return FighterProtocol.UseSkillRes.newBuilder().setCode(1001).setMsg("您已经死亡 请等待复活").build();
        }
        // 查询技能资源
        SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
        if (skillConfig == null) {
            return FighterProtocol.UseSkillRes.newBuilder().setCode(1002).setMsg("不存在该技能").build();
        }
        // 获取角色技能数据
        /*PlayerSkill playerSkill = skillManager.getPlayerSkill(playerId);
        if (playerSkill == null) {
            return FighterProtocol.UseSkillRes.newBuilder().setCode(1003).setMsg("用户技能数据读取失败").build();
        }
        if (playerSkill.hasSkill(skillId)) {
            return FighterProtocol.UseSkillRes.newBuilder().setCode(1004).setMsg("用户没有该技能").build();
        }*/
        // 判断技能冷却时间 获取单位冷却组件
        UnitCoolTime unitCoolTime = coolTimeManager.getUnitCoolTime(playerId);
        if (unitCoolTime != null) {
            if (unitCoolTime.hasSkillCoolTime(skillId)) {
                return FighterProtocol.UseSkillRes.newBuilder().setCode(1005).setMsg("技能冷却中").build();
            }
        }
        playerUseSkill(playerId, targetId, unitType, skillId);
        return FighterProtocol.UseSkillRes.newBuilder().setCode(0).setMsg("技能使用成功").build();
    }

    /**
     * 角色切换战斗模式
     *
     * @param playerId
     * @param model
     * @return com.game.protocol.FighterProtocol.ChangeModelRes
     */
    @Override
    public FighterProtocol.ChangeModelRes changeFighterModel(long playerId, int model) {
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return FighterProtocol.ChangeModelRes.newBuilder().setCode(1000).setMsg("非法用户").build();
        }
        FighterModeEnum fighterModeEnum = null;
        for(FighterModeEnum modeEnum:FighterModeEnum.values()){
            if(modeEnum.ordinal()==model){
                fighterModeEnum = modeEnum;
                break;
            }
        }
        if(fighterModeEnum==null){
            return FighterProtocol.ChangeModelRes.newBuilder().setCode(1001).setMsg("错误参数").build();
        }
        player.setFighterModeEnum(fighterModeEnum);
        return FighterProtocol.ChangeModelRes.newBuilder().setCode(0).setMsg("模式切换成功").build();
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
        Player player = playerManager.getPlayer(playerId);
        if (player == null || player.isDead()) {
            return;
        }
        // 获得技能基础对象
        SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
        if (skillConfig == null) {
            return;
        }
        // 验证技能CD 时间
        if (verifySkillCoolTime(playerId, skillConfig)) {
            logger.info("{} 释放技能{} 失败，该技能冷却中", player.getName(), skillConfig.getName());
            return;
        }
        // 玩家释放攻击性技能
        if (skillConfig.getSkillType() == SkillType.DAMAGE) {
            playerCastDamageSkill(player, targetId, unitType, skillConfig);
        }
        // 玩家释放辅助性技能
        if (skillConfig.getSkillType() == SkillType.ASSIST) {
            playerCastAssistSkill(player, targetId, unitType, skillConfig);
        }
        // 玩家释放召唤系技能
        if (skillConfig.getSkillType() == SkillType.SUMMONER) {
            playerCastSummonerSkill(player, skillConfig);
        }
        // 如果玩家不再副本中 同步场景数据
        if(player.getInstanceId()==null){
            Scene scene = sceneManager.getScene(player.getSceneId());
            if(scene==null){
                return;
            }
            // 同步数据
            SceneHelper.syncSceneInfo(scene);
        }
    }


    /**
     * 玩家释放伤害型技能
     *
     * @param player
     * @param targetId
     * @param unitType
     * @param skillConfig
     * @return void
     */
    private void playerCastDamageSkill(Player player, long targetId, int unitType, SkillConfig skillConfig) {
        // 玩家释放范围性伤害技能
        if (skillConfig.getSkillRange() == SkillType.AREA) {
            playerCastAreaDamageSkill(player, skillConfig);
        }

        // 玩家释放单体伤害技能
        if (skillConfig.getSkillRange() == SkillType.TARGET) {
            // 判断技能目标
            // 对玩家释放
            if (unitType == UnitType.PLAYER) {
                Player target = playerManager.getPlayer(targetId);
                if (target == null || target.isDead()) {
                    return;
                }
                playerCastDamageSkill2Player(player, target, skillConfig);
            }
            // 对怪物释放
            if (unitType == UnitType.MONSTER) {
                Monster monster = monsterManager.getMonster(targetId);
                if (monster == null || monster.isDead()) {
                    return;
                }
                playerCastDamageSkill2Monster(player, monster, skillConfig);
            }
            // 对宝宝释放
            if (unitType == UnitType.PET) {
                Pet pet = petManager.getPet(targetId);
                if (pet == null || pet.isDead()) {
                    return;
                }
                playerCastDamageSkill2Pet(player, pet, skillConfig);
            }
        }
        // 技能进入CD
        skillEntryCoolTime(player.getUnitId(), skillConfig);
    }

    /**
     * 玩家释放范围性伤害技能(目前范围型技能只能攻击怪物)
     *
     * @param player
     * @param skillConfig
     * @return void
     */
    private void playerCastAreaDamageSkill(Player player, SkillConfig skillConfig) {
        if (player == null || player.isDead()) {
            return;
        }
        // 技能范围内目标(当前场景中所有怪物)
        List<Monster> targets = new ArrayList<>();
        // 获取技能范围内的敌人
        // 范围内怪物
        if (player.getInstanceId() == null) {
            // 一般场景中 获取场景中的所有怪物
            Scene scene = sceneManager.getScene(player.getSceneId());
            if (scene == null) {
                return;
            }
            Map<Long, Long> map = scene.getMonsterMap();
            for (Map.Entry<Long, Long> entry : map.entrySet()) {
                Monster monster = monsterManager.getMonster(entry.getValue());
                // 排除死亡的怪物和已经回收的怪物
                if (monster == null || monster.isDead()) {
                    continue;
                }
                targets.add(monster);
            }
        } else {
            // 玩家在副本中 获取副本所有怪物
            InstanceObject instanceObject = instanceManager.getInstance(player.getInstanceId());
            if (instanceObject == null) {
                return;
            }
            List<Long> currMonsters = instanceObject.getCurrMonsters();
            for (long monsterId : currMonsters) {
                Monster monster = monsterManager.getMonster(monsterId);
                if (monster == null || monster.isDead()) {
                    continue;
                }
                targets.add(monster);
            }
        }
        // 范围内玩家
        // 范围内宝宝
        // 攻击怪物
        for (Monster monster : targets) {
            FighterContext context = playerAttackMonster(player, monster, skillConfig);
            // 推送战报
            pushFighterContext(context);
        }
    }

    /**
     * 玩家释放伤害技能攻击玩家
     *
     * @param player
     * @param target
     * @param skillConfig
     * @return void
     */
    private void playerCastDamageSkill2Player(Player player, Player target,
                                              SkillConfig skillConfig) {
        if (player == null || player.isDead()) {
            return;
        }
        if (target == null || target.isDead()) {
            return;
        }
        // 验证该玩家是否可以被攻击
        if (!verifyAttackPlayer(player, target)) {
            return;
        }
        FighterContext fighterContext = playerAttackPlayer(player, target, skillConfig);
        // 推送战报
        pushFighterContext(fighterContext);
    }

    /**
     * 玩家释放伤害技能攻击怪物
     *
     * @param player
     * @param target
     * @param skillConfig
     * @return void
     */
    private void playerCastDamageSkill2Monster(Player player, Monster target, SkillConfig skillConfig) {
        // 校验是否能够攻击该怪物
        if (!verifyAttackMonster(player, target)) {
            return;
        }
        FighterContext fighterContext = playerAttackMonster(player, target, skillConfig);
        // 推送战报
        pushFighterContext(fighterContext);
    }

    /**
     * 玩家攻击怪物
     *
     * @param player
     * @param monster
     * @param skillConfig
     * @return com.game.gameserver.module.fighter.entity.FighterContext 战报
     */
    private FighterContext playerAttackMonster(Player player,
                                               Monster monster, SkillConfig skillConfig) {
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", player.getAttack() + "");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        int hurt = 0;
        try {
            hurt = (int) Double.parseDouble(jse.eval(ruleFormula) + "");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // 真正的伤害
        int ruleHurt = hurt - monster.getDefense();
        // 无法击破防御
        if (ruleHurt <= 0) {
            ruleHurt = 1;
        }
        // 扣除怪物血量
        monster.reduceCurrHp(ruleHurt);
        // 构建战报
        FighterContext fighterContext = new FighterContext();
        // 怪物血量为空
        if (monster.isCurrHpEmpty()) {
            // 设置怪物为死亡状态
            monster.changeState(MonsterState.DEAD);
            // 玩家切换状态
            player.changeState(PlayerState.TAKEOFF);
            // 发出怪物死亡事件
            MonsterDeadEvent event = new MonsterDeadEvent(player.getId(),monster.getId(),
                    monster.getMonsterConfig().getId(),1);
            EventBus.EVENT_BUS.fire(event);
        }
        logger.info("{} 使用 {} 对 {} 造成 {} 伤害", player.getName(), skillConfig.getName()
                , monster.getMonsterConfig().getName(), ruleHurt);
        // 怪物未死亡 进入攻击状态
        if (!monster.isDead() && !monster.getCurrState().equals(MonsterState.ATTACK)) {
            // 设置怪物仇恨
            monster.setHateUnit(player);
            // 切换为攻击状态
            monster.changeState(MonsterState.ATTACK);
        }
        return fighterContext;
    }

    /**
     * 玩家攻击玩家
     *
     * @param player
     * @param target
     * @param skillConfig
     * @return com.game.gameserver.module.fighter.entity.FighterContext
     */
    private FighterContext playerAttackPlayer(Player player, Player target, SkillConfig skillConfig) {
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", player.getAttack() + "");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        // 计算伤害
        int hurt = 0;
        try {
            hurt = (int) Double.parseDouble(jse.eval(ruleFormula) + "");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        // 真正的伤害 伤害-防御
        int ruleHurt = hurt - target.getDefense();
        if (ruleHurt <= 0) {
            ruleHurt = 1;
        }
        // 测试 强制伤害为1
        ruleHurt = 1;
        // 扣除敌方血量
        target.subtractCurrHp(ruleHurt);
        // 判断血量是否为空
        boolean result = target.isCurrHpEmpty();
        if (result) {
            // 设置对方死亡
            target.changeState(PlayerState.DEAD);
            // 清空攻击目标
            player.setAttackTarget(null);
            // 进入脱战状态
            player.changeState(PlayerState.TAKEOFF);
        }
        // 测试用 被攻击者进入战斗状态
        if (!target.isDead() && !target.getCurrState().equals(PlayerState.ATTACK)) {
            // 对方将你列入攻击目标
            target.setAttackTarget(player);
            // 对方进入攻击状态
            target.changeState(PlayerState.ATTACK);
        }
        // 返回战斗上下文
        FighterContext fighterContext = new FighterContext();
        logger.info("{} 使用 {} 攻击 {} 造成伤害 {}", player.getName(), skillConfig.getName(),
                target.getName(), ruleHurt);
        return fighterContext;
    }


    /**
     * 玩家释放伤害技能攻击宝宝
     *
     * @param player
     * @param pet
     * @param skillConfig
     * @return void
     */
    private void playerCastDamageSkill2Pet(Player player, Pet pet, SkillConfig skillConfig) {
        if (verifyAttackPet(player, pet)) {
            return;
        }
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", player.getAttack() + "");
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
        logger.info("{} 使用 {} 对 {} 造成 {} 伤害", player.getName(), skillConfig.getName()
                , pet.getPetConfig().getName(), ruleHurt);
        // 下面为测试
        if (!pet.isDead() && !pet.getCurrState().equals(MonsterState.ATTACK)) {
            // 设置宝宝仇恨对象
            pet.setHateUnit(player);
            // 进入攻击状态
            pet.changeState(PetState.ATTACK);
        }
    }


    /**
     * 验证宝宝是否可以被攻击
     *
     * @param player
     * @param pet
     * @return boolean
     */
    private boolean verifyAttackPet(Player player, Pet pet) {
        return true;
    }

    /**
     * 玩家释放辅助性技能
     *
     * @param player
     * @param targetId
     * @param unitType
     * @param skillConfig
     * @return void
     */
    private void playerCastAssistSkill(Player player, long targetId, int unitType, SkillConfig skillConfig) {
        // 技能目标
        List<Player> targets = new ArrayList<>();
        // 判断技能范围
        if (skillConfig.getSkillRange() == SkillType.AREA) {
            // 获取在同一场景下的队友
            Team team = teamManager.getTeamObject(player.getTeamId());
            // 队伍不存在
            if (team == null) {
                targets.add(player);
            } else {
                for (Long playerId : team.getMembers()) {
                    Player temp = playerManager.getPlayer(playerId);
                    if (temp == null) {
                        continue;
                    }
                    // 判断是否在同一个场景下
                    if (player.getInstanceId() == null) {
                        // 同一场景
                        if (player.getSceneId().equals(temp.getSceneId())) {
                            targets.add(temp);
                        }
                    } else {
                        // 同一副本
                        if (player.getInstanceId().equals(temp.getInstanceId())) {
                            targets.add(temp);
                        }
                    }
                }
            }
        } else {
            // 单体辅助技能
            Player target = playerManager.getPlayer(targetId);
            if (target == null) {
                return;
            }
            targets.add(target);
        }
        // 治疗
        for (Player target : targets) {
            FighterContext fighterContext = playerAssistPlayer(player, target, skillConfig);
            pushFighterContext(fighterContext);
        }
    }

    private FighterContext playerAssistPlayer(Player player, Player target, SkillConfig skillConfig) {
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
            target.addCurrHp(value);
        }
        // 加蓝
        if (SkillType.MP.equals(type)) {
            target.addCurrMp(value);
        }
        FighterContext fighterContext = new FighterContext();
        logger.info("{} 对 {} 释放技能 {} ，{} 的 {} 增加 {}",
                player.getName(),
                target.getName(),
                skillConfig.getName(),
                target.getName(),
                type,
                value);
        return fighterContext;
    }

    /**
     * 玩家释放召唤性技能
     *
     * @param player
     * @param skillConfig
     * @return void
     */
    private void playerCastSummonerSkill(Player player, SkillConfig skillConfig) {
        // 解析技能
        String formula = skillConfig.getFormula();
        // 生成召唤物
        int petConfigId = Integer.parseInt(formula);
        // 获得宝宝基础配置
        PetConfig petConfig = StaticConfigManager.getInstance().getPetConfigMap().get(petConfigId);
        if (petConfig == null) {
            return;
        }
        // 放入场景中
        if (player.getInstanceId() == null) {
            Scene scene = sceneManager.getScene(player.getSceneId());
            if (scene == null) {
                return;
            }
            // 生成宝宝
            Pet pet = petManager.createPet(player.getId(), petConfigId);
            // 放入场景
            scene.getPetMap().put(pet.getId(), pet);
        } else {
            // 放入副本中
            InstanceObject instanceObject = instanceManager.getInstance(player.getInstanceId());
            if (instanceObject == null) {
                return;
            }
            Pet pet = petManager.createPet(player.getId(), petConfigId);
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
        Monster monster = monsterManager.getMonster(monsterId);
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
            monsterCastDamageSkill(monster, targetId, unitType, skillConfig);
        }
        // 如果玩家不再副本中 同步场景数据
        if(monster.getMonsterType()==MonsterType.SCENE_MONSTER){
            Scene scene = sceneManager.getScene(monster.getAddrId());
            if(scene==null){
                return;
            }
            // 同步数据
            SceneHelper.syncSceneInfo(scene);
        }
    }

    /**
     * 怪物释放伤害型技能
     *
     * @param monster
     * @param targetId
     * @param unitType
     * @param skillConfig
     * @return void
     */
    private void monsterCastDamageSkill(Monster monster, long targetId, int unitType, SkillConfig skillConfig) {
        // 怪物释放范围性技能
        if (skillConfig.getSkillRange() == SkillType.AREA) {
            monsterCastAreaDamageSkill(monster, skillConfig);
        }
        // 单体技能
        if (SkillType.TARGET == skillConfig.getSkillRange()) {
            // 攻击玩家
            if (unitType == UnitType.PLAYER) {
                Player player = playerManager.getPlayer(targetId);
                if (player == null || player.isDead()) {
                    return;
                }
                monsterCastSkill2Player(monster, player, skillConfig);
            }
            // 攻击宝宝
            if (unitType == UnitType.MONSTER) {
                Pet pet = petManager.getPet(targetId);
                if (pet == null || pet.isDead()) {
                    return;
                }
                monsterCastSkill2Pet(monster, pet, skillConfig);
            }
        }
        // 怪物技能进入CD
        skillEntryCoolTime(monster.getUnitId(), skillConfig);
    }

    /**
     * 怪物释放范围性攻击技能
     *
     * @param monster
     * @param skillConfig
     * @return void
     */
    private void monsterCastAreaDamageSkill(Monster monster, SkillConfig skillConfig) {
        // 被攻击的玩家列表
        List<Player> targets = new ArrayList<>();
        // 如果是场景怪物
        if (monster.getMonsterType() == MonsterType.SCENE_MONSTER) {
            Scene scene = sceneManager.getScene(monster.getAddrId());
            // 获取场景内玩家
            Map<Long, Player> playerObjectMap = scene.getPlayerMap();
            for (Map.Entry<Long, Player> entry : playerObjectMap.entrySet()) {
                if (entry.getValue().isDead()) {
                    continue;
                }
                targets.add(entry.getValue());
            }
        }
        // 如果是副本怪物
        if (monster.getMonsterType() == MonsterType.INSTANCE_MONSTER) {
            InstanceObject instanceObject = instanceManager.getInstance(monster.getAddrId());
            // 获取副本内玩家
            List<Long> playerIds = instanceObject.getCurrPlayers();
            for (Long playerId : playerIds) {
                Player player = playerManager.getPlayer(playerId);
                if (player == null || player.isDead()) {
                    continue;
                }
                targets.add(player);
            }
        }
        // 攻击玩家
        for (Player target : targets) {
            FighterContext fighterContext = monsterCastSkill2Player(monster, target, skillConfig);
            pushFighterContext(fighterContext);
        }
    }

    /**
     * 怪物攻击玩家
     *
     * @param monster
     * @param target
     * @param skillConfig
     * @return void
     */
    private FighterContext monsterCastSkill2Player(Monster monster, Player target, SkillConfig skillConfig) {
        // 解析技能 获取技能公式
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", monster.getAttack() + "");
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
        // 扣除玩家血量
        target.subtractCurrHp(ruleHurt);
        // 构建战报
        FighterContext fighterContext = new FighterContext();
        // 玩家血量为空 玩家是否死亡
        if (target.isCurrHpEmpty()) {
            // 设置玩家为死亡状态
            target.changeState(PlayerState.DEAD);
        }
        logger.info("{} 使用 {} 对 {} 造成 {} 伤害", monster.getMonsterConfig().getName(), skillConfig.getName()
                , target.getName(), ruleHurt);
        return fighterContext;
    }

    /**
     * 怪物攻击宝宝
     *
     * @param monster
     * @param target
     * @param skillConfig
     * @return void
     */
    private FighterContext monsterCastSkill2Pet(Monster monster, Pet target, SkillConfig skillConfig) {
        String formula = skillConfig.getFormula();
        String ruleFormula = formula.replace("${0}", monster.getAttack() + "");
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
        logger.info("{} 使用 {} 对 {} 造成 {} 伤害", monster.getMonsterConfig().getName(), skillConfig.getName()
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
     * @param player
     * @param target
     * @return boolean
     */
    private boolean verifyAttackPlayer(Player player, Player target) {
        if (target == null) {
            return false;
        }
        if (target.isDead()) {
            return false;
        }
        // 位置判断
        if (player.getInstanceId() == null) {
            if (!player.getSceneId().equals(target.getSceneId())) {
                return false;
            }
        } else {
            if (!player.getInstanceId().equals(target.getInstanceId())) {
                return false;
            }
        }
        // 攻击模式判断
        // 和平
        if (player.getFighterModeEnum() == FighterModeEnum.PEACE) {
            return false;
        }
        // 组队
        if (player.getFighterModeEnum() == FighterModeEnum.TEAM) {
            if (player.getTeamId() == null) {
                return true;
            }
            return !player.getTeamId().equals(target.getTeamId());
        }
        // 全体
        if (player.getFighterModeEnum() == FighterModeEnum.ALL) {
            return true;
        }
        return false;
    }


    /**
     * 验证怪物是否可以被攻击
     */
    public boolean verifyAttackMonster(Player player, Monster monster) {
        if (monster == null) {
            return false;
        }
        // 验证场景位置
        if (player.getInstanceId() == null) {
            // 是否在同一个场景
            if (!player.getSceneId().equals(monster.getAddrId())) {
                return false;
            }
        } else {
            // 是否在同一个副本中
            if (!player.getInstanceId().equals(monster.getAddrId())) {
                return false;
            }
        }
        return true;
    }
}
