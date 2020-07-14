package com.game.gameserver.module.backbag.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.backbag.dao.BackBagDbService;
import com.game.gameserver.module.backbag.entity.BackBagEntity;
import com.game.gameserver.module.backbag.helper.BackBagHelper;
import com.game.gameserver.module.backbag.model.BackBag;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author xuewenkang
 * @date 2020/7/12 4:33
 */
@Service
public class BackBagService {
    private static final Logger logger = LoggerFactory.getLogger(BackBagService.class);

    @Autowired
    private BackBagDbService backBagDbService;

    /**
     * 读取用户背包
     *
     * @param playerDomain
     * @return void
     */
    public void loadPlayerBackBag(Player playerDomain) {
        // 从数据库中获取数据
        BackBagEntity backBagEntity = backBagDbService.select(playerDomain.getPlayerEntity().getId());
        BackBag backBag = new BackBag(backBagEntity);
        // 解析Json 获得道具表
        Map<Integer, Item> itemMap = JSON.parseObject(backBagEntity.getItems(),
                new TypeReference<Map<Integer, Item>>() {
                });
        // 初始化道具 并放入背包
        itemMap.forEach((key, value) -> {
            backBag.getItemMap().put(key, value);
        });
        // 放入玩家实体
        playerDomain.setBackBag(backBag);
        // 同步数据到客户端
        NotificationHelper.syncBackBag(playerDomain);
    }

    public Item getItem(Player playerDomain, int bagIndex) {
        BackBag backBag = playerDomain.getBackBag();
        if (!verifyIndex(backBag, bagIndex)) {
            NotificationHelper.notifyPlayer(playerDomain, "参数位置错误");
            return null;
        }
        return playerDomain.getBackBag().getItemMap().get(bagIndex);
    }

    /**
     * 展示玩家背包
     *
     * @param playerDomain
     * @return void
     */
    public void showPlayerBackBag(Player playerDomain) {
        BackBag backBag = playerDomain.getBackBag();
        NotificationHelper.notifyPlayer(playerDomain, BackBagHelper.buildPlayerBackBag(backBag));
        // 同步数据到客户端
        NotificationHelper.syncBackBag(playerDomain);
    }

    /**
     * 移动道具
     *
     * @param playerDomain
     * @param sourceIndex  原位置
     * @param targetIndex  目标位置
     * @return void
     */
    public void moveItem(Player playerDomain, int sourceIndex, int targetIndex) {
        BackBag backBag = playerDomain.getBackBag();
        if (!verifyIndex(backBag, sourceIndex) && verifyIndex(backBag, targetIndex)) {
            NotificationHelper.notifyPlayer(playerDomain, "参数位置错误");
            return;
        }

        Lock lock = backBag.getWriteLock();
        lock.lock();
        try {
            // 取出道具
            Item sourceItem = backBag.getItemMap().remove(sourceIndex);
            if (sourceItem == null) {
                NotificationHelper.notifyPlayer(playerDomain, "该位置没有物品");
                return;
            }
            // 取出目标位置的道具
            Item targetItem = backBag.getItemMap().remove(targetIndex);
            // 放入目的位置
            backBag.getItemMap().put(targetIndex, sourceItem);
            sourceItem.setBagIndex(targetIndex);
            // 如果目标位置不为空
            if (targetItem != null) {
                playerDomain.getBackBag().getItemMap().put(sourceIndex, targetItem);
                targetItem.setBagIndex(sourceIndex);
            }
        } finally {
            lock.unlock();
        }
        // 同步数据
        NotificationHelper.notifyPlayer(playerDomain, "移动道具到" + targetIndex + "位置");
        NotificationHelper.syncBackBag(playerDomain);
    }

    /**
     * 丢弃道具
     *
     * @param player   玩家
     * @param bagIndex 背包位置
     * @param num      数量
     * @return void
     */
    public void discardItem(Player player, int bagIndex, int num) {
        BackBag backBag = player.getBackBag();
        if (!verifyIndex(backBag, bagIndex)) {
            NotificationHelper.notifyPlayer(player, "参数位置错误");
            return;
        }
        removeItem(player, bagIndex, num);
        NotificationHelper.syncBackBag(player);
    }

    private boolean verifyIndex(BackBag backBag, int index) {
        return index < backBag.getCapacity() && index >= 0;
    }


    /**
     * 整理背包
     *
     * @param playerDomain
     * @return void
     */
    public void clearUpBag(Player playerDomain) {
        BackBag backBag = playerDomain.getBackBag();
        Lock lock = backBag.getWriteLock();
        lock.lock();
        try {
            List<Item> consumablesItems = new ArrayList<>();
            List<Item> equipItems = new ArrayList<>();
            // 遍历道具列表
            backBag.getItemMap().forEach((key, value) -> {
                ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap()
                        .get(value.getItemConfigId());
                // 消耗品
                if (itemConfig.getType().equals(ItemType.CONSUMABLES.getType())) {
                    consumablesItems.add(value);
                }
                // 装备
                if (itemConfig.getType().equals(ItemType.EQUIP.getType())) {
                    equipItems.add(value);
                }
            });
            // 合并列表
            consumablesItems.addAll(equipItems);
            backBag.getItemMap().clear();
            // 按照顺序放入map
            for (int i = 0; i < consumablesItems.size(); i++) {
                Item item = consumablesItems.get(i);
                item.setBagIndex(i);
                backBag.getItemMap().put(i, item);
            }
        } finally {
            lock.unlock();
        }
        // 同步
        NotificationHelper.syncBackBag(playerDomain);
    }

    /**
     * 放入道具
     *
     * @param playerDomain
     * @param item
     * @return boolean
     */
    public boolean addItem(Player playerDomain, Item item) {
        BackBag backBag = playerDomain.getBackBag();
        if (item == null) {
            return false;
        }
        // 上锁
        Lock lock = backBag.getWriteLock();
        lock.lock();
        try {
            // 获取物品本地资源
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(item.getItemConfigId());
            // 可以堆叠的道具
            if (itemConfig.isOverlap()) {
                for (int i = 0; i < backBag.getCapacity(); i++) {
                    Item targetItem = backBag.getItemMap().get(i);
                    // 类型相等
                    if (targetItem != null && targetItem.getItemConfigId().equals(item.getItemConfigId())) {
                        // 判断是否达到叠加上限
           /*             if(targetItem.getNum()+item.getNum()>itemConfig.getOverNum()){
                            NotificationHelper.notifyPlayer(playerDomain, "持有物品达到上线");
                            return false;
                        }*/
                        targetItem.addNum(item.getNum());
                        NotificationHelper.notifyPlayer(playerDomain, MessageFormat
                                .format("物品{0} x {1}已经放入背包中", itemConfig.getName(), item.getNum()));
                        return true;
                    }
                }
            }
            // 不可堆叠的道具 找到一个空位放入
            if (!backBag.hasScape()) {
                NotificationHelper.notifyPlayer(playerDomain, "背包没有空余位置");
                return false;
            }
            for (int i = 0; i < backBag.getCapacity(); i++) {
                if (backBag.getItemMap().get(i) == null) {
                    item.setBagIndex(i);
                    backBag.getItemMap().put(i, item);
                    NotificationHelper.notifyPlayer(playerDomain, MessageFormat
                            .format("物品{0}已经放入背包中", itemConfig.getName()));
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    /**
     * 移除一定数量的道具
     *
     * @param player
     * @param bagIndex
     * @param num      数量
     * @return java.sql.Time
     */
    public Item removeItem(Player player, int bagIndex, int num) {
        BackBag backBag = player.getBackBag();
        // 验证位置参数
        if (!verifyIndex(backBag, bagIndex)) {
            NotificationHelper.notifyPlayer(player, "参数位置错误");
            return null;
        }
        Lock lock = backBag.getWriteLock();
        lock.lock();
        try {
            // 获取该位置参数
            Item item = backBag.getItemMap().get(bagIndex);
            if (item == null) {
                NotificationHelper.notifyPlayer(player, "该位置没有道具");
                return null;
            }
            // 判断数量
            if (item.getNum() < num) {
                NotificationHelper.notifyPlayer(player, "数量大于持有数量");
                return null;
            }
            // 数量相等 直接移除
            if (item.getNum() == num) {
                return backBag.getItemMap().remove(bagIndex);
            }
            // 创建副本
            Item copyItem = new Item();
            BeanUtils.copyProperties(item, copyItem);
            copyItem.setNum(num);
            // 减少持有量
            item.decreaseNum(num);

            // 通知
            ItemConfig itemConfig = StaticConfigManager.getInstance()
                    .getItemConfigMap().get(item.getItemConfigId());
            NotificationHelper.notifyPlayer(player, MessageFormat.format(
                    "从背包中移除{0} x {1}", itemConfig.getName(), num
            ));
            // 返回副本
            return copyItem;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 移除道具
     *
     * @param playerDomain
     * @param bagIndex
     * @return com.game.gameserver.module.item.model.Item
     */
    public Item removeItem(Player playerDomain, int bagIndex) {
        BackBag backBag = playerDomain.getBackBag();
        // 验证位置参数
        if (!verifyIndex(backBag, bagIndex)) {
            NotificationHelper.notifyPlayer(playerDomain, "参数位置错误");
            return null;
        }
        Lock lock = backBag.getWriteLock();
        lock.lock();
        try {
            Item item = backBag.getItemMap().get(bagIndex);
            if (item == null) {
                NotificationHelper.notifyPlayer(playerDomain, "该位置没有道具");
                return null;
            }
            // 移除该道具
            item = backBag.getItemMap().remove(bagIndex);
            // 同步数据
            ItemConfig itemConfig = StaticConfigManager.getInstance()
                    .getItemConfigMap().get(item.getItemConfigId());
            NotificationHelper.notifyPlayer(playerDomain, MessageFormat.format(
                    "从背包中移除{0}", itemConfig.getName()
            ));
            NotificationHelper.syncBackBag(playerDomain);
            return item;
        } finally {
            lock.unlock();
        }
    }


}
