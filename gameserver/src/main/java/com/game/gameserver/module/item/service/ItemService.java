package com.game.gameserver.module.item.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.backbag.type.BagType;
import com.game.gameserver.module.item.helper.ItemHelper;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/7/12 2:25
 */
@Service
public class ItemService {

    public Item createItem(int itemConfigId,int num){
        Item item = new Item();
        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(itemConfigId);
        item.setItemConfigId(itemConfigId);
        item.setNum(num);
        // 如果是装备类型 设置耐久读
        if(itemConfig.getType().equals(ItemType.EQUIP.getType())){
            item.setDurability(itemConfig.getMaxDurability());
        }
        return item;
    }

    public void useItem(Player player, int bagIndex){

    }

    public void showItem(Player player, int bagType, int bagIndex ){
        Item item = null;
        if(bagType== BagType.BACK_BAG){
            item = player.getBackBag().getItemMap().get(bagIndex);
        }
        if(bagType==BagType.EQUIP_BAR){
            item = player.getEquipBar().getEquipMap().get(bagIndex);
        }
        if(item==null){
            NotificationHelper.notifyPlayer(player, "该位置不存在道具");
            return;
        }
        NotificationHelper.notifyPlayer(player, ItemHelper.buildItem(item));
    }
}
