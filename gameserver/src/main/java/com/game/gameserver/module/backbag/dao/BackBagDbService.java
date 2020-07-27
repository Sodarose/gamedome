package com.game.gameserver.module.backbag.dao;

import com.alibaba.fastjson.JSON;
import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.backbag.entity.BackBagEntity;
import com.game.gameserver.module.backbag.model.BackBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/12 16:30
 */
@Repository
public class BackBagDbService extends BaseDbService {
    private final static Logger logger =LoggerFactory.getLogger(BackBagDbService.class);

    @Autowired
    private BackBagMapper backBagMapper;

    public BackBagEntity select(long playerId){
        return backBagMapper.select(playerId);
    }

    public int insert(BackBagEntity backBagEntity){
        return backBagMapper.insert(backBagEntity);
    }

    public int update(BackBagEntity backBagEntity){
        return backBagMapper.update(backBagEntity);
    }

    public int delete(long playerId){
        return backBagMapper.delete(playerId);
    }

    public void insertAsync(BackBagEntity backBagEntity){
        submit(()->{
            insert(backBagEntity);
        });
    }

    public void updateAsync(BackBagEntity backBagEntity){
        submit(()->{
            int i = update(backBagEntity);
        });
    }

    public void deleteAsync(long playerId){
        submit(()->{
            int i = delete(playerId);
        });
    }
}
