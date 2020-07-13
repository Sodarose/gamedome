package com.game.gameserver.module.backbag.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.backbag.helper.BackBagHelper;
import com.game.gameserver.module.backbag.model.BackBag;
import com.game.gameserver.module.backbag.type.BagType;
import com.game.gameserver.module.item.dao.ItemDbService;
import com.game.gameserver.module.item.entity.ItemEntity;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.domain.PlayerDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author xuewenkang
 * @date 2020/7/12 4:33
 */
@Service
public class PlayerBackBagService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerBackBagService.class);

    @Autowired
    private ItemDbService itemDbService;

    public void loadPlayerBackBag(PlayerDomain playerDomain){
        // 创建用户背包
        BackBag backBag = new BackBag(playerDomain.getBackBag().getCapacity());
        /// 获取背包中的道具
        List<ItemEntity> itemEntityList = itemDbService.selectItemList(BagType.PLAYER_BACK_BAG,
                playerDomain.getPlayer().getId());
        // 放入背包
        itemEntityList.forEach(itemEntity -> {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap()
                    .get(itemEntity.getItemConfigId());
            Item item = new Item(itemConfig);
            BeanUtils.copyProperties(itemEntity,item);

        });
        playerDomain.setBackBag(backBag);
    }

    /**
     * 展示玩家背包
     *
     * @param playerDomain
     * @return void
     */
    public void showPlayerBackBag(PlayerDomain playerDomain){
        BackBag backBag = playerDomain.getBackBag();
        NotificationHelper.syncPlayerBackBag(playerDomain);
        NotificationHelper.notifyPlayerDomain(playerDomain, BackBagHelper.buildPlayerBackBag(backBag));
    }

    /**
     * 移动道具
     *
     * @param playerDomain
     * @param sourceIndex 原位置
     * @param targetIndex 目标位置
     * @return void
     */
    public void moveItem(PlayerDomain playerDomain,int sourceIndex,int targetIndex){
        BackBag backBag = playerDomain.getBackBag();
        // 验证参数是否正确
        if(!verifyIndex(backBag,sourceIndex)&&verifyIndex(backBag,targetIndex)){
            NotificationHelper.notifyPlayerDomain(playerDomain, "参数位置错误");
            return;
        }
        Lock lock = backBag.getWriteLock();
        lock.lock();
        try {
            // 取出道具
            Item[] items = backBag.getItems();
            Item sourceItem = items[sourceIndex];
            if(sourceItem==null){
                NotificationHelper.notifyPlayerDomain(playerDomain, "该位置没有物品");
                return;
            }
            Item targetItem = items[targetIndex];
            // 交换位置
            items[targetIndex] = sourceItem;
            sourceItem.setBagIndex(targetIndex);
            itemDbService.updateAsync(sourceItem);

            items[sourceIndex] = targetItem;
            if(targetItem!=null){
                targetItem.setBagIndex(sourceIndex);
                itemDbService.updateAsync(targetItem);
            }
        }finally {
            lock.unlock();
        }
        // 同步数据
        NotificationHelper.syncPlayerBackBag(playerDomain);
    }

    /**
     * 丢弃道具
     *
     * @param playerDomain 玩家
     * @param bagIndex 背包位置
     * @param num 数量
     * @return void
     */
    public void discardItem(PlayerDomain playerDomain,int bagIndex,int num){
        BackBag backBag = playerDomain.getBackBag();
        if(!verifyIndex(backBag,bagIndex)){
            NotificationHelper.notifyPlayerDomain(playerDomain, "参数位置错误");
            return;
        }
        Lock lock = backBag.getWriteLock();
        lock.lock();
        try{
            Item[] items = backBag.getItems();
            Item item = items[bagIndex];
            if (item==null||item.getNum()<num) {
                NotificationHelper.notifyPlayerDomain(playerDomain, "道具不存在或超过拥有数量");
                return;
            }
            item.setNum(item.getNum()-num);
            if(item.getNum()==0){
                // 移除道具
                items[bagIndex] = null;
                itemDbService.deleteAsync(item.getId());
            }else{
                itemDbService.updateAsync(item);
            }
            NotificationHelper.notifyPlayerDomain(playerDomain,
                    MessageFormat.format("丢弃道具{0} 数量{1}",
                            item.getItemConfig().getName(),num));
        }finally {
            lock.unlock();
        }
        // 同步数据
        NotificationHelper.syncPlayerBackBag(playerDomain);
    }

    private boolean verifyIndex(BackBag backBag, int index){
        return index< backBag.getCapacity()&&index>=0;
    }

    /**
     * 放入道具
     *
     * @param playerDomain
     * @param item
     * @return boolean
     */
    public boolean putItem(PlayerDomain playerDomain,Item item){
        BackBag backBag = playerDomain.getBackBag();
        
        return false;
    }

    /**
     * 取出道具
     *
     * @param playerDomain
     * @param bagIndex
     * @param num
     * @return java.sql.Time
     */
    public Time takeItem(PlayerDomain playerDomain,int bagIndex,int num){
        return null;
    }
}
