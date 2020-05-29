package com.game.gameserver.module.equip.facade.impl;

import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.dictionary.dict.DictEquip;
import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.equip.dao.EquipMapper;
import com.game.gameserver.module.equip.entity.EquipBar;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.equip.facade.EquipFacade;
import com.game.gameserver.module.equip.factory.EquipEntityFactory;
import com.game.gameserver.module.equip.model.EquipModel;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.model.ItemType;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/25 17:59
 */
@Service
public class EquipFacadeImpl implements EquipFacade {
    private final static Logger logger = LoggerFactory.getLogger(EquipFacadeImpl.class);

    @Autowired
    private EquipMapper equipMapper;
    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private DictionaryManager dictionaryManager;

    @Override
    public List<Equip> getEquipListByRoleId(Integer roleId) {
        List<EquipModel> equipModels = equipMapper.getIsEquipmentEquipByRoleId(roleId);
        if(equipModels==null||equipModels.size()==0){
            return null;
        }
        List<Equip> equipEntities = new ArrayList<>();
        for(EquipModel equipModel:equipModels){
            equipEntities.add(EquipEntityFactory.createEquipEntity(equipModel));
        }
        return equipEntities;
    }

    @Override
    public EquipBar getEquipBarByRoleId(Integer roleId) {
        EquipBar equipBar = new EquipBar();
        List<Equip> equips = getEquipListByRoleId(roleId);
        equipBar.init(equips);
        return equipBar;
    }

    /**
     * 获得背包内的装备列表
     *
     * @param bagId
     * @return java.util.Map<java.lang.Integer, com.game.gameserver.module.equip.entity.Equip>
     */
    @Override
    public List<Equip> getEquipMapByBagId(Integer bagId) {
        List<EquipModel> equipModels = equipMapper.getEquipListByBagId(bagId);
        List<Equip> equips = new ArrayList<>();
        for(EquipModel equipModel:equipModels){
           equips.add(EquipEntityFactory.createEquipEntity(equipModel));
        }
        return equips;
    }

}
