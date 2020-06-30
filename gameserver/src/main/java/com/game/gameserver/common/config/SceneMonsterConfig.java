package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @JSONField(name = "sceneId")
    private int sceneId;
    @JSONField(name = "sceneMonsterConfig")
    private String sceneMonsterConfig;

    @JSONField(serialize = false)
    private Map<Integer,Integer> sceneMonsterConfigMap;

    public Map<Integer,Integer> getSceneMonsterConfigMap(){
        if(sceneMonsterConfigMap!=null){
            return sceneMonsterConfigMap;
        }
        sceneMonsterConfigMap = new HashMap<>();
        return sceneMonsterConfigMap;
    }
}
