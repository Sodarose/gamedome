package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/8 21:25
 */
@Data
public class SceneMonster {
    @JSONField(name = "monsterId")
    private int monsterId;
    @JSONField(name = "count")
    private int count;
}
