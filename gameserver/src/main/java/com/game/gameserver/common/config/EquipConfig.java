package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 装备静态数据表
 *
 * @author xuewenkang
 * @date 2020/6/10 15:05
 */
@Data
public class EquipConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "level")
    private int level;
    @JSONField(name = "maxDurability")
    private int maxDurability;
    @JSONField(name = "hp")
    private int hp;
    @JSONField(name = "mp")
    private int mp;
    @JSONField(name = "attack")
    private int attack;
    @JSONField(name = "defense")
    private int defense;
    @JSONField(name = "price")
    private int price;
    @JSONField(name = "desc")
    private String desc;
}
