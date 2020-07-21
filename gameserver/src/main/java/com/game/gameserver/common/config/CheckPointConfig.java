package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/17 18:35
 */
@Data
public class CheckPointConfig {
    @JSONField(name = "round")
    private int round;
    @JSONField(name = "monsters")
    private List<Integer> monsters;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "desc")
    private String desc;
}
