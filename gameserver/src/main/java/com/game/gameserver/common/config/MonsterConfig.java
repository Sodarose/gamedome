package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 怪物静态数据
 *
 * @author xuewenkang
 * @date 2020/6/8 21:00
 */
@Data
public class MonsterConfig {

    /** id */
    @JSONField(name = "id")
    private int id;

    /** 名称 */
    @JSONField(name = "name")
    private String name;

    /** 等级 */
    @JSONField(name = "level")
    private int level;

    /** 类型 */
    @JSONField(name = "type")
    private int type;

    /** hp */
    @JSONField(name = "hp")
    private int hp;

    /** mp */
    @JSONField(name = "mp")
    private int mp;

    /** 攻击力 */
    @JSONField(name = "attack")
    private int attack;

    /** 防御力 */
    @JSONField(name = "defense")
    private int defense;

    /** 技能列表 */
    @JSONField(name = "skills")
    private List<Integer> skills;

    /** 刷新时间 */
    @JSONField(name = "refreshTime")
    private Integer refreshTime;

    /** 掉落 */
    @JSONField(name = "drop")
    private String drop;
}
