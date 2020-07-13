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



    public int insert(BackBag backBag){
        // 将道具列表转化为JSON
        String items = JSON.toJSONString(backBag.getItemMap());
        // 创建实体对象
        BackBagEntity backBagEntity = new BackBagEntity(backBag.getPlayerId(),backBag.getCapacity(),items);
        int i = backBagMapper.insert(backBagEntity);
        if(i==0){
            logger.info("playerId {} 背包未插入数据库失败",backBag.getPlayerId());
        }
        return i;
    }

    public void insertAsync(BackBag backBag){
        submit(()->{
            insert(backBag);
        });
    }

    public void update(BackBag backBag,boolean async){
        // 将道具列表转化为JSON
        String items = JSON.toJSONString(backBag.getItemMap());
        // 创建实体对象
        BackBagEntity backBagEntity = new BackBagEntity(backBag.getPlayerId(),backBag.getCapacity(),items);
        int i = backBagMapper.update(backBagEntity);
        if(i==0){
            logger.info("playerId {} 背包未插入数据库失败",backBag.getPlayerId());
        }
    }

    public void delete(BackBag backBag ,boolean async){

    }

}
