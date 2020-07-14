package com.game.gameserver.module.guild.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.guild.dao.GuildWarehouseDbService;
import com.game.gameserver.module.guild.helper.GuildHelper;
import com.game.gameserver.module.guild.manager.GuildManager;
import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.module.guild.model.GuildWarehouse;
import com.game.gameserver.module.guild.model.Member;
import com.game.gameserver.module.guild.type.GuildPermission;
import com.game.gameserver.module.guild.type.PositionPermissionMapping;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author xuewenkang
 * @date 2020/7/14 11:42
 */
@Service
public class GuildWarehouseService {

    @Autowired
    private GuildManager guildManager;
    @Autowired
    private GuildWarehouseDbService guildWarehouseDbService;
    @Autowired
    private BackBagService backBagService;

    /**
     * 展示工会仓库
     *
     * @param player
     * @return void
     */
    public void showGuildWarehouse(Player player) {
        Guild guild = guildManager.getGuild(player.getGuildId());
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "你没有加入工会或数据不存在");
            return;
        }
        // 验证权限 是否有背包使用权限
        Member member = guild.getMemberMap().get(player.getName());
        List<Integer> perm = PositionPermissionMapping.POSITION_PERMISSION_MAP.get(member.getPosition());
        if (!perm.contains(GuildPermission.USE_WAREHOUSE)) {
            NotificationHelper.notifyPlayer(player, "你没有使用公会仓库的权限");
            return;
        }
        GuildWarehouse guildWarehouse = guild.getGuildWarehouse();
        NotificationHelper.notifyPlayer(player, GuildHelper.buildGuildWarehouse(guildWarehouse));
    }

    /**
     * 放入道具到公会仓库
     *
     * @param player
     * @param bagIndex
     * @return void
     */
    public void putInItem2GuildWarehouse(Player player, int bagIndex) {
        Guild guild = guildManager.getGuild(player.getGuildId());
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "你没有加入工会或数据不存在");
            return;
        }
        // 验证权限 是否有背包使用权限
        Member member = guild.getMemberMap().get(player.getName());
        List<Integer> perm = PositionPermissionMapping.POSITION_PERMISSION_MAP.get(member.getPosition());
        if (!perm.contains(GuildPermission.USE_WAREHOUSE)) {
            NotificationHelper.notifyPlayer(player, "你没有使用公会仓库的权限");
            return;
        }
        GuildWarehouse guildWarehouse = guild.getGuildWarehouse();
        Lock lock = guildWarehouse.getWriteLock();
        lock.lock();
        try {
            // 背包移除道具
            Item item = backBagService.removeItem(player, bagIndex);
            if (item == null) {
                NotificationHelper.notifyPlayer(player, "该位置道具为空");
                return;
            }
            // 仓库空间是否足够
            if (!guildWarehouse.hasSpace(item)) {
                NotificationHelper.notifyPlayer(player, "仓库空间不足");
                return;
            }
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap()
                    .get(item.getItemConfigId());
            // 是否是可叠加类型
            if (itemConfig.isOverlap()) {
                for (Map.Entry<Integer, Item> entry : guildWarehouse.getItemMap().entrySet()) {
                    if (entry.getValue().getItemConfigId().equals(item.getItemConfigId())) {
                        int num = item.getNum() + entry.getValue().getNum();
                        entry.getValue().setNum(num);
                        NotificationHelper.notifyPlayer(player, MessageFormat
                                .format("物品{0} x {1}已经放入背包中", itemConfig.getName(), item.getNum()));
                        // 异步更新数据库
                        guildWarehouseDbService.updateAsync(GuildHelper.transFromGuildWarehouseEntity(guildWarehouse));
                        return;
                    }
                }
            }
            // 找到第一个空的格子
            for (int i = 0; i < guildWarehouse.getCapacity(); i++) {
                if (guildWarehouse.getItemMap().get(i) == null) {
                    guildWarehouse.getItemMap().put(i, item);
                    NotificationHelper.notifyPlayer(player, MessageFormat
                            .format("物品{0} x {1}已经放入背包中", itemConfig.getName(), item.getNum()));
                    break;
                }
            }
            // 异步更新数据库
            guildWarehouseDbService.updateAsync(GuildHelper.transFromGuildWarehouseEntity(guildWarehouse));
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从仓库中拿去道具到背包
     *
     * @param player
     * @param bagIndex
     * @return void
     */
    public void takeOutItem2BackBag(Player player, int bagIndex) {
        Guild guild = guildManager.getGuild(player.getGuildId());
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "你没有加入工会或数据不存在");
            return;
        }
        // 验证权限 是否有背包使用权限
        Member member = guild.getMemberMap().get(player.getName());
        List<Integer> perm = PositionPermissionMapping.POSITION_PERMISSION_MAP.get(member.getPosition());
        if (!perm.contains(GuildPermission.USE_WAREHOUSE)) {
            NotificationHelper.notifyPlayer(player, "你没有使用公会仓库的权限");
            return;
        }
        GuildWarehouse guildWarehouse = guild.getGuildWarehouse();
        Lock lock = guildWarehouse.getWriteLock();
        lock.lock();
        try {
            Item item = guildWarehouse.getItemMap().get(bagIndex);
            if (item == null) {
                NotificationHelper.notifyPlayer(player, "该位置没有道具");
                return;
            }
            // 放入道具到背包
            boolean result = backBagService.addItem(player, item);
            if (!result) {
                NotificationHelper.notifyPlayer(player, "背包空间不足");
                return;
            }
            // 仓库移除道具
            guildWarehouse.getItemMap().remove(bagIndex);

            // 同步数据到客户端
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap()
                    .get(item.getItemConfigId());
            NotificationHelper.notifyPlayer(player, MessageFormat.format("取出道具{0} x {1}",
                    itemConfig.getName(), item.getNum()));
            NotificationHelper.syncBackBag(player);
            // 异步更新数据
            guildWarehouseDbService.updateAsync(GuildHelper.transFromGuildWarehouseEntity(guildWarehouse));
        } finally {
            lock.unlock();
        }
    }

    /**
     * 整理仓库
     *
     * @param player
     * @return void
     */
    public void clearUpWarehouse(Player player) {
        Guild guild = guildManager.getGuild(player.getGuildId());
        if (guild == null) {
            NotificationHelper.notifyPlayer(player, "你没有加入工会或数据不存在");
            return;
        }
        // 验证权限 是否有背包使用权限
        Member member = guild.getMemberMap().get(player.getName());
        List<Integer> perm = PositionPermissionMapping.POSITION_PERMISSION_MAP.get(member.getPosition());
        if (!perm.contains(GuildPermission.USE_WAREHOUSE)) {
            NotificationHelper.notifyPlayer(player, "你没有使用公会仓库的权限");
            return;
        }
        GuildWarehouse guildWarehouse = guild.getGuildWarehouse();
        Lock lock = guildWarehouse.getWriteLock();
        lock.lock();
        try {
            // 消耗品
            List<Item> consumables = new ArrayList<>();
            // 装备
            List<Item> equips = new ArrayList<>();
            // 遍历仓库
            guildWarehouse.getItemMap().forEach(
                    (key, value) -> {
                        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap()
                                .get(value.getItemConfigId());
                        if (itemConfig.getType().equals(ItemType.CONSUMABLES.getType())) {
                            consumables.add(value);
                        }
                        if (itemConfig.getType().equals(ItemType.EQUIP.getType())) {
                            equips.add(value);
                        }
                    }
            );
            List<Item> items = new ArrayList<>();
            items.addAll(consumables);
            items.addAll(equips);
            guildWarehouse.getItemMap().clear();
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                guildWarehouse.getItemMap().put(i, item);
            }
        } finally {
            lock.unlock();
        }
    }
}
