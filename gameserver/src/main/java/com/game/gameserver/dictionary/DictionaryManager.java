package com.game.gameserver.dictionary;

import com.game.gameserver.dictionary.dict.*;
import com.game.gameserver.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 文档管理 读取xlsx中的数据
 * @author xuewenkang
 * @date 2020/5/21 15:15
 */
@Component
public class DictionaryManager {

    private final static Logger logger = LoggerFactory.getLogger(DictionaryManager.class);

    private final static DictionaryData data = new DictionaryData();

    public DictionaryManager(){

    }

    public void loadConfig(){
        data.loadConfig();
    }


    public List<DictScene> getDictSceneList(){
        return data.getDictScenes();
    }

    /**
     * 根据职业ID搜查职业名称
     * @param id
     * @return java.lang.String
     */
    public String getRoleCareerName(Integer id){
        List<DictRoleCareer> roleCareers = data.getDictRoleCareers();
        for(DictRoleCareer roleCareer:roleCareers){
            if(roleCareer.getId().equals(id)){
                return roleCareer.getName();
            }
        }
        return null;
    }

    /**
     * 根据装备ID 返回装备资料
     * @param id id
     * @return com.game.gameserver.dictionary.dict.DictEquip
     */
    public DictEquip getDictEquipById(Integer id){
        List<DictEquip> dictEquips = data.getDictEquips();
        for(DictEquip dictEquip:dictEquips){
            if(dictEquip.getId().equals(id)){
                return dictEquip;
            }
        }
        return null;
    }

    /**
     * 根据ID查找道具
     * @param id
     * @return com.game.gameserver.dictionary.dict.DictItem
     */
    public DictItem getDictItemById(Integer id){
        List<DictItem> dictItems = data.getDictItems();
        for(DictItem dictItem:dictItems){
            if(dictItem.getId().equals(id)){
                return dictItem;
            }
        }
        return null;
    }

    /**
     * 根据职业和等级 返回对应的基础属性
     * @param career 职业
     * @param level 等级
     * @return com.game.gameserver.dictionary.dict.DictRoleLevelProperty
     */
    public DictRoleLevelProperty getDictRoleLevelProperty(Integer career,Integer level){
        List<DictRoleLevelProperty> dictRoleLevelProperties = data.getDictRoleLevelProperties();
        for(DictRoleLevelProperty property:dictRoleLevelProperties){
            if(property.getCareerId().equals(career)&&property.getLevel().equals(level)){
                return property;
            }
        }
        return null;
    }
}
