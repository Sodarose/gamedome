package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 技能静态数据配置
 * @author xuewenkang
 * @date 2020/6/11 14:00
 */
@Data
public class SkillConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "careerId")
    private int careerId;
    @JSONField(name = "limitLevel")
    private int limitLevel;
    @JSONField(name = "maxLearnLevel")
    private int maxLearnLevel;
    @JSONField(name = "coolTime")
    private int coolTime;
    @JSONField(name = "formula")
    private String formula;
    @JSONField(name = "desc")
    private String desc;
}
