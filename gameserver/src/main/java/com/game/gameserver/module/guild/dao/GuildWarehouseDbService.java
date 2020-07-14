package com.game.gameserver.module.guild.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.guild.entity.GuildWarehouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/14 11:31
 */
@Repository
public class GuildWarehouseDbService extends BaseDbService {
    @Autowired
    private GuildWarehouseMapper guildWarehouseMapper;

    public GuildWarehouseEntity select(long guildId){
        return guildWarehouseMapper.select(guildId);
    }

    public int update(GuildWarehouseEntity guildWarehouseEntity){
        return guildWarehouseMapper.update(guildWarehouseEntity);
    }

    public int insert(GuildWarehouseEntity guildWarehouseEntity){
        return guildWarehouseMapper.insert(guildWarehouseEntity);
    }

    public int delete(long guildId){
        return guildWarehouseMapper.delete(guildId);
    }

    public void updateAsync(GuildWarehouseEntity guildWarehouseEntity){
        submit(()->{
            update(guildWarehouseEntity);
        });
    }

    public void insertAsync(GuildWarehouseEntity guildWarehouseEntity){
        submit(()->{
            insert(guildWarehouseEntity);
        });
    }

    public void deleteAsync(long guildId){
        submit(()->{
            delete(guildId);
        });
    }
}
