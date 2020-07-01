package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 *
 * @author xuewenkang
 * @date 2020/6/9 14:33
 */
@Data
public class InstanceMonster {
    @JSONField(name = "roundIndex")
    private int roundIndex;
    @JSONField(name = "monsterId")
    private int monsterId;
    @JSONField(name = "count")
    private int count;
}
