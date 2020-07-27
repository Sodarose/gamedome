package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 技能静态数据配置
 * @author xuewenkang
 * @date 2020/6/11 14:00
 */
@Data
public class SkillConfig {

    /** 技能Id */
    @JSONField(name = "id")
    private int id;

    /** 技能名称 */
    @JSONField(name = "name")
    private String name;

    /** 技能类型 */
    @JSONField(name = "type")
    private int type;

    /** 技能范围 */
    @JSONField(name = "range")
    private int range;

    /** 消耗品 */
    @JSONField(name = "consume")
    private int consume = 0;

    /** 技能限制等级 */
    @JSONField(name = "limitLevel")
    private int limitLevel;

    /** 最大等级限制 */
    @JSONField(name = "maxLevel")
    private int maxLevel;

    /** 冷却时间 */
    @JSONField(name = "coolTime")
    private int coolTime;

    @JSONField(name = "buffer")
    private int buffer;

    /** 技能公式 */
    @JSONField(name = "formula")
    private String formula;

    /** 技能召唤物 */
    @JSONField(name = "summon")
    private List<Integer> summon;

    /** 技能描述 */
    @JSONField(name = "desc")
    private String desc;
}
