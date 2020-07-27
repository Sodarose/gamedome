package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/23 17:05
 */
@Data
public class LevelConfig {
    @JSONField(name = "level")
    private int level;
    @JSONField(name = "needExpr")
    private int needExpr;
    @JSONField(name = "property")
    private Property property;
}
