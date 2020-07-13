package com.game.gameserver.module.guild.domain;

import com.alibaba.fastjson.JSONObject;
import com.game.gameserver.module.guild.entity.Apply;
import com.game.gameserver.module.guild.entity.Guild;
import com.game.gameserver.module.guild.entity.GuildWarehouse;
import com.game.gameserver.module.guild.entity.Member;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xuewenkang
 * @date 2020/7/8 9:45
 */
@Data
public class GuildDomain {
    /** 公会基本信息 */
    private Guild guild;
    /** 公会成员*/
    private Map<String, Member> memberMap;
    /** 公会仓库 */
    private GuildWarehouse guildWarehouse;
    /** 公会申请人 */
    private Map<String,Apply> applyMap;
    /** 读写锁 */
    private final ReentrantReadWriteLock lock;

    public GuildDomain(Guild guild){
        JSONObject jsonObject = null;
        this.guild = guild;
        this.memberMap = new ConcurrentHashMap<>();
        this.applyMap = new ConcurrentHashMap<>();
        this.guildWarehouse = new GuildWarehouse();
        this.lock = new ReentrantReadWriteLock();
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }

    public Lock getReadLock(){
        return lock.readLock();
    }

    @Override
    public String toString(){
        return "";
    }
}
