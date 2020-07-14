package com.game.gameserver.module.guild.entity;

import com.game.gameserver.module.guild.model.GuildWarehouse;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/13 15:50
 */
@Data
public class GuildWarehouseEntity {
    /** 工会Id */
    private long guildId;
    /** 仓库容量 */
    private int capacity;
    /** 仓库数据 */
    private String items;

    public GuildWarehouseEntity(){

    }

    public GuildWarehouseEntity(GuildWarehouse guildWarehouse){
        this.guildId = guildWarehouse.getGuild();
        this.capacity = guildWarehouse.getCapacity();
        this.items = "";
    }
}
