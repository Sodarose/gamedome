package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 副本怪物配置
 *
 * @author xuewenkang
 * @date 2020/6/9 14:32
 */
@Data
public class InstanceMonsterConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "instanceId")
    private int instanceId;
    @JSONField(name = "instanceMonster")
    private List<InstanceMonster> instanceMonsterList;
}
