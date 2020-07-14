package com.game.gameserver.module.guild.dao;

import com.game.gameserver.module.guild.entity.GuildEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/2 20:57
 */
@Mapper
@Repository
public interface GuildMapper {

    int count(String guildName);

    List<GuildEntity> selectGuildEntityList();

    GuildEntity select(long guildId);

    int update(GuildEntity guildEntity);

    int insert(GuildEntity guildEntity);

    int delete(long id);
}
