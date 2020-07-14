package com.game.gameserver.module.guild.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.game.gameserver.module.guild.dao.GuildDbService;
import com.game.gameserver.module.guild.dao.GuildWarehouseDbService;
import com.game.gameserver.module.guild.entity.GuildEntity;
import com.game.gameserver.module.guild.entity.GuildWarehouseEntity;
import com.game.gameserver.module.guild.helper.GuildHelper;
import com.game.gameserver.module.guild.model.Applicant;
import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.module.guild.model.GuildWarehouse;
import com.game.gameserver.module.guild.model.Member;
import com.game.gameserver.module.item.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
    private final static Map<Long, Guild> LOCAL_GUILD_DOMAIN_MAP = new ConcurrentHashMap<>();


    @Autowired
    private GuildDbService guildDbService;
    @Autowired
    private GuildWarehouseDbService guildWarehouseDbService;

    public void loadGuild(){
        logger.info("加载公会");
        // 获得公会列表
        List<GuildEntity> guildEntities = guildDbService.selectGuildEntityList();
        // 处理数据 转换为业务模型对象
        guildEntities.forEach(guildEntity -> {
            // 转化成业务模型对象
            Guild guild = GuildHelper.transFromGuild(guildEntity);
            // 获取公会数据库
            GuildWarehouseEntity guildWarehouseEntity = guildWarehouseDbService.select(guildEntity.getId());
            // 转换成业务模型对象
            GuildWarehouse guildWarehouse = GuildHelper.transFromGuildWarehouse(guildWarehouseEntity);
            guild.setGuildWarehouse(guildWarehouse);
            // 放入缓存
            LOCAL_GUILD_DOMAIN_MAP.put(guild.getId(),guild);
        });
    }

    public Guild getGuild(long guildId){
        return LOCAL_GUILD_DOMAIN_MAP.get(guildId);
    }

    public void putGuild(long guildId, Guild guildDomain){
        LOCAL_GUILD_DOMAIN_MAP.put(guildId,guildDomain);
    }

    public List<Guild> getAllGuild(){
        return new ArrayList<>(LOCAL_GUILD_DOMAIN_MAP.values());
    }

    public void remove(long guildId){
        LOCAL_GUILD_DOMAIN_MAP.remove(guildId);
    }
}
