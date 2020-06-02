package com.game.gameserver.dictionary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.game.gameserver.dictionary.entity.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xuewenkang
 * @date 2020/6/2 17:02
 */
@Getter
public class StaticDataManager {
    public static final Logger logger = LoggerFactory.getLogger(StaticDataManager.class);
    private final static StaticDataManager INSTANCE = new StaticDataManager();


    private final static String JSON_FILE_PATH = "json";

    public static StaticDataManager getInstance(){
        return INSTANCE;
    }

    private Map<Integer, MedicamentData> medicamentDict = new HashMap<>(1);
    private Map<Integer, CareerData> careerDict = new HashMap<>(1);
    private Map<Integer, CareerLevelPropertyData> careerLevelPropertyDict = new HashMap<>(1);
    private Map<Integer, EquipData> equipDict = new HashMap<>(1);
    private Map<Integer, ItemData> itemDict = new HashMap<>(1);
    private Map<Integer, MonsterData> monsterDict = new HashMap<>(1);
    private Map<Integer, NpcData> npcDict = new HashMap<>(1);
    private Map<Integer, SceneData> sceneDict = new HashMap<>(1);
    private Map<Integer,SceneConfig> sceneConfigDict = new HashMap<>(1);
    private Map<Integer, SkillData> skillDict = new HashMap<>(1);

    private StaticDataManager(){
    }

    public void loadConfig(){
        ClassLoader loader = this.getClass().getClassLoader();
        URL url = loader.getResource(JSON_FILE_PATH);
        if(url==null){
            logger.warn("{} null exists",JSON_FILE_PATH);
            return;
        }
        String path = url.getFile();
        File fileDire = new File(path);
        if(!fileDire.exists()||!fileDire.isDirectory()){
            logger.warn("{} null exists",JSON_FILE_PATH);
            return;
        }
        loadStaticMedicament(path);
        loadStaticCareer(path);
        loadStaticCareerLevelProperty(path);
        loadStaticEquip(path);
        loadStaticItem(path);
        loadStaticMonster(path);
        loadStaticNpc(path);
        loadStaticScene(path);
        loadStaticSceneConfig(path);
        loadStaticSkill(path);
    }

    private void loadStaticMedicament(String path){
        String fileName = "Medicament.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                MedicamentData medicamentData = JSON.parseObject(jsonReader.readString(), MedicamentData.class);
                medicamentDict.put(medicamentData.getId(), medicamentData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticCareer(String path){
        String fileName = "Career.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                CareerData careerData = JSON.parseObject(jsonReader.readString(), CareerData.class);
                careerDict.put(careerData.getId(), careerData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticCareerLevelProperty(String path){
        String fileName = "CareerLevelProperty.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                CareerLevelPropertyData careerLevelPropertyData = JSON.parseObject(jsonReader.
                        readString(), CareerLevelPropertyData.class);
                careerLevelPropertyDict.put(careerLevelPropertyData.getId(), careerLevelPropertyData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticEquip(String path){
        String fileName = "Equip.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                EquipData equipData = JSON.parseObject(jsonReader.readString(), EquipData.class);
                equipDict.put(equipData.getId(), equipData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticItem(String path){
        String fileName = "Item.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                ItemData itemData = JSON.parseObject(jsonReader.readString(), ItemData.class);
                itemDict.put(itemData.getId(), itemData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticMonster(String path){
        String fileName = "Monster.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                MonsterData monsterData = JSON.parseObject(jsonReader.readString(), MonsterData.class);
                monsterDict.put(monsterData.getId(), monsterData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticNpc(String path){
        String fileName = "Npc.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                NpcData npcData = JSON.parseObject(jsonReader.readString(), NpcData.class);
                npcDict.put(npcData.getId(), npcData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticScene(String path){
        String fileName = "Scene.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                SceneData sceneData = JSON.parseObject(jsonReader.readString(), SceneData.class);
                sceneDict.put(sceneData.getId(), sceneData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticSceneConfig(String path){
        String fileName = "SceneConfig.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                SceneConfig sceneConfig = JSON.parseObject(jsonReader.readString(),SceneConfig.class);
                sceneConfigDict.put(sceneConfig.getId(),sceneConfig);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaticSkill(String path){
        String fileName = "Skill.json";
        path+="/"+fileName;
        try {
            JSONReader jsonReader = new JSONReader(new InputStreamReader(new FileInputStream(path)));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                SkillData skillData = JSON.parseObject(jsonReader.readString(), SkillData.class);
                skillDict.put(skillData.getId(), skillData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args){
        StaticDataManager.getInstance().loadConfig();
    }

}
