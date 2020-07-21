package com.game.gameserver.module.skill.helper;

import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.module.skill.model.Skill;

import java.util.List;

/**
 * 技能辅助类
 *
 * @author xuewenkang
 * @date 2020/7/16 15:14
 */
public class SkillHelper {

    public static String buildSkillConfigList(List<SkillConfig> skillConfigs){
        StringBuilder sb = new StringBuilder("技能列表:").append("\n");
        skillConfigs.forEach(skillConfig -> {
            sb.append(buildSkillConfigMsg(skillConfig)).append("\n");
        });
        return sb.toString();
    }

    public static String buildSkillConfigMsg(SkillConfig skillConfig){
        StringBuilder sb = new StringBuilder();
        sb.append("技能id").append(skillConfig.getId()).append("\n");
        sb.append("技能名称:").append(skillConfig.getName()).append("\n");
        sb.append("技能类型:").append(skillConfig.getType()).append("\n");
        sb.append("技能消耗:").append(skillConfig.getConsume()).append("\n");
        sb.append("学习等级:").append(skillConfig.getLimitLevel()).append("\n");
        sb.append("冷却时间:").append(skillConfig.getCoolTime()).append("\n");
        sb.append("技能介绍:").append(skillConfig.getDesc()).append("\n");
        return sb.toString();
    }

    public static String buildSkillList(List<Skill> skills){
        StringBuilder sb = new StringBuilder("已学习技能列表:").append("\n");
        skills.forEach(skill -> {
            sb.append(buildSkillMsg(skill)).append("\n");
        });
        return sb.toString();
    }

    public static String buildSkillMsg(Skill skill){
        return buildSkillConfigMsg(skill.getSkillConfig());
    }
}
