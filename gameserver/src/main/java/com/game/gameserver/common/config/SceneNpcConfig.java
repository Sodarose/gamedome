package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 场景Npc配置
 *
 * @author xuewenkang
 * @date 2020/6/8 18:12
 */
@Data
public class SceneNpcConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "sceneId")
    private int sceneId;
    @JSONField(name = "sceneNpc")
    private List<SceneNpc> sceneNpcList;
}
