package com.game.gameserver.module.guild.dao;

import com.game.gameserver.module.guild.entity.GuildWarehouseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/13 15:49
 */
@Mapper
@Repository
public interface GuildWarehouseMapper {
    GuildWarehouseEntity select(long guildId);

    int update(GuildWarehouseEntity guildWarehouseEntity);

    int insert(GuildWarehouseEntity guildWarehouseEntity);

    int delete(long guildId);
}
