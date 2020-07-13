package com.game.gameserver.module.guild.manager;

import com.game.gameserver.module.guild.domain.GuildDomain;
import org.apache.poi.sl.draw.geom.Guide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/7/13 15:53
 */
@Component
public class GuildManager {
    private final static Logger logger = LoggerFactory.getLogger(GuildManager.class);
    /**
     * 缓存
     */
    private final static Map<Long, GuildDomain> LOCAL_GUILD_DOMAIN_MAP = new ConcurrentHashMap<>();

    public void loadGuild(){

    }

    public GuildDomain getGuildDomain(long guildId){
        return LOCAL_GUILD_DOMAIN_MAP.get(guildId);
    }

    public void putGuildDomain(long guildId, GuildDomain guildDomain){
        LOCAL_GUILD_DOMAIN_MAP.put(guildId,guildDomain);
    }
}
