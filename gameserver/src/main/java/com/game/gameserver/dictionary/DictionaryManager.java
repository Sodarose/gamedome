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
}
