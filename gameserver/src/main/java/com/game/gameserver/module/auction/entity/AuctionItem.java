package com.game.gameserver.module.auction.entity;

import com.game.gameserver.util.GameUUID;
import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 拍卖品实体
 *
 * @author xuewenkang
 * @date 2020/7/7 9:48
 */
@Data
public class AuctionItem {
    /** id */
    private Long id;
    /** 物品Id*/
    private Integer itemConfigId;
    /** 物品数量*/
    private Integer num;
    /** 物品价格*/
    private Integer price;
    /** 拍卖模式*/
    private Integer model;
    /** 上架时间*/
    private Long publishTime;
    /** 当前竞拍玩家*/
    private Long bidderId;
    /** 拍卖品所属玩家*/
    private Long playerId;

    /** 读写锁 */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public AuctionItem(){

    }

    public AuctionItem(long id,int model,int itemConfigId,int num,int price,long playerId){
        this.id = id;
        this.itemConfigId = itemConfigId;
        this.num = num;
        this.model = model;
        this.price = price;
        this.playerId = playerId;
    }

    public Lock getReadLock(){
        return lock.readLock();
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }
}
