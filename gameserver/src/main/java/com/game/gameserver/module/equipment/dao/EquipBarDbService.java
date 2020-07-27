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

    public EquipBarEntity select(long playerId) {
        return equipBarMapper.select(playerId);
    }

    public int insert(EquipBarEntity equipBarEntity) {
        return equipBarMapper.insert(equipBarEntity);
    }

    public int update(EquipBarEntity equipBarEntity) {
        return equipBarMapper.update(equipBarEntity);
    }

    public int delete(long playerId) {
        return equipBarMapper.delete(playerId);
    }

    public void insertAsync(EquipBarEntity equipBarEntity) {
        submit(() -> {
            int i = equipBarMapper.insert(equipBarEntity);
        });
    }

    public void updateAsync(EquipBarEntity equipBarEntity) {
        submit(() -> {
            int i = equipBarMapper.update(equipBarEntity);
        });
    }

    public void deleteAsync(long playerId) {
        submit(() -> {
            int i = equipBarMapper.delete(playerId);
        });
    }
}
