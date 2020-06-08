package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 场景的怪物配置
 *
 * @author xuewenkang
 * @date 2020/6/8 18:11
 */
@Data
public class SceneMonsterConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "sceneMonster")
    private List<SceneMonster> sceneMonsterList;
}
