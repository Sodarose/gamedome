package com.game.gameserver.dictionary;

import com.game.gameserver.dictionary.dict.*;
import com.game.gameserver.util.ExcelUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态变量存储
 * @author xuewenkang
 * @date 2020/5/24 22:44
 */
@Data
public class DictionaryData {
    public static final Logger logger = LoggerFactory.getLogger(DictionaryData.class);
    private final static String FILE_SUFFIX = "xlsx";
    private final static String TABLE_PATH = "xlxs";

    /**
     * 静态数据存储
     */
    private List<DictBuffer> dictBuffers = new ArrayList<>();
    private List<DictBufferType> dictBufferTypes = new ArrayList<>();
    private List<DictEffect> dictEffects = new ArrayList<>();
    private List<DictEquip> dictEquips = new ArrayList<>();
    private List<DictEquipPart> dictEquipParts = new ArrayList<>();
    private List<DictItem> dictItems = new ArrayList<>();
    private List<DictItemType> dictItemTypes = new ArrayList<>();
    private List<DictMonster> dictMonsters = new ArrayList<>();
    private List<DictMonsterType>  dictMonsterTypes = new ArrayList<>();
    private List<DictNpc> dictNpcs = new ArrayList<>();
    private List<DictQuality> dictQualities = new ArrayList<>();
    private List<DictRoleCareer> dictRoleCareers = new ArrayList<>();
    private List<DictRoleLevelProperty> dictRoleLevelProperties = new ArrayList<>();
    private List<DictRoleProperty> dictRoleProperties = new ArrayList<>();
    private List<DictScene> dictScenes = new ArrayList<>();
    private List<DictSkill> dictSkills = new ArrayList<>();


    public void loadConfig(){
        logger.info("读取本地配置表");
        ClassLoader loader = this.getClass().getClassLoader();
        URL url = loader.getResource(TABLE_PATH);
        if(url==null){
            logger.warn("{} null exists",TABLE_PATH);
            return;
        }
        String path = url.getFile();
        loadDictBuff(path);
        loadDictBuffType(path);
        //loadDictEffect(path);
        loadDictEquip(path);
        loadDictEquipParts(path);
        loadDictItem(path);
        loadDictItemType(path);
        loadDictQuality(path);
        loadDictMonster(path);
        loadDictMonsterType(path);
        loadDictNpc(path);
        loadDictRoleCareer(path);
        loadDictRoleLevelProperty(path);
        loadDictRoleProperty(path);
        loadDictScene(path);
        loadDictSkill(path);
    }

    private void loadDictBuff(String path){
        path += "/DictBuff.xlsx";
        logger.info("load DictBuff by {} ",path);
        dictBuffers = ExcelUtil.parseExcel(path,DictBuffer.class);
    }

    private void loadDictBuffType(String path){
        path += "/DictBuffType.xlsx";
        logger.info("load ictBuffType by {} ",path);
        dictBufferTypes = ExcelUtil.parseExcel(path,DictBufferType.class);
    }


    private void loadDictEffect(String path){
        path += "/DictEffect.xlsx";;
        logger.info("load DictEffect by {} ",path);
        ExcelUtil.parseExcel(path,DictEffect.class);
    }

    private void loadDictEquip(String path){
        path += "/DictEquip.xlsx";
        logger.info("load DictEquip by {} ",path);
        dictEquips = ExcelUtil.parseExcel(path,DictEquip.class);
    }

    private void loadDictEquipParts(String path){
        path += "/DictEquipPart.xlsx";;
        logger.info("load DictEquipType by {} ",path);
        dictEquipParts = ExcelUtil.parseExcel(path, DictEquipPart.class);
    }

    private void loadDictItem(String path){
        path += "/DictItem.xlsx";;
        logger.info("load DictItem by {} ",path);
        dictItems = ExcelUtil.parseExcel(path,DictItem.class);
    }

    private void loadDictQuality(String path){
        path += "/DictQuality.xlsx";;
        logger.info("load DictQuality by {} ",path);
        dictQualities = ExcelUtil.parseExcel(path,DictQuality.class);
    }

    private void loadDictItemType(String path){
        path += "/DictItemType.xlsx";;
        logger.info("load DictItemType by {} ",path);
        dictItemTypes = ExcelUtil.parseExcel(path,DictItemType.class);
    }

    private void loadDictMonster(String path){
        path += "/DictMonster.xlsx";;
        logger.info("load DictMonster by {} ",path);
        dictMonsters = ExcelUtil.parseExcel(path,DictMonster.class);
    }

    private void loadDictMonsterType(String path){
        path += "/DictMonsterType.xlsx";;
        logger.info("load DictMonsterType by {} ",path);
        dictMonsterTypes = ExcelUtil.parseExcel(path,DictMonsterType.class);
    }

    private void loadDictNpc(String path){
        path += "/DictNpc.xlsx";;
        logger.info("load DictNpc by {} ",path);
        dictNpcs = ExcelUtil.parseExcel(path,DictNpc.class);
    }

    private void loadDictRoleCareer(String path){
        path += "/DictRoleCareer.xlsx";;
        logger.info("load DictRoleCareer by {} ",path);
        dictRoleCareers = ExcelUtil.parseExcel(path,DictRoleCareer.class);
    }

    private void loadDictRoleLevelProperty(String path){
        path += "/DictRoleLevelProperty.xlsx";;
        logger.info("load DictRoleLevelProperty by {} ",path);
        dictRoleLevelProperties =  ExcelUtil.parseExcel(path,DictRoleLevelProperty.class);
    }

    private void loadDictRoleProperty(String path){
        path += "/DictRoleProperty.xlsx";;
        logger.info("load DictRoleProperty by {} ",path);
        dictRoleProperties = ExcelUtil.parseExcel(path,DictRoleProperty.class);
    }

    private void loadDictScene(String path){
        path += "/DictScene.xlsx";;
        logger.info("load DictScene by {} ",path);
        dictScenes = ExcelUtil.parseExcel(path,DictScene.class);
    }

    private void loadDictSkill(String path){
        path += "/DictSkill.xlsx";;
        logger.info("load DictSkill by {} ",path);
        ExcelUtil.parseExcel(path,DictSkill.class);
    }
}
