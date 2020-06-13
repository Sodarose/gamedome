package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 场景静态配置
 *
 * @author xuewenkang
 * @date 2020/6/8 16:46
 */
@Data
public class SceneConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "desc")
    private String desc;
    @JSONField(name = "exitWays")
    private List<Integer> exitWays;
    @JSONField(name = "sceneMonsterConfigId")
    private int sceneMonsterConfigId;
    @JSONField(name = "sceneNpcConfigId")
    private int sceneNpcConfigId;
}
