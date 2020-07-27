package com.game.gameserver.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 物静态资源配置
 *
 * @author xuewenkang
 * @date 2020/7/12 2:35
 */
@Data
public class ItemConfig {

    /** 物品编号 */
    @JSONField(name = "id")
    private Integer id;

    /** 物品类型 */
    @JSONField(name = "type")
    private Integer type;

    /** 物品名称 */
    @JSONField(name = "name")
    private String name;

    /** 使用等级 */
    @JSONField(name = "level")
    private Integer level;

    /** 装备/使用效果 */
    @JSONField(name = "bufferId")
    private Integer bufferId;

    /** 装备/使用冷却时间 */
    @JSONField(name = "coolTime")
    private Integer coolTime;

    /** 装备部位 */
    @JSONField(name = "part")
    private Integer part;

    /** 装备星级 */
    @JSONField(name = "star")
    private Integer star;

    /** 物品持有属性  根据物品类型的不同而不同*/
    @JSONField(name = "property")
    private Property property;

    /** 售出价格 */
    @JSONField(name = "price")
    private int price;

    /** 最大耐久度 */
    @JSONField(name = "maxDurability")
    private Integer maxDurability;

    /** 是否可堆叠 */
    @JSONField(name = "overlap")
    private boolean overlap;

    /** 最大堆叠数量 */
    @JSONField(name = "overNum")
    private Integer overNum;

    /** 物品描述 */
    @JSONField(name = "desc")
    private String desc;


}
