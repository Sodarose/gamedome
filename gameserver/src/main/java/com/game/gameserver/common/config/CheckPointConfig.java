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
    /** 当前关卡 */
    @JSONField(name = "round")
    private int round;

    /** 怪物列表 */
    @JSONField(name = "monsters")
    private List<Integer> monsters;

    /** 通关条件 */
    @JSONField(name = "condition")
    private int condition;

    /** 当前关卡介绍 */
    @JSONField(name = "desc")
    private String desc;
}
