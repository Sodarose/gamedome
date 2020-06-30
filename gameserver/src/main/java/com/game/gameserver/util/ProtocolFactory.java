package com.game.gameserver.util;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.email.entity.Email;
import com.game.gameserver.module.instance.model.InstanceObject;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.type.BagType;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.npc.model.NpcObject;
import com.game.gameserver.module.player.entity.PlayerBattle;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.bean.Scene;
import com.game.gameserver.module.skill.entity.Skill;
import com.game.gameserver.module.skill.entity.PlayerSkill;
import com.game.gameserver.module.store.entity.Commodity;
import com.game.gameserver.module.task.entity.PlayerTask;
import com.game.gameserver.module.task.entity.Task;
import com.game.gameserver.module.task.entity.TaskProgress;
import com.game.gameserver.module.team.entity.Team;
import com.game.protocol.*;

import java.util.List;
import java.util.Map;

/**
 * 转换工具
 *
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class ProtocolFactory {

    /**
     * 将playerList 转换为 PlayerProtocol.PlayerList
     *
     * @param playerList
     * @return com.game.protocol.PlayerProtocol.PlayerList
     */
    public static PlayerProtocol.PlayerListRes createPlayerList(List<com.game.gameserver.module.player.entity.Player> playerList) {
        PlayerProtocol.PlayerListRes.Builder builder = PlayerProtocol.PlayerListRes.newBuilder();
        for (com.game.gameserver.module.player.entity.Player player : playerList) {
            builder.addPlayerInfoList(createSimplePlayerInfo(player));
        }
        return builder.build();
    }

    /**
     * 将player 转换为 PlayerProtocol.BriefPlayerInfo
     *
     * @param player
     * @return com.game.protocol.PlayerProtocol.BriefPlayerInfo
     */
    public static PlayerProtocol.SimplePlayerInfo createSimplePlayerInfo(com.game.gameserver.module.player.entity.Player player) {
        PlayerProtocol.SimplePlayerInfo.Builder builder = PlayerProtocol.SimplePlayerInfo.newBuilder();
        builder.setId(player.getId().intValue());
        builder.setName(player.getName());
        builder.setCareerId(player.getCareerId());
        builder.setLevel(player.getLevel());
        return builder.build();
    }

    public static PlayerProtocol.LoginPlayerRes createLoginPlayerRes(int code, String msg, PlayerObject playerObject) {
        PlayerProtocol.LoginPlayerRes.Builder builder = PlayerProtocol.LoginPlayerRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (playerObject != null) {
            builder.setPlayerInfo(createPlayerInfo(playerObject));
        }
        return builder.build();
    }

    public static PlayerProtocol.PlayerInfo createPlayerInfo(PlayerObject playerObject) {
        PlayerProtocol.PlayerInfo.Builder builder = PlayerProtocol.PlayerInfo.newBuilder();
        builder.setId(playerObject.getPlayer().getId());
        builder.setName(playerObject.getPlayer().getName());
        builder.setLevel(playerObject.getPlayer().getLevel());
        builder.setCareerId(playerObject.getPlayer().getCareerId());
        builder.setGolds(playerObject.getPlayer().getGolds());
        builder.setState(playerObject.getCurrState().ordinal());
        builder.setFighterModel(playerObject.getFighterModeEnum().ordinal());
        builder.setSceneId(playerObject.getPlayer().getSceneId());
        // 人物属性
        builder.setPlayerBattle(createPlayerBattle(playerObject.getPlayerBattle()));
        return builder.build();
    }


    public static PlayerProtocol.PlayerBattle createPlayerBattle(PlayerBattle playerBattle) {
        PlayerProtocol.PlayerBattle.Builder builder = PlayerProtocol.PlayerBattle.newBuilder();
        builder.setHp(playerBattle.getHp());
        builder.setMp(playerBattle.getMp());
        builder.setCurrHp(playerBattle.getCurrHp());
        builder.setCurrMp(playerBattle.getCurrMp());
        builder.setAttack(playerBattle.getAttack());
        builder.setDefense(playerBattle.getDefense());
        return builder.build();
    }

    public static PlayerProtocol.PlayerSkill createPlayerSkill(PlayerSkill playerSkill) {
        PlayerProtocol.PlayerSkill.Builder builder = PlayerProtocol.PlayerSkill.newBuilder();
    /*    for (Skill skill : playerSkill.getSkillList()) {
            builder.addSkillInfo(createSkillInfo(skill));
        }*/
        return builder.build();
    }

    public static PlayerProtocol.SkillInfo createSkillInfo(Skill skill) {
        PlayerProtocol.SkillInfo.Builder builder = PlayerProtocol.SkillInfo.newBuilder();
        SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skill.getSkillId());
        builder.setId(skill.getId());
        builder.setName(skillConfig.getName());
        builder.setCareerId(skillConfig.getCareerId());
        builder.setLimitLevel(skillConfig.getLimitLevel());
        builder.setMaxLearnLevel(skillConfig.getMaxLearnLevel());
        builder.setCoolTime(skillConfig.getCoolTime());
        builder.setFormula(skillConfig.getFormula());
        builder.setDesc(skillConfig.getDesc());
        builder.setBagIndex(skill.getBagIndex());
        builder.setPlayerId(skill.getPlayerId());
        return builder.build();
    }


    /**
     * @param scene
     * @return com.game.protocol.SceneProtocol.SceneInfo
     */
    public static SceneProtocol.SceneInfo createSceneInfo(Scene scene) {
        SceneProtocol.SceneInfo.Builder builder = SceneProtocol.SceneInfo.newBuilder();
        builder.setId(scene.getId());
        builder.setName(scene.getSceneConfig().getName());
        builder.setDescription(scene.getSceneConfig().getDesc());
        builder.setPlayerNum(scene.getPlayerNum());
        for (Map.Entry<Long, PlayerObject> entry : scene.getPlayerObjectMap().entrySet()) {
            builder.putPlayers(entry.getKey(), createOtherPlayerInfo(entry.getValue()));
        }
        for (Map.Entry<Long, Long> entry : scene.getMonsterObjectMap().entrySet()) {
            MonsterObject monsterObject = MonsterManager.instance.getMonster(entry.getValue());
            if (monsterObject == null) {
                continue;
            }
            builder.putMonsters(entry.getKey(), createMonster(monsterObject));
        }
        for (Map.Entry<Long, NpcObject> entry : scene.getNpcObjectMap().entrySet()) {
            builder.putNpcs(entry.getKey(), createNpc(entry.getValue()));
        }
        return builder.build();
    }

    /**
     * @param playerObject
     * @return com.game.protocol.PlayerProtocol.OtherPlayerInfo
     */
    public static PlayerProtocol.OtherPlayerInfo createOtherPlayerInfo(PlayerObject playerObject) {
        PlayerProtocol.OtherPlayerInfo.Builder builder = PlayerProtocol.OtherPlayerInfo.newBuilder();
        builder.setId(playerObject.getPlayer().getId());
        builder.setName(playerObject.getPlayer().getName());
        builder.setLevel(playerObject.getPlayer().getLevel());
        builder.setCareerId(playerObject.getPlayer().getCareerId());
        builder.setPlayerBattle(createPlayerBattle(playerObject.getPlayerBattle()));
        return builder.build();
    }

    /**
     * @param monsterObject
     * @return com.game.protocol.SceneProtocol.Monster
     */
    public static Actor.MonsterInfo createMonster(MonsterObject monsterObject) {
        Actor.MonsterInfo.Builder builder = Actor.MonsterInfo.newBuilder();
        builder.setId(monsterObject.getId());
        builder.setName(monsterObject.getMonsterConfig().getName());
        builder.setLevel(monsterObject.getMonsterConfig().getLevel());
        builder.setHp(monsterObject.getHp());
        builder.setMp(monsterObject.getMp());
        builder.setAttack(monsterObject.getAttack());
        builder.setDefense(monsterObject.getDefense());
        builder.setCurrHp(monsterObject.getCurrHp());
        builder.setCurrMp(monsterObject.getCurrMp());
        return builder.build();
    }

    public static Actor.NpcInfo createNpc(NpcObject npcObject) {
        Actor.NpcInfo.Builder builder = Actor.NpcInfo.newBuilder();
        builder.setId(npcObject.getId());
        builder.setName(npcObject.getNpcConfig().getName());
        builder.setLevel(npcObject.getNpcConfig().getLevel());
        return builder.build();
    }

    public static Store.CommodityList createCommodityList(List<Commodity> commodities) {
        Store.CommodityList.Builder builder = Store.CommodityList.newBuilder();
        for (Commodity commodity : commodities) {
            builder.addCommodityInfos(createCommodityInfo(commodity));
        }
        return builder.build();
    }


    public static Store.CommodityInfo createCommodityInfo(Commodity commodity) {
        Store.CommodityInfo.Builder builder = Store.CommodityInfo.newBuilder();
        CommodityConfig commodityConfig = StaticConfigManager.getInstance().getCommodityConfigMap()
                .get(commodity.getCommodityId());
        if (commodityConfig == null) {
            return null;
        }

        builder.setId(commodityConfig.getId());
        builder.setGoodsType(commodityConfig.getGoodsType());
        builder.setGoodsId(commodityConfig.getGoodsId());

        if (ItemType.EQUIP == commodityConfig.getGoodsType()) {
            EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap()
                    .get(commodityConfig.getGoodsId());
            if (equipConfig == null) {
                return null;
            }
            builder.setGoodsName(equipConfig.getName());
        }

        if (ItemType.PROP == commodityConfig.getGoodsType()) {
            PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap()
                    .get(commodityConfig.getGoodsId());
            if (propConfig == null) {
                return null;
            }
            builder.setGoodsName(propConfig.getName());
        }
        builder.setShoreType(commodityConfig.getStoreType());
        builder.setOriginalPrice(commodityConfig.getOriginalPrice());
        builder.setPrice(commodityConfig.getOriginalPrice());
        builder.setLimitCount(commodityConfig.getLimitCount());
        return builder.build();
    }

    public static TeamProtocol.CreateTeamRes createCreateTeamRes(int code, String msg) {
        TeamProtocol.CreateTeamRes.Builder builder = TeamProtocol.CreateTeamRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        return builder.build();
    }

    public static TeamProtocol.TeamInfo createTeamInfo(Team team) {
        TeamProtocol.TeamInfo.Builder builder = TeamProtocol.TeamInfo.newBuilder();
        builder.setId(team.getId());
        builder.setCaptainId(team.getCaptainId());
        builder.setTeamName(team.getTeamName());
        builder.setCurrNum(team.getCurrNum());
        builder.setMaxNum(team.getMaxNum());
        builder.setInstance(team.getInstanceId() != null);
        for (Long playerId : team.getMembers()) {
            PlayerObject playerObject = PlayerManager.instance.getPlayerObject(playerId);
            if (playerObject == null) {
                continue;
            }
            builder.addMemberName(playerObject.getPlayer().getName());
        }
        return builder.build();
    }

    public static TeamProtocol.EntryTeamRes createEntryRes(int code, String msg) {
        TeamProtocol.EntryTeamRes.Builder builder = TeamProtocol.EntryTeamRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        return builder.build();
    }

    public static InstanceProtocol.InstanceSuccess createInstanceSuccess(InstanceConfig instanceConfig) {
        InstanceProtocol.InstanceSuccess.Builder builder = InstanceProtocol.InstanceSuccess.newBuilder();
        builder.setCode(0);
        builder.setMsg("恭喜通关");
        builder.setExprAward(instanceConfig.getExprAward());
        builder.setGoldAward(instanceConfig.getGoldAward());
        for (Integer equipId : instanceConfig.getEquipAward()) {
            EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap().get(equipId);
            if (equipConfig == null) {
                continue;
            }
            builder.addEquipList(equipConfig.getName());
        }
        for (Integer propId : instanceConfig.getPropAward()) {
            PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(propId);
            if (propConfig == null) {
                continue;
            }
            builder.addPropList(propConfig.getName());
        }
        return builder.build();
    }

    public static InstanceProtocol.InstanceInfoListRes createInstanceInfoListRes(int code, String msg, List<InstanceConfig> instanceConfigs) {
        InstanceProtocol.InstanceInfoListRes.Builder builder = InstanceProtocol.InstanceInfoListRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (instanceConfigs != null) {
            for (InstanceConfig instanceConfig : instanceConfigs) {
                builder.addInstanceInfoList(createInstanceInfo(instanceConfig));
            }
        }
        return builder.build();
    }

    public static InstanceProtocol.InstanceConfigInfo createInstanceInfo(InstanceConfig instanceConfig) {
        InstanceProtocol.InstanceConfigInfo.Builder builder = InstanceProtocol.InstanceConfigInfo.newBuilder();
        builder.setInstanceConfigId(instanceConfig.getId());
        builder.setInstanceName(instanceConfig.getName());
        builder.setInstanceType(instanceConfig.getType());
        builder.setDiff(instanceConfig.getDiff());
        builder.setOpenTime("全天开放");
        builder.setLimitTime(instanceConfig.getLimitTime());
        builder.setNeedTeam(false);
        builder.setMinNum(instanceConfig.getMinNum());
        builder.setMaxNum(instanceConfig.getMaxNum());
        builder.setMinLevel(instanceConfig.getMinLevel());
        builder.setExprAward(instanceConfig.getExprAward());
        builder.setGoldAward(instanceConfig.getGoldAward());
        builder.setDesc(instanceConfig.getDesc());
        for (Integer equipId : instanceConfig.getEquipAward()) {
            EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap().get(equipId);
            if (equipConfig == null) {
                continue;
            }
            builder.addEquipAward(equipConfig.getName());
        }
        for (Integer propId : instanceConfig.getPropAward()) {
            PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(propId);
            if (propConfig == null) {
                continue;
            }
            builder.addPropAward(propConfig.getName());
        }
        return builder.build();
    }

    public static InstanceProtocol.EntryInstanceRes createEntryInstanceRes(int code, String msg, InstanceObject instanceObject) {
        InstanceProtocol.EntryInstanceRes.Builder builder = InstanceProtocol.EntryInstanceRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (instanceObject != null) {
            InstanceProtocol.InstanceInfo info = createInstanceInfo(instanceObject);
            if (info == null) {
                builder.setCode(1001);
                builder.setMsg("数据错误");
                return builder.build();
            }
            builder.setInstanceInfo(info);
        }
        return builder.build();
    }

    public static InstanceProtocol.InstanceInfo createInstanceInfo(InstanceObject instanceObject) {
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceObject
                .getInstanceConfigId());
        if (instanceConfig == null) {
            return null;
        }
        InstanceProtocol.InstanceInfo.Builder builder = InstanceProtocol.InstanceInfo.newBuilder();
        builder.setId(instanceObject.getId());
        builder.setName(instanceConfig.getName());
        builder.setDescription(instanceConfig.getDesc());
        builder.setEndTime(instanceObject.getEndTime());
        builder.setRecoveryTime(instanceObject.getRecoveryTime());
        for (Long playerId : instanceObject.getCurrPlayers()) {
            PlayerObject playerObject = PlayerManager
                    .instance.getPlayerObject(playerId);
            if (playerObject == null) {
                continue;
            }
            builder.addPlayerList(createOtherPlayerInfo(playerObject));
        }

        for (Long monsterId : instanceObject.getCurrMonsters()) {
            MonsterObject monsterObject = MonsterManager.instance.getMonster(monsterId);
            if (monsterObject == null) {
                continue;
            }
            builder.addMonsterList(createMonster(monsterObject));
        }
        return builder.build();
    }

    public static InstanceProtocol.ExitInstanceRes createExitInstanceRes(int code, String msg) {
        InstanceProtocol.ExitInstanceRes.Builder builder = InstanceProtocol.ExitInstanceRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        return builder.build();
    }

    public static TeamProtocol.CheckTeamRes createCheckTeamRes(int code, String msg, Team team) {
        TeamProtocol.CheckTeamRes.Builder builder = TeamProtocol.CheckTeamRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (team != null) {
            builder.setTeamInfo(createTeamInfo(team));
        }
        return builder.build();
    }

    public static ItemProtocol.PlayerBag createPlayerBag(List<Item> items) {
        ItemProtocol.PlayerBag.Builder builder = ItemProtocol.PlayerBag.newBuilder();
        for (Item item : items) {
            builder.addItemList(createItemInfo(item));
        }
        return builder.build();
    }

    public static ItemProtocol.ItemInfo createItemInfo(Item item) {
        ItemProtocol.ItemInfo.Builder builder = ItemProtocol.ItemInfo.newBuilder();
        builder.setItemId(item.getId());
        builder.setItemType(item.getItemType());
        if (item.getItemType().equals(ItemType.EQUIP)) {
            EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap().get(item.getItemId());
            builder.setItemName(equipConfig.getName());
            builder.setDesc(equipConfig.getDesc());
        }
        if (item.getItemType().equals(ItemType.PROP)) {
            PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(item.getItemId());
            builder.setItemName(propConfig.getName());
            builder.setDesc(propConfig.getDesc());
        }
        builder.setItemNum(item.getNum());
        builder.setBagPack(BagType.PLAYER_BAG);
        builder.setBagIndex(item.getBagIndex());
        builder.setBound(item.getBound() == 1);
        return builder.build();
    }

    public static EmailProtocol.EmailListRes createEmailListRes(int code, String msg, List<Email> emailList) {
        EmailProtocol.EmailListRes.Builder builder = EmailProtocol.EmailListRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (emailList != null && emailList.size() != 0) {
            for (Email email : emailList) {
                builder.addEmail(createEmailInfo(email));
            }
        }
        return builder.build();
    }

    public static EmailProtocol.EmailInfo createEmailInfo(Email email) {
        EmailProtocol.EmailInfo.Builder builder = EmailProtocol.EmailInfo.newBuilder();
        builder.setId(email.getId());
        builder.setTitle(email.getTitle());
        builder.setSendName(email.getSenderName());
        builder.setContent(email.getContent());
        for (Item item : email.getAttachments()) {
            if (item.getItemType().equals(ItemType.EQUIP)) {
                EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap()
                        .get(item.getItemId());
                if (equipConfig == null) {
                    continue;
                }
                builder.addAttachments(equipConfig.getName());
            }
            if (item.getItemType().equals(ItemType.PROP)) {
                PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(item.getItemId());
                if (propConfig == null) {
                    continue;
                }
                builder.addAttachments(propConfig.getName());
            }
        }
        builder.setGolds(email.getGolds());
        builder.setState(email.getState());
        return builder.build();
    }

    /**
     *
     *
     * @param code
     * @param msg
     * @param taskConfigs
     * @return com.game.protocol.TaskProtocol.QueryAllTaskRes
     */
    public static TaskProtocol.QueryAllTaskRes createQueryAllTaskRes(int code, String msg, List<TaskConfig> taskConfigs) {
        TaskProtocol.QueryAllTaskRes.Builder builder = TaskProtocol.QueryAllTaskRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (taskConfigs != null) {
            for (TaskConfig taskConfig : taskConfigs) {
                builder.addInfos(createTaskConfigInfo(taskConfig));
            }
        }
        return builder.build();
    }

    /** 可接受任务列表 */
    public static TaskProtocol.QueryReceiveAbleTaskRes createQueryReceiveAbleTaskRes(int code,String msg,
                                                                                List<TaskConfig> taskConfigs){
        TaskProtocol.QueryReceiveAbleTaskRes.Builder builder = TaskProtocol.QueryReceiveAbleTaskRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (taskConfigs != null) {
            for (TaskConfig taskConfig : taskConfigs) {
                builder.addInfos(createTaskConfigInfo(taskConfig));
            }
        }
        return builder.build();
    }

    public static TaskProtocol.QueryReceiveTaskRes creatQueryReceiveTaskRes(int code, String msg, PlayerTask playerTask) {
        TaskProtocol.QueryReceiveTaskRes.Builder builder = TaskProtocol.QueryReceiveTaskRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if(playerTask!=null){
            for (Task task : playerTask.getTaskList()) {
                builder.addTaskInfos(createTaskInfo(task));
            }
        }
        return builder.build();
    }



    public static TaskProtocol.TaskConfigInfo createTaskConfigInfo(TaskConfig taskConfig) {
        TaskProtocol.TaskConfigInfo.Builder builder = TaskProtocol.TaskConfigInfo.newBuilder();
        builder.setId(taskConfig.getId());
        builder.setName(taskConfig.getName());
        builder.setDesc(taskConfig.getDescription());
        builder.setType(taskConfig.getType());
        builder.setLimitLevel(taskConfig.getLimitLevel());
        builder.setTaskRequire(taskConfig.getTaskRequire());
        builder.setExpr(taskConfig.getExpr());
        builder.setGolds(taskConfig.getGolds());
        builder.setProps(taskConfig.getProps());
        builder.setEquips(taskConfig.getEquips());
        return builder.build();
    }

    /**
     * @param task
     * @return com.game.protocol.TaskProtocol.TaskInfo
     */
    public static TaskProtocol.TaskInfo createTaskInfo(Task task) {
        TaskProtocol.TaskInfo.Builder builder = TaskProtocol.TaskInfo.newBuilder();
        builder.setId(task.getId());
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(task.getTaskId());
        builder.setTaskId(taskConfig.getId());
        builder.setName(taskConfig.getName());
        builder.setDesc(taskConfig.getDescription());
        builder.setExpr(taskConfig.getExpr());
        builder.setGolds(taskConfig.getGolds());
        builder.setProps(taskConfig.getProps());
        builder.setEquips(taskConfig.getEquips());
        builder.setState(task.getState());
        List<TaskProgress> taskProgresses = task.getTaskProgresses();
        for (TaskProgress taskProgress : taskProgresses) {
            TaskProtocol.TaskProgressInfo info = TaskProtocol.TaskProgressInfo.newBuilder().setType(taskProgress.getType())
                    .setTarget(taskProgress.getTarget())
                    .setAmount(taskProgress.getAmount())
                    .setNum(taskProgress.getNum())
                    .setComplete(taskProgress.isComplete()).build();
            builder.addTaskProgressInfo(info);
        }
        return builder.build();
    }
}

