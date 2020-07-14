package com.game.gameserver.module.guild.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.guild.entity.GuildEntity;
import com.game.gameserver.module.guild.manager.GuildManager;
import com.game.gameserver.module.guild.model.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/7 10:58
 */
@Repository
public class GuildDbService extends BaseDbService {
    @Autowired
    private GuildMapper guildMapper;

    public List<GuildEntity> selectGuildEntityList(){
        return guildMapper.selectGuildEntityList();
    };

    public int count(String guildName){
        return guildMapper.count(guildName);
    }

    public GuildEntity select(long guildId){
        return guildMapper.select(guildId);
    }

    public int update(GuildEntity guildEntity){
        return guildMapper.update(guildEntity);
    }

    public int insert(GuildEntity guildEntity){
        return guildMapper.insert(guildEntity);
    }

    public int delete(long guildId){
        return guildMapper.delete(guildId);
    }

    public void updateAsync(GuildEntity guildEntity){
        submit(()->{
            int i = guildMapper.update(guildEntity);
        });
    }

    public void insertAsync(GuildEntity guildEntity){
        submit(()->{
            int i = guildMapper.insert(guildEntity);
        });
    }

    public void deleteAsync(long guildId){
        submit(()->{
            int i = guildMapper.delete(guildId);
        });
    }
}
