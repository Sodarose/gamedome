package com.game.gameserver.module.equip.factory;

import com.game.gameserver.context.ServerContext;
import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.dictionary.dict.DictEquip;
import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.equip.model.EquipModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author xuewenkang
 * @date 2020/5/26 9:48
 */
public class EquipEntityFactory {
    private final static Logger logger = LoggerFactory.getLogger(EquipEntityFactory.class);

    public static Equip createEquipEntity(EquipModel equipModel){
        ApplicationContext applicationContext = ServerContext.getApplication();
        DictionaryManager dictionaryManager =  applicationContext.getBean(DictionaryManager.class);
        Equip equip = new Equip();
        equip.setId(equipModel.getId());

        DictItem dictItem = dictionaryManager.getDictItemById(equipModel.getItemId());
        if(dictItem==null){
            logger.error("无法在DictionaryManager中找到ID为{}的道具数据",equipModel.getItemId());
            return null;
        }
        equip.setDictItem(dictItem);
        equip.setItemType(equipModel.getItemType());
        equip.setRoleId(equipModel.getRoleId());
        equip.setBagId(equipModel.getBagId());
        equip.setBagIndex(equipModel.getBagIndex());
        equip.setItemCount(equipModel.getItemCount());

        DictEquip dictEquip = dictionaryManager.getDictEquipById(equipModel.getEquipId());
        if(dictEquip==null){
            logger.error("无法在DictionaryManager中找到ID为{}的装备数据",equipModel.getEquipId());
            return null;
        }
        equip.setDictEquip(dictEquip);
        equip.setDurability(equipModel.getDurability());
        equip.setEquipment(equipModel.getEquipment());
        return equip;
    }
}
