package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 副本关卡配置
 *
 * @author xuewenkang
 * @date 2020/7/17 16:09
 */
@Data
public class InstanceCheckPointConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "roundAmount")
    private int roundAmount;
    @JSONField(name = "checkpointConfig")
    private List<CheckPointConfig> checkPointConfigList;
}
