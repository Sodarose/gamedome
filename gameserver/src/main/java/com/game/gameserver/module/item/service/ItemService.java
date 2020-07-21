package com.game.gameserver.module.item.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.backbag.type.BagType;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.buffer.service.BufferService;
import com.game.gameserver.module.cooltime.service.CoolTimeService;
import com.game.gameserver.module.equipment.model.EquipBar;
import com.game.gameserver.module.equipment.service.EquipService;
import com.game.gameserver.module.item.helper.ItemHelper;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author xuewenkang
 * @date 2020/7/12 2:25
 */
@Service
public class ItemService {

    @Autowired
    private BackBagService backBagService;
    @Autowired
    private EquipService equipService;
    @Autowired
    private BufferService bufferService;
    @Autowired
    private CoolTimeService coolTimeService;

    public Item createItem(int itemConfigId, int num) {
        Item item = new Item();
        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(itemConfigId);
        item.setItemConfigId(itemConfigId);
        item.setNum(num);
        // 如果是装备类型 设置耐久读
        if (itemConfig.getType().equals(ItemType.EQUIP.getType())) {
            item.setDurability(itemConfig.getMaxDurability());
        }
        return item;
    }

    public void useItem(Player player, int bagIndex) {
        Item item = backBagService.getItem(player, bagIndex);
        if (item == null) {
            NotificationHelper.notifyPlayer(player, "使用物品失败，该位置不存在道具");
            return;
        }
        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(item.getItemConfigId());
        // 判断道具是否进入了CD
        if (player.getItemCdMap().containsKey(item.getItemConfigId())) {
            NotificationHelper.notifyPlayer(player,
                    MessageFormat.format("道具{0}处于CD中", itemConfig.getName()));
            return;
        }

        if (itemConfig.getType().equals(ItemType.EQUIP.getType())) {
            equipService.putEquip(player, bagIndex);
        }
        if (itemConfig.getType().equals(ItemType.CONSUMABLES.getType())) {
            // 添加buffer
            backBagService.removeItem(player, bagIndex, 1);
            // 扣除道具
            bufferService.addBuffer(player, itemConfig.getBufferId());
        }
        // 判断道具是否有CD 如果有 则进入CD
        if (itemConfig.getCoolTime() != null && itemConfig.getCoolTime() > 0) {
            coolTimeService.itemInCd(player, item);
        }
        NotificationHelper.syncBackBag(player);
    }

    public void showItem(Player player, int bagType, int bagIndex) {
        Item item = null;
        if (bagType == BagType.BACK_BAG) {
            item = player.getBackBag().getItemMap().get(bagIndex);
        }
        if (bagType == BagType.EQUIP_BAR) {
            item = player.getEquipBar().getEquipMap().get(bagIndex);
        }
        if (item == null) {
            NotificationHelper.notifyPlayer(player, "该位置不存在道具");
            return;
        }
        NotificationHelper.notifyPlayer(player, ItemHelper.buildItem(item));
    }
}
