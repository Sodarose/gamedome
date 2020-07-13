package com.game.gameserver.module.equipment.dao;

import com.alibaba.fastjson.JSON;
import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.backbag.entity.BackBagEntity;
import com.game.gameserver.module.equipment.entity.EquipBarEntity;
import com.game.gameserver.module.equipment.model.EquipBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/12 17:49
 */
@Repository
public class EquipBarDbService extends BaseDbService {

    @Autowired
    private EquipBarMapper equipBarMapper;

    public EquipBarEntity select(long playerId){
        return equipBarMapper.select(playerId);
    }

    public int insert(EquipBar equipBar){
        // 将道具列表转化为JSON
        String items = JSON.toJSONString(equipBar.getEquipMap());
        // 创建实体对象
        EquipBarEntity equipBarEntity = new EquipBarEntity(equipBar.getPlayerId(),items);
        return equipBarMapper.insert(equipBarEntity);
    }

    public int update(EquipBar equipBar){
        return 0;
    }

    public int delete(EquipBar equipBar){
        return 0;
    }

    public void insertAsync(EquipBar equipBar){
        return;
    }

    public void updateAsync(EquipBar equipBar){

    }

    public void deleteAsync(EquipBar equipBar){

    }
}
