package com.game.gameserver.module.guild.model;

import com.game.gameserver.module.guild.entity.GuildEntity;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 工会业务模型
 *
 * @author xuewenkang
 * @date 2020/7/8 9:45
 */
@Data
public class Guild {

    /** 公会id*/
    private Long id;

    /** 公会名称*/
    private String name;

    /** 公会等级*/
    private Integer level;

    /** 公会经验 */
    private Integer expr;

    /** 成员容量*/
    private Integer capacity;

    /** 仓库金币 */
    private Integer golds;

    /** 公会公告*/
    private String announcement;

    /** 公会成员表 */
    private Map<String,Member> memberMap;

    /** 公会申请入会表 */
    private Map<String, Applicant> applicantMap;

    /** 公会仓库 */
    private GuildWarehouse guildWarehouse;

    /** 读写锁 */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Guild(){

    }

    public Guild(GuildEntity guildEntity){
        this.id = guildEntity.getId();
        this.name = guildEntity.getName();
        this.level = guildEntity.getLevel();
        this.expr = guildEntity.getExpr();
        this.capacity = guildEntity.getCapacity();
        this.golds = guildEntity.getGolds();
        this.announcement = guildEntity.getAnnouncement();
        this.memberMap = new ConcurrentHashMap<>();
        this.applicantMap = new ConcurrentHashMap<>();
    }

    public void addGolds(int value){
        this.golds = value;
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }

    public Lock getReadLock(){
        return lock.readLock();
    }
}
