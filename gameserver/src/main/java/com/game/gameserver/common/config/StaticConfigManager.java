package com.game.gameserver.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
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

    private Map<Integer, SceneConfig> sceneConfigMap = new HashMap<>(16);
    private Map<Integer, InstanceConfig> instanceConfigMap = new HashMap<>(16);
    private Map<Integer, InstanceCheckPointConfig> integerInstanceCheckpointConfigMap = new HashMap<>(16);
    private Map<Integer, NpcConfig> npcConfigMap = new HashMap<>(16);
    private Map<Integer, MonsterConfig> monsterConfigMap = new HashMap<>(16);
    private Map<Integer, CommodityConfig> commodityConfigMap = new HashMap<>(16);
    private Map<Integer, CareerConfig> careerConfigMap = new HashMap<>(16);
    private Map<Integer, SkillConfig> skillConfigMap = new HashMap<>(16);
    private Map<Integer, PetConfig> petConfigMap = new HashMap<>(16);
    private Map<Integer, TaskConfig> taskConfigMap = new HashMap<>(16);
    private Map<Integer, AchievementConfig> achievementConfigMap = new HashMap<>(16);
    private Map<Integer, GuildLevelConfig> guildLevelConfigMap = new HashMap<>(16);
    private Map<Integer, ItemConfig> itemConfigMap = new HashMap<>(16);
    private Map<Integer, ShopConfig> shopConfigMap = new HashMap<>(16);
    private Map<Integer, GoodsConfig> goodsConfigMap = new HashMap<>(16);
    private Map<Integer, BufferConfig> bufferConfigMap = new HashMap<>(16);
    private Map<Integer, LevelConfig> levelConfigMap = new HashMap<>(16);

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
        loadInstanceConfig(path);
        loadMonsterConfig(path);
        loadNpcConfig(path);
        loadItemConfig(path);
        loadCommodityConfig(path);
        loadCareerConfig(path);
        loadSkillConfig(path);
        loadTaskConfig(path);
        loadAchievementConfig(path);
        loadUnionLevelConfig(path);
        loadShopConfig(path);
        loadGoodsConfig(path);
        loadBufferConfig(path);
        loadInstanceCheckPointConfig(path);
        loadPetConfig(path);
        loadLevelConfig(path);
    }

    private void loadLevelConfig(String path) {
        logger.info("load LevelConfig.json");
        String fileName = "LevelConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                LevelConfig levelConfig = JSON.parseObject(jsonReader.readString(),
                        LevelConfig.class);
                levelConfigMap.put(levelConfig.getLevel(), levelConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPetConfig(String path) {
        logger.info("load PetConfig.json");
        String fileName = "PetConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                PetConfig petConfig = JSON.parseObject(jsonReader.readString(),
                        PetConfig.class);
                petConfigMap.put(petConfig.getId(), petConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInstanceCheckPointConfig(String path) {
        logger.info("load InstanceCheckPointConfig.json");
        String fileName = "InstanceCheckPointConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                InstanceCheckPointConfig instanceCheckPointConfig = JSON.parseObject(jsonReader.readString(),
                        InstanceCheckPointConfig.class);
                integerInstanceCheckpointConfigMap.put(instanceCheckPointConfig.getId(), instanceCheckPointConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBufferConfig(String path) {
        logger.info("load BufferConfig.json");
        String fileName = "BufferConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                BufferConfig bufferConfig = JSON.parseObject(jsonReader.readString(), BufferConfig.class);
                bufferConfigMap.put(bufferConfig.getId(), bufferConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取商品配置
     *
     * @param path
     * @return void
     */
    private void loadGoodsConfig(String path) {
        logger.info("load GoodsConfig.json");
        String fileName = "GoodsConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                GoodsConfig goodsConfig = JSON.parseObject(jsonReader.readString(), GoodsConfig.class);
                goodsConfigMap.put(goodsConfig.getId(), goodsConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取商店配置
     *
     * @param path
     * @return void
     */
    private void loadShopConfig(String path) {
        logger.info("load ShopConfig.json");
        String fileName = "ShopConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                ShopConfig shopConfig = JSON.parseObject(jsonReader.readString(), ShopConfig.class);
                shopConfigMap.put(shopConfig.getId(), shopConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    private void loadItemConfig(String path) {
        logger.info("load ItemConfig.json");
        String fileName = "ItemConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                ItemConfig itemConfig = JSONObject.parseObject(jsonReader.readString(), ItemConfig.class);
                itemConfigMap.put(itemConfig.getId(), itemConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCommodityConfig(String path) {
       /* logger.info("load ShopConfig.json");
        String fileName = "ShopConfig.json";
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
        }*/
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

    private void loadSkillConfig(String path) {
        logger.info("load SkillConfig.json");
        String fileName = "SkillConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                SkillConfig skillConfig = JSON.parseObject(jsonReader.readString(), SkillConfig.class);
                skillConfigMap.put(skillConfig.getId(), skillConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTaskConfig(String path) {
        logger.info("load TaskConfig.json");
        String fileName = "TaskConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                TaskConfig taskConfig = JSON.parseObject(jsonReader.readString(), TaskConfig.class);
                taskConfigMap.put(taskConfig.getId(), taskConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAchievementConfig(String path) {
        logger.info("load AchievementConfig.json");
        String fileName = "AchievementConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                AchievementConfig achievementConfig = JSON.parseObject(jsonReader.readString(), AchievementConfig.class);
                achievementConfigMap.put(achievementConfig.getId(), achievementConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUnionLevelConfig(String path) {
        logger.info("load GuildLevelConfig.json");
        String fileName = "GuildLevelConfig.json";
        path += "/" + fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                GuildLevelConfig guildLevelConfig = JSON.parseObject(jsonReader.readString(), GuildLevelConfig.class);
                guildLevelConfigMap.put(guildLevelConfig.getLevel(), guildLevelConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
