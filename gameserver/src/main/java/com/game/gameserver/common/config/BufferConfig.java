package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/13 0:56
 */
@Data
public class BufferConfig {
    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "name")
    private String  name;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "effect")
    private String effect;
    @JSONField(name = "duration")
    private long duration;
    @JSONField(name = "intervalTime")
    private int intervalTime;
}
