package com.game.gameserver.module.equip.facade.impl;

import com.game.gameserver.module.equip.dao.EquipMapper;
import com.game.gameserver.module.equip.entity.EquipBar;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.equip.facade.EquipFacade;
import com.game.gameserver.module.equip.factory.EquipEntityFactory;
import com.game.gameserver.module.equip.model.EquipModel;
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



    @Override
    public Equip getEquipByItemId(Integer itemId) {
        EquipModel equipModel = equipMapper.getEquipByItemId(itemId);
        return EquipEntityFactory.createEquipEntity(equipModel);
    }
}
