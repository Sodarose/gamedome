package com.game.gameserver.module.skill.service;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.entity.CreatureState;
import com.game.gameserver.common.entity.CreatureType;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.buffer.service.BufferService;
import com.game.gameserver.module.cooltime.service.CoolTimeService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.pet.model.Pet;
import com.game.gameserver.module.pet.service.PetService;
import com.game.gameserver.module.player.dao.PlayerDbService;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerPropertyService;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.skill.dao.SkillDbService;
import com.game.gameserver.module.skill.entity.SkillEntity;
import com.game.gameserver.module.skill.helper.SkillHelper;
import com.game.gameserver.module.skill.model.PlayerSkill;
import com.game.gameserver.module.skill.model.Skill;
import com.game.gameserver.module.skill.type.SkillType;
import com.game.gameserver.net.modelhandler.skill.SkillHandle;
import com.game.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 技能服务
 *
 * @author xuewenkang
 * @date 2020/6/11 10:34
 */
@Service
public class SkillService {

    @Autowired
    private SkillDbService skillDbService;
    @Autowired
    private PlayerPropertyService playerPropertyService;
    @Autowired
    private BufferService bufferService;
    @Autowired
    private CoolTimeService coolTimeService;
    @Autowired
    private PetService petService;

    public static SkillService instance;

    public SkillService(){
        instance = this;
    }

    /**
     * 获取可用技能
     *
     * @param creature
     * @return java.util.List<com.game.gameserver.module.skill.model.Skill>
     */
    public List<Skill> getUseAbleSkill(Creature creature){
        List<Skill> useAbleSkill = new ArrayList<>();
        // 获取冷却中的技能
        Map<Integer,Skill> cdSkillMap = creature.getInCdSkill();
        // 获取所有技能
        Map<Integer,Skill> skillMap = creature.getSkillMap();
        skillMap.values().forEach(skill -> {
            if(cdSkillMap.get(skill.getSkillId())==null){
                useAbleSkill.add(skill);
            }
        });
        return useAbleSkill;
    }

    /**
     * 加载用户技能
     *
     * @param player
     * @return void
     */
    public void loadPlayerSkill(Player player) {
        try{
        List<SkillEntity> skillEntities = skillDbService.skillEntityList(player.getId());
        PlayerSkill playerSkill = new PlayerSkill(player.getId());
        skillEntities.forEach(skillEntity -> {
            Skill skill = new Skill();
            BeanUtils.copyProperties(skillEntities, skill);
            SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap()
                    .get(skillEntity.getSkillId());
            skill.setSkillConfig(skillConfig);
            playerSkill.getSkillMap().put(skill.getSkillId(), skill);
        });
        player.setPlayerSkill(playerSkill);
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 展示当前职业技能列表
     *
     * @param player
     * @return void
     */
    public void showCareerSkill(Player player) {
        CareerConfig careerConfig = StaticConfigManager.getInstance()
                .getCareerConfigMap().get(player.getCareerId());
        List<Integer> skillList = careerConfig.getSkillIds();
        List<SkillConfig> skillConfigs = new ArrayList<>();
        skillList.forEach(skillId->{
            SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
            if(skillConfig!=null){
                skillConfigs.add(skillConfig);
            }
        });
        NotificationHelper.notifyPlayer(player, SkillHelper.buildSkillConfigList(skillConfigs));
    }


    /**
     *
     * @param player
     * @return void
     */
    public void showSkill(Player player){
        List<Skill> skills = new ArrayList<>(player.getPlayerSkill().getSkillMap().values());
        NotificationHelper.notifyPlayer(player,SkillHelper.buildSkillList(skills));
    }

    /**
     * 学习技能
     *
     * @param player
     * @param skillId
     * @return void
     */
    public void learnSkill(Player player,int skillId){
        PlayerSkill playerSkill = player.getPlayerSkill();
        if(playerSkill.getSkillMap().get(skillId)!=null){
            NotificationHelper.notifyPlayer(player,"您已经学习了该技能");
            return;
        }
        CareerConfig careerConfig = StaticConfigManager.getInstance()
                .getCareerConfigMap().get(player.getCareerId());
        List<Integer> skillList = careerConfig.getSkillIds();
        if(!skillList.contains(skillId)){
            NotificationHelper.notifyPlayer(player,"你不能学习本职业以外的技能");
        }
        SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
        if(skillConfig==null){
            NotificationHelper.notifyPlayer(player,"无此技能");
        }
        // 新增技能
        Skill skill = createSkill(player,skillConfig);
        playerSkill.getSkillMap().put(skillId,skill);
        // 异步存储
        skillDbService.insertAsync(skill);
        NotificationHelper.notifyPlayer(player, MessageFormat.format("学习技能 {0}",
                skill.getSkillConfig().getName()));
    }

    /**
     * 遗忘技能
     *
     * @param player
     * @param skillId
     * @return void
     */
    public void forgetSkill(Player player,int skillId){
        PlayerSkill playerSkill = player.getPlayerSkill();
        Skill skill = playerSkill.getSkillMap().get(skillId);
        if(skill==null){
            NotificationHelper.notifyPlayer(player,"你没有学习该技能");
            return;
        }
        // 移除该技能
        playerSkill.getSkillMap().remove(skillId);
        skillDbService.deleteAsync(skill);
        NotificationHelper.notifyPlayer(player, MessageFormat.format("已经遗忘技能 {0}",
                skill.getSkillConfig().getName()));
    }

    public void useSkill(Player player,long targetId,int targetType ,int skillId){

    }


    /**
     * 释放技能
     *
     * @param initiator
     * @param target
     * @param skill
     * @return void
     */
    public void castSkill(Creature initiator,Creature target,Skill skill){
        // 根据技能类型不同 释放不同的技能
        if(skill.getType() == SkillType.BUFFER.getType()){
            // buff 技能
            castBufferSkill(initiator,skill);
        }else if(skill.getType() == SkillType.DAMAGE.getType()){
            // 伤害型技能
            castDamageSkill(initiator,target,skill);
        }else if(skill.getType()==SkillType.TREATMENT.getType()){
            // 治疗性技能
            castTreatment(initiator,target,skill);
        }else if(skill.getType()==SkillType.SUMMONING.getType()){
            // 召唤型技能
            castSummoning(initiator,skill);
        }
        // 技能进入CD
        coolTimeService.skillInCd(initiator,skill);
    }

    private Skill createSkill(Player player,SkillConfig skillConfig){
        Skill skill = new Skill();
        skill.setSkillConfig(skillConfig);
        skill.setSkillId(skillConfig.getId());
        skill.setLevel(1);
        skill.setPlayerId(player.getId());
        return skill;
    }


    /**
     * 释放Bffer
     *
     * @param
     * @return void
     */
    private void castBufferSkill(Creature initiator,Skill skill){
        SkillConfig skillConfig = skill.getSkillConfig();
        if(skillConfig.getBuffer()!=-1){
            bufferService.addBuffer(initiator,skillConfig.getBuffer());
        }
        // 扣除蓝耗
        NotificationHelper.notifyScene(initiator.getScene(), MessageFormat
                .format("{0} 使用了 {1}",initiator.getName(),skillConfig.getName()));
    }

    /**
     * 释放伤害型技能
     *
     * @param initiator
     * @param target
     * @param skill
     * @return void
     */
    private void castDamageSkill(Creature initiator,Creature target,Skill skill){
        SkillConfig skillConfig = skill.getSkillConfig();
        // 如果不是怪物类型 则减少蓝量
        if(initiator.getType()!= CreatureType.MONSTER){
            initiator.setCurrMp(initiator.getCurrMp()-skillConfig.getConsume());
        }
        // buffer效果
        if(skillConfig.getBuffer()!=-1){
            bufferService.addBuffer(target,skillConfig.getBuffer());
        }
        // 伤害
        if(skillConfig.getFormula()!=null&&!skillConfig.getFormula().isEmpty()){
            String formula = skillConfig.getFormula();
            String ruleFormula = formula.replace("${0}", initiator.getAttack() + "");
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
            // 扣除目标血量
            target.setCurrHp(target.getCurrHp()-hurt);
            // 如果对方死亡 则设置为死亡状态
            if(target.getCurrHp()<=0){
                target.setCurrHp(0);
                target.setDead(true);
            }
        }
        NotificationHelper.notifyScene(initiator.getScene(), MessageFormat
                .format("{0} 对 {1} 使用了 {2}  ",initiator.getName(),
                        target.getName(),skillConfig.getName()));
    }

    /**
     * 治疗型技能
     *
     * @param initiator
     * @param target
     * @param skill
     * @return void
     */
    private void castTreatment(Creature initiator,Creature target,Skill skill){
        SkillConfig skillConfig = skill.getSkillConfig();
        if(skillConfig.getBuffer()!=-1){
            bufferService.addBuffer(initiator,skillConfig.getBuffer());
        }
        NotificationHelper.notifyScene(initiator.getScene(), MessageFormat
                .format("{0} 对 {1} 使用了 {2}",target.getName(),skillConfig.getName()));
    }

    /**
     * 召唤技能
     *
     * @param initiator
     * @param skill
     * @return void
     */
    private void castSummoning(Creature initiator,Skill skill){
       Scene scene =  initiator.getScene();
       if(scene==null){
           return;
       }
       if(skill.getSkillConfig().getSummon().size()==0){
           return;
       }
       // 获得召唤物列表
        List<Integer> summon = skill.getSkillConfig().getSummon();
        summon.forEach(summonId->{
            Pet pet = petService.createPet((Player) initiator,summonId);
            if(pet!=null){
                // 放入场景中
                pet.setCurrScene(scene);
                scene.getPetMap().put(pet.getId(),pet);
            }
        });
    }

}
