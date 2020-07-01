package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/8 21:31
 */
@Data
public class SceneNpc {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "npcId")
    private int npcId;
}
