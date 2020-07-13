package com.game.gameserver.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.game.gameserver.module.skill.entity.Skill;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 静态配置管理对象
 *
 * @author xuewenkang
 * @date 2020/6/8 15:27
 */
@Getter
public class StaticConfigManager {
    public static final Logger logger = LoggerFactory.getLogger(StaticConfigManager.class);

    private final static StaticConfigManager INSTANCE = new StaticConfigManager();
    private final static String JSON_FILE_PATH = "json";

    private Map<Long, SceneConfig> sceneConfigMap = new HashMap<>(16);
    private Map<Integer, SceneNpcConfig> sceneNpcConfigMap = new HashMap<>(16);
    private Map<Integer, SceneMonsterConfig> sceneMonsterConfigMap = new HashMap<>(16);
    private Map<Integer, InstanceConfig> instanceConfigMap = new HashMap<>(16);
    private Map<Integer, InstanceMonsterConfig> instanceMonsterConfigMap = new HashMap<>(16);
    private Map<Integer, InstanceNpcConfig> instanceNpcConfigMap = new HashMap<>(16);
    private Map<Integer, NpcConfig> npcConfigMap = new HashMap<>(16);
    private Map<Integer, MonsterConfig> monsterConfigMap = new HashMap<>(16);
    private Map<Integer, EquipConfig> equipConfigMap = new HashMap<>(16);
    private Map<Integer, PropConfig> propConfigMap = new HashMap<>(16);
    private Map<Integer, CommodityConfig> commodityConfigMap = new HashMap<>(16);
    private Map<Integer, CareerConfig> careerConfigMap = new HashMap<>(16);
    private Map<Integer, SkillConfig>  skillConfigMap = new HashMap<>(16);
    private Map<Integer, PetConfig> petConfigMap = new HashMap<>(16);
    private Map<Integer, TaskConfig> taskConfigMap = new HashMap<>(16);
    private Map<Integer, AchievementConfig> achievementConfigMap = new HashMap<>(16);

    public static StaticConfigManager getInstance() {
        return INSTANCE;
    }

    private StaticConfigManager() {

    }

    public void loadConfig() {
        ClassLoader loader = this.getClass().getClassLoader();
        URL url = loader.getResource(JSON_FILE_PATH);
        if (url == null) {
            logger.warn("{} null exists", JSON_FILE_PATH);
            return;
        }
        String path = url.getFile();
        File fileDire = new File(path);
        if (!fileDire.exists() || !fileDire.isDirectory()) {
            logger.warn("{} null exists", JSON_FILE_PATH);
            return;
        }
        loadSceneConfig(path);
        loadSceneMonsterConfig(path);
        loadSceneNpcConfig(path);
        loadInstanceConfig(path);
        loadMonsterConfig(path);
        loadNpcConfig(path);
        loadInstanceMonsterConfig(path);
        loadInstanceNpcConfig(path);
        loadEquipConfig(path);
        loadPropConfig(path);
        loadCommodityConfig(path);
        loadCareerConfig(path);
        loadSkillConfig(path);
        loadTaskConfig(path);
        loadAchievementConfig(path);
    }

    private void loadSceneConfig(String path) {
        logger.info("load SceneConfig.json");
        String fileName = "SceneConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                SceneConfig sceneConfig = JSON.parseObject(jsonReader.readString(), SceneConfig.class);
                sceneConfigMap.put(sceneConfig.getId(), sceneConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSceneMonsterConfig(String path) {
        logger.info("load SceneMonsterConfig.json");
        String fileName = "SceneMonsterConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                SceneMonsterConfig sceneMonsterConfig = JSON.parseObject(jsonReader.readString(),
                        SceneMonsterConfig.class);
                sceneMonsterConfigMap.put(sceneMonsterConfig.getSceneId(), sceneMonsterConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSceneNpcConfig(String path) {
        logger.info("load SceneNpcConfig.json");
        String fileName = "SceneNpcConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                SceneNpcConfig sceneNpcConfig = JSON.parseObject(jsonReader.readString(), SceneNpcConfig.class);
                sceneNpcConfigMap.put(sceneNpcConfig.getId(), sceneNpcConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadMonsterConfig(String path) {
        logger.info("load MonsterConfig");
        String fileName = "MonsterConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                MonsterConfig monsterConfig = JSON.parseObject(jsonReader.readString(), MonsterConfig.class);
                monsterConfigMap.put(monsterConfig.getId(), monsterConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadNpcConfig(String path) {
        logger.info("load NpcConfig");
        String fileName = "npcConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                NpcConfig npcConfig = JSON.parseObject(jsonReader.readString(), NpcConfig.class);
                npcConfigMap.put(npcConfig.getId(), npcConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadInstanceConfig(String path) {
        logger.info("load InstanceConfig.json");
        String fileName = "InstanceConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                InstanceConfig instanceConfig = JSON.parseObject(jsonReader.readString(), InstanceConfig.class);
                instanceConfigMap.put(instanceConfig.getId(), instanceConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInstanceMonsterConfig(String path) {
        logger.info("load InstanceMonsterConfig.json");
        String fileName = "InstanceMonsterConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                InstanceMonsterConfig instanceMonsterConfig = JSON.parseObject(jsonReader.readString(), InstanceMonsterConfig.class);
                instanceMonsterConfigMap.put(instanceMonsterConfig.getId(), instanceMonsterConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInstanceNpcConfig(String path) {
        logger.info("load InstanceNpcConfig.json");
        String fileName = "InstanceNpcConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                InstanceNpcConfig instanceNpcConfig = JSON.parseObject(jsonReader.readString(), InstanceNpcConfig.class);
                instanceNpcConfigMap.put(instanceNpcConfig.getId(), instanceNpcConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEquipConfig(String path) {
        logger.info("load EquipConfig.json");
        String fileName = "EquipConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                EquipConfig equipConfig = JSON.parseObject(jsonReader.readString(), EquipConfig.class);
                equipConfigMap.put(equipConfig.getId(), equipConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPropConfig(String path) {
        logger.info("load PropConfig.json");
        String fileName = "PropConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                PropConfig propConfig = JSON.parseObject(jsonReader.readString(), PropConfig.class);
                propConfigMap.put(propConfig.getId(), propConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCommodityConfig(String path) {
        logger.info("load CommodityConfig.json");
        String fileName = "CommodityConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                CommodityConfig commodityConfig = JSON.parseObject(jsonReader.readString(), CommodityConfig.class);
                commodityConfigMap.put(commodityConfig.getId(), commodityConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCareerConfig(String path) {
        logger.info("load CareerConfig.json");
        String fileName = "CareerConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                CareerConfig careerConfig = JSON.parseObject(jsonReader.readString(), CareerConfig.class);
                careerConfigMap.put(careerConfig.getId(), careerConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSkillConfig(String path){
        logger.info("load SkillConfig.json");
        String fileName = "SkillConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                SkillConfig skillConfig = JSON.parseObject(jsonReader.readString(), SkillConfig.class);
                skillConfigMap.put(skillConfig.getId(),skillConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTaskConfig(String path){
        logger.info("load TaskConfig.json");
        String fileName = "TaskConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                TaskConfig taskConfig = JSON.parseObject(jsonReader.readString(), TaskConfig.class);
                taskConfigMap.put(taskConfig.getId(),taskConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAchievementConfig(String path){
        logger.info("load AchievementConfig.json");
        String fileName = "AchievementConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                AchievementConfig achievementConfig = JSON.parseObject(jsonReader.readString(), AchievementConfig.class);
                achievementConfigMap.put(achievementConfig.getId(),achievementConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
