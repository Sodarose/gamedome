package com.game.gameserver.module.backbag.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.AddItemEvent;
import com.game.gameserver.event.event.LogoutEvent;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author xuewenkang
 * @date 2020/7/12 4:33
 */
@Listener
@Service
public class BackBagService {
    private static final Logger logger = LoggerFactory.getLogger(BackBagService.class);
    private static final int DEFAULT_CAPACITY = 36;


    @Autowired
    private BackBagDbService backBagDbService;

    public void createBackBag(long playerId) {
        BackBag backBag = new BackBag();
        backBag.setPlayerId(playerId);
        backBag.setCapacity(DEFAULT_CAPACITY);
        // 异步存储
        backBagDbService.insertAsync(backBag);
    }

    /**
     * 读取用户背包
     *
     * @param player
     * @return void
     */
    public void loadPlayerBackBag(Player player) {
        // 从数据库中获取数据
        BackBagEntity backBagEntity = backBagDbService.select(player.getPlayerEntity().getId());
        if (backBagEntity == null) {
            backBagEntity = new BackBagEntity();
            backBagEntity.setPlayerId(player.getId());
            backBagEntity.setCapacity(DEFAULT_CAPACITY);
            backBagDbService.insertAsync(backBagEntity);
        }
        BackBag backBag = new BackBag();
        BeanUtils.copyProperties(backBagEntity, backBag);
        // 放入玩家实体
        player.setBackBag(backBag);
        // 同步数据到客户端
        NotificationHelper.syncBackBag(player);
    }

    public Item getItem(Player player, int bagIndex) {
        BackBag backBag = player.getBackBag();
        if (!verifyIndex(backBag, bagIndex)) {
            NotificationHelper.notifyPlayer(player, "参数位置错误");
            return null;
        }
        return player.getBackBag().getItemMap().get(bagIndex);
    }

    /**
     * 展示玩家背包
     *
     * @param player
     * @return void
     */
    public void showPlayerBackBag(Player player) {
        BackBag backBag = player.getBackBag();
        NotificationHelper.notifyPlayer(player, BackBagHelper.buildPlayerBackBag(backBag));
        // 同步数据到客户端
        NotificationHelper.syncBackBag(player);
    }

    /**
     * 移动道具
     *
     * @param player
     * @param sourceIndex 原位置
     * @param targetIndex 目标位置
     * @return void
     */
    public void moveItem(Player player, int sourceIndex, int targetIndex) {
        BackBag backBag = player.getBackBag();
        if (!verifyIndex(backBag, sourceIndex) && !verifyIndex(backBag, targetIndex)) {
            NotificationHelper.notifyPlayer(player, "参数位置错误");
            return;
        }

        // 取出道具
        Item sourceItem = backBag.getItemMap().remove(sourceIndex);
        if (sourceItem == null) {
            NotificationHelper.notifyPlayer(player, "该位置没有物品");
            return;
        }
        // 取出目标位置的道具
        Item targetItem = backBag.getItemMap().remove(targetIndex);
        // 放入目的位置
        backBag.getItemMap().put(targetIndex, sourceItem);
        sourceItem.setBagIndex(targetIndex);
        // 如果目标位置不为空
        if (targetItem != null) {
            player.getBackBag().getItemMap().put(sourceIndex, targetItem);
            targetItem.setBagIndex(sourceIndex);
        }
        backBagDbService.updateAsync(backBag);
        // 同步数据
        NotificationHelper.notifyPlayer(player, "移动道具到" + targetIndex + "位置");
        NotificationHelper.syncBackBag(player);
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
        backBagDbService.updateAsync(backBag);
        NotificationHelper.syncBackBag(player);
    }

    private boolean verifyIndex(BackBag backBag, int index) {
        return index < backBag.getCapacity() && index >= 0;
    }


    /**
     * 整理背包
     *
     * @param player
     * @return void
     */
    public void clearUpBag(Player player) {
        BackBag backBag = player.getBackBag();

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
        backBagDbService.updateAsync(backBag);
        // 同步
        NotificationHelper.syncBackBag(player);
    }

    public boolean hasSpace(Player player, List<Item> items) {
        BackBag backBag = player.getBackBag();
        // 剩余格子数
        int residue = backBag.getCapacity() - backBag.getItemMap().size();
        if (residue > items.size()) {
            return true;
        }
        // 所需格子数
        int need = 0;
        // 逐个判断
        for (Item item : items) {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap()
                    .get(item.getItemConfigId());

            // 可叠加类型道具
            if (itemConfig.isOverlap()) {
                // 背包不存在同类型道具
                if (!backBag.hasItem(item)) {
                    need++;
                }
            } else {
                // 不可叠加类型道具
                need++;
            }
        }
        return residue >= need;
    }

    public boolean hasSpace(Player player, Item... items) {
        List<Item> itemList = new ArrayList<>(Arrays.asList(items));
        return hasSpace(player, itemList);
    }


    /**
     * 放入道具
     *
     * @param player
     * @param item
     * @return boolean
     */
    public boolean addItem(Player player, Item item) {
        BackBag backBag = player.getBackBag();
        if (item == null) {
            return false;
        }
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
                            NotificationHelper.notifyPlayer(player, "持有物品达到上线");
                            return false;
                        }*/
                    targetItem.addNum(item.getNum());
                    NotificationHelper.notifyPlayer(player, MessageFormat
                            .format("物品{0} x {1}已经放入背包中", itemConfig.getName(), item.getNum()));
                    // 发出增加道具事件
                    AddItemEvent addItemEvent = new AddItemEvent(player, item);
                    EventBus.EVENT_BUS.fire(addItemEvent);
                    return true;
                }
            }
        }
        // 不可堆叠的道具 找到一个空位放入
        if (!backBag.hasScape()) {
            NotificationHelper.notifyPlayer(player, "背包没有空余位置");
            return false;
        }
        for (int i = 0; i < backBag.getCapacity(); i++) {
            if (backBag.getItemMap().get(i) == null) {
                item.setBagIndex(i);
                backBag.getItemMap().put(i, item);
                NotificationHelper.notifyPlayer(player, MessageFormat
                        .format("物品{0}已经放入背包中", itemConfig.getName()));
                // 发出增加道具事件
                AddItemEvent addItemEvent = new AddItemEvent(player, item);
                EventBus.EVENT_BUS.fire(addItemEvent);
                return true;
            }
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
    }

    /**
     * 移除道具
     *
     * @param player
     * @param bagIndex
     * @return com.game.gameserver.module.item.model.Item
     */
    public Item removeItem(Player player, int bagIndex) {
        BackBag backBag = player.getBackBag();
        // 验证位置参数
        if (!verifyIndex(backBag, bagIndex)) {
            NotificationHelper.notifyPlayer(player, "参数位置错误");
            return null;
        }
        Item item = backBag.getItemMap().get(bagIndex);
        if (item == null) {
            NotificationHelper.notifyPlayer(player, "该位置没有道具");
            return null;
        }
        // 移除该道具
        item = backBag.getItemMap().remove(bagIndex);
        // 同步数据
        ItemConfig itemConfig = StaticConfigManager.getInstance()
                .getItemConfigMap().get(item.getItemConfigId());
        NotificationHelper.notifyPlayer(player, MessageFormat.format(
                "从背包中移除{0}", itemConfig.getName()
        ));
        NotificationHelper.syncBackBag(player);
        return item;
    }


    /**
     * 处理玩家登录事件
     *
     * @param logoutEvent
     * @return void
     */
    @EventHandler
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        Player player = logoutEvent.getPlayer();
        if (player != null) {
            BackBag backBag = player.getBackBag();
            backBagDbService.updateAsync(backBag);
        }
    }
}
