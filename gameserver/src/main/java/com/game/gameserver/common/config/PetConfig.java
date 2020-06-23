package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/23 9:42
 */
@Data
public class PetConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "level")
    private int level;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "hp")
    private int hp;
    @JSONField(name = "mp")
    private int mp;
    @JSONField(name = "attack")
    private int attack;
    @JSONField(name = "defense")
    private int defense;
    @JSONField(name = "skills")
    private List<Integer> skills;
}
