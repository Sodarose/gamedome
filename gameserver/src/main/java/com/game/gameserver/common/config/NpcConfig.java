package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * npc 静态数据
 *
 * @author xuewenkang
 * @date 2020/6/8 21:00
 */
@Data
public class NpcConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "level")
    private int level;
    @JSONField(name = "talk")
    private String talk;
}
