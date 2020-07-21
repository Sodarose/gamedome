package com.game.gameserver.util;

/*import com.game.gameserver.module.achievement.entity.AchievementEntity;
import com.game.gameserver.module.achievement.model.PlayerAchievement;*/
/*import com.game.gameserver.module.pet.entity.Pet;*/
/*import com.game.gameserver.module.team.entity.Team;*/

/**
 * 转换工具
 *
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class ProtocolFactory {

   /* public static PlayerProtocol.QueryRoleListRes createQueryRoleListRes(List<Role> roles) {
        PlayerProtocol.QueryRoleListRes.Builder builder = PlayerProtocol.QueryRoleListRes.newBuilder();
        for (Role role : roles) {
            builder.addRoles(createRoleInfo(role));
        }
        return builder.build();
    }

    public static PlayerProtocol.RoleInfo createRoleInfo(Role role) {
        PlayerProtocol.RoleInfo.Builder builder = PlayerProtocol.RoleInfo.newBuilder();
        builder.setId(role.getId());
        builder.setName(role.getName());
        builder.setCareerId(role.getCareerId());
        builder.setLevel(role.getLevel());
        return builder.build();
    }



    public static PlayerProtocol.QueryPlayerInfoRes createQueryPlayerInfoRes(Player player) {
        PlayerProtocol.QueryPlayerInfoRes.Builder builder = PlayerProtocol.QueryPlayerInfoRes.newBuilder();
        builder.setPlayerInfo(createPlayerInfo(player));
        return builder.build();
    }


    public static PlayerProtocol.PlayerInfo createPlayerInfo(Player player) {
        PlayerProtocol.PlayerInfo.Builder builder = PlayerProtocol.PlayerInfo.newBuilder();
        // 基本信息
*//*        builder.setId(player.getId());
        builder.setName(player.getName());
        builder.setLevel(player.getLevel());
        builder.setCareerId(player.getCareerId());
        builder.setGolds(player.getGolds());
        builder.setState(player.getCurrState().ordinal());
        builder.setFighterModel(player.getFighterModeEnum().ordinal());
        builder.setSceneId(player.getSceneId());
        //
        if(player.getGuildId()!=null){
            builder.setUnionId(player.getGuildId());
        }
        // 战斗属性
        builder.setHp(player.getHp());
        builder.setCurrHp(player.getCurrHp());
        builder.setMp(player.getMp());
        builder.setCurrHp(player.getCurrMp());
        builder.setAttack(player.getAttack());
        builder.setDefense(player.getDefense());*//*
        // 其他信息
        return builder.build();
    }

    public static PlayerProtocol.SimplePlayerInfo createSimplePlayerInfo(Player player) {
        PlayerProtocol.SimplePlayerInfo.Builder builder = PlayerProtocol.SimplePlayerInfo.newBuilder();
*//*        builder.setId(player.getId());
        builder.setName(player.getName());
        builder.setCurrHp(player.getCurrHp());
        builder.setState(player.getCurrState().ordinal());*//*
        return builder.build();
    }


    public static SceneProtocol.QuerySceneListRes createQuerySceneListRes(List<GameScene> scenes) {
        SceneProtocol.QuerySceneListRes.Builder builder = SceneProtocol.QuerySceneListRes.newBuilder();
        for (GameScene scene : scenes) {
            builder.addScenes(createSimpleSceneInfo(scene));
        }
        return builder.build();
    }

    public static SceneProtocol.SceneInfo createSceneInfo(GameScene scene) {
        SceneProtocol.SceneInfo.Builder builder = SceneProtocol.SceneInfo.newBuilder();

        // 写入npc信息
        for (Map.Entry<Long, Npc> entry : scene.getNpcMap().entrySet()) {
            builder.addNpcs(createSimpleNpcInfo(entry.getValue()));
        }
        // 写入宝宝信息
       *//* for (Map.Entry<Long, Pet> entry : scene.getPetMap().entrySet()) {
            builder.addPets(createSimplePetInfo(entry.getValue()));
        }*//*
        return builder.build();
    }

    public static SceneProtocol.SimpleSceneInfo createSimpleSceneInfo(GameScene scene) {
        SceneProtocol.SimpleSceneInfo.Builder builder = SceneProtocol.SimpleSceneInfo.newBuilder();

        return builder.build();
    }


    public static PlayerProtocol.OtherPlayerInfo createOtherPlayerInfo(Player player) {
        PlayerProtocol.OtherPlayerInfo.Builder builder = PlayerProtocol.OtherPlayerInfo.newBuilder();
        builder.setId(player.getId());
        builder.setName(player.getName());
        builder.setLevel(player.getLevel());
        builder.setCareerId(player.getCareerId());
        return builder.build();
    }

    public static Actor.MonsterInfo createMonster(Monster monster) {
        Actor.MonsterInfo.Builder builder = Actor.MonsterInfo.newBuilder();

        return builder.build();
    }

    public static Actor.SimpleMonsterInfo createSimpleMonsterInfo(Monster monster) {
        Actor.SimpleMonsterInfo.Builder builder = Actor.SimpleMonsterInfo.newBuilder();

        return builder.build();
    }


    public static Actor.NpcInfo createNpc(Npc npc) {
        Actor.NpcInfo.Builder builder = Actor.NpcInfo.newBuilder();

        return builder.build();
    }

    public static Actor.SimpleNpcInfo createSimpleNpcInfo(Npc npc) {
        Actor.SimpleNpcInfo.Builder builder = Actor.SimpleNpcInfo.newBuilder();

        return builder.build();
    }

*//*    public static Actor.SimplePetInfo createSimplePetInfo(Pet pet) {
        Actor.SimplePetInfo.Builder builder = Actor.SimplePetInfo.newBuilder();
        builder.setId(pet.getId());
        builder.setName(pet.getPetConfig().getName());
        builder.setCurrHp(pet.getCurrHp());
        builder.setState(0);
        return builder.build();
    }*//*

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

   *//* public static TeamProtocol.TeamInfo createTeamInfo(Team team) {
        TeamProtocol.TeamInfo.Builder builder = TeamProtocol.TeamInfo.newBuilder();
     *//**//*   builder.setId(team.getId());
        builder.setCaptainId(team.getCaptainId());
        builder.setTeamName(team.getTeamName());
        builder.setCurrNum(team.getCurrNum());
        builder.setMaxNum(team.getMaxNum());
        builder.setInstance(team.getInstanceId() != null);
        for (Long playerId : team.getMembers()) {
            Player player = PlayerManager.instance.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            builder.addMemberName(player.getName());
        }*//**//*
        return builder.build();
    }*//*

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

    public static InstanceProtocol.EntryInstanceRes createEntryInstanceRes(int code, String msg, InstanceCc instanceObject) {
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

    public static InstanceProtocol.InstanceInfo createInstanceInfo(InstanceCc instanceObject) {
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
      *//*  for (Long playerId : instanceObject.getCurrPlayers()) {
            Player player = PlayerManager
                    .instance.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            builder.addPlayerList(createOtherPlayerInfo(player));
        }*//*

        for (Long monsterId : instanceObject.getCurrMonsters()) {


        }
        return builder.build();
    }

    public static InstanceProtocol.ExitInstanceRes createExitInstanceRes(int code, String msg) {
        InstanceProtocol.ExitInstanceRes.Builder builder = InstanceProtocol.ExitInstanceRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        return builder.build();
    }

  *//*  public static TeamProtocol.CheckTeamRes createCheckTeamRes(int code, String msg, Team team) {
        TeamProtocol.CheckTeamRes.Builder builder = TeamProtocol.CheckTeamRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        *//**//*if (team != null) {
            builder.setTeamInfo(createTeamInfo(team));
        }*//**//*
        return builder.build();
    }*//*



    public static EmailProtocol.EmailListRes createEmailListRes(int code, String msg, List<EmailEntity> emailEntityList) {
        EmailProtocol.EmailListRes.Builder builder = EmailProtocol.EmailListRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (emailEntityList != null && emailEntityList.size() != 0) {
            for (EmailEntity emailEntity : emailEntityList) {
                builder.addEmail(createEmailInfo(emailEntity));
            }
        }
        return builder.build();
    }

    public static EmailProtocol.EmailInfo createEmailInfo(EmailEntity emailEntity) {
        EmailProtocol.EmailInfo.Builder builder = EmailProtocol.EmailInfo.newBuilder();
        builder.setId(emailEntity.getId());
        builder.setTitle(emailEntity.getTitle());
        builder.setSendName(emailEntity.getSenderName());
        builder.setContent(emailEntity.getContent());
        //
        builder.setGolds(emailEntity.getGolds());
        builder.setState(emailEntity.getState());
        return builder.build();
    }

    *//**
     * @param code
     * @param msg
     * @param taskConfigs
     * @return com.game.protocol.TaskProtocol.QueryAllTaskRes
     *//*
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

    *//**
     * 可接受任务列表
     *//*
    public static TaskProtocol.QueryReceiveAbleTaskRes createQueryReceiveAbleTaskRes(int code, String msg,
                                                                                     List<TaskConfig> taskConfigs) {
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

    public static TaskProtocol.QueryReceiveTaskRes creatQueryReceiveTaskRes(int code, String msg, UserTask playerTask) {
        TaskProtocol.QueryReceiveTaskRes.Builder builder = TaskProtocol.QueryReceiveTaskRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (playerTask != null) {
            for (TaskEntity task : playerTask.getTaskList()) {
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
        builder.setTaskRequire(taskConfig.getTaskRequireStr());
        builder.setExpr(taskConfig.getExpr());
        builder.setGolds(taskConfig.getGolds());
        builder.setProps(TaskUtil.parserPropsAward2Str(taskConfig.getPropAwards()));
        builder.setEquips(TaskUtil.parserEquipAward2Str(taskConfig.getEquipAwards()));
        return builder.build();
    }

    *//**
     * @param task
     * @return com.game.protocol.TaskProtocol.TaskInfo
     *//*
    public static TaskProtocol.TaskInfo createTaskInfo(TaskEntity task) {
        TaskProtocol.TaskInfo.Builder builder = TaskProtocol.TaskInfo.newBuilder();
        builder.setId(task.getId());
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(task.getTaskId());
        builder.setTaskId(taskConfig.getId());
        builder.setName(taskConfig.getName());
        builder.setDesc(taskConfig.getDescription());
        builder.setExpr(taskConfig.getExpr());
        builder.setGolds(taskConfig.getGolds());
        builder.setProps(TaskUtil.parserPropsAward2Str(taskConfig.getPropAwards()));
        builder.setEquips(TaskUtil.parserEquipAward2Str(taskConfig.getEquipAwards()));
        builder.setState(task.getState());
        List<TaskProgress> taskProgresses = task.getTaskProgresses();
        for (TaskProgress taskProgress : taskProgresses) {
            TaskProtocol.TaskProgressInfo info = TaskProtocol.TaskProgressInfo.newBuilder()
                    .setComplete(taskProgress.isComplete())
                    // 进度的字符串描述
                    .setDescription(TaskUtil.parserTaskRequireByStr(taskProgress))
                    .build();
            builder.addTaskProgressInfo(info);
        }
        return builder.build();
    }

    public static AchievementProtocol.QueryAchievementListRes
    createQueryAchievementListRes(int code, String msg, List<AchievementConfig> achievementConfigs) {
        AchievementProtocol.QueryAchievementListRes.Builder builder = AchievementProtocol
                .QueryAchievementListRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (achievementConfigs != null) {
            for (AchievementConfig achievementConfig : achievementConfigs) {
                builder.addAchievementConfigInfos(createAchievementConfigInfo(achievementConfig));
            }
        }
        return builder.build();
    }

    *//**
     * @param config
     * @return com.game.protocol.AchievementProtocol.AchievementConfigInfo
     *//*
    public static AchievementProtocol.AchievementConfigInfo createAchievementConfigInfo(AchievementConfig config) {
        AchievementProtocol.AchievementConfigInfo.Builder builder = AchievementProtocol.AchievementConfigInfo
                .newBuilder();
        builder.setId(config.getId());
        builder.setName(config.getName());
        builder.setLimitLevel(config.getLimitLevel());
        builder.setTaskRequire(config.getTaskRequireStr());
        builder.setExpr(config.getExpr());
        builder.setGolds(config.getGolds());
        builder.setProps(TaskUtil.parserPropsAward2Str(config.getPropAwards()));
        builder.setEquips(TaskUtil.parserEquipAward2Str(config.getEquipAwards()));
        return builder.build();
    }*/

    /*public static AchievementProtocol.QueryPlayerAchievementListRes
    createQueryPlayerAchievementListRes(int code, String msg, PlayerAchievement playerAchievement) {
        AchievementProtocol.QueryPlayerAchievementListRes.Builder builder = AchievementProtocol
                .QueryPlayerAchievementListRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (playerAchievement != null) {
            for (AchievementEntity achievement : playerAchievement.getAchievements()) {
                builder.addAchievementInfos(createAchievementInfo(achievement));
            }
        }
        return builder.build();
    }*/

   /* public static AchievementProtocol.AchievementInfo createAchievementInfo(AchievementEntity achievement) {
        AchievementProtocol.AchievementInfo.Builder builder = AchievementProtocol.AchievementInfo.newBuilder();
        AchievementConfig achievementConfig = StaticConfigManager.getInstance().getAchievementConfigMap()
                .get(achievement.getAchievementId());
        if(achievementConfig==null){
            return builder.build();
        }
        builder.setId(achievement.getId());
        builder.setAchievementId(achievementConfig.getId());
        builder.setName(achievementConfig.getName());
        builder.setExpr(achievementConfig.getExpr());
        builder.setGolds(achievementConfig.getGolds());
        builder.setProps(TaskUtil.parserPropsAward2Str(achievementConfig.getPropAwards()));
        builder.setEquips(TaskUtil.parserEquipAward2Str(achievementConfig.getEquipAwards()));
        builder.setState(achievement.getState());
        // 设置进度
        List<TaskProgress> taskProgresses = achievement.getTaskProgresses();
        for (TaskProgress taskProgress : taskProgresses) {
            TaskProtocol.TaskProgressInfo info = TaskProtocol.TaskProgressInfo.newBuilder()
                    .setComplete(taskProgress.isComplete())
                    // 进度的字符串描述
                    .setDescription(TaskUtil.parserTaskRequireByStr(taskProgress))
                    .build();
            builder.addTaskProgressInfo(info);
        }
        return builder.build();
    }*/

}

