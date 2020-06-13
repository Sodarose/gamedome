package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 职业对应等级的属性
 *
 * @author xuewenkang
 * @date 2020/6/11 9:41
 */
@Data
public class CareerLevelProperty {
    @JSONField(name = "level")
    private int level;
    @JSONField(name = "hp")
    private int hp;
    @JSONField(name = "mp")
    private int mp;
    @JSONField(name = "attack")
    private int attack;
    @JSONField(name = "defense")
    private int defense;
}
