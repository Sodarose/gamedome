package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/8 21:25
 */
@Data
public class SceneMonster {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "initCount")
    private int initCount;
}
