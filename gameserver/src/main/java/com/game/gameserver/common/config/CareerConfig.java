package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 职业信息静态配置
 *
 * @author xuewenkang
 * @date 2020/6/11 9:29
 */
@Data
public class CareerConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "desc")
    private String desc;
    @JSONField(name = "careerLevelProperty")
    private List<CareerLevelProperty> careerLevelProperty;
    @JSONField(name = "skillIds")
    private List<Integer> skillIds;
}
