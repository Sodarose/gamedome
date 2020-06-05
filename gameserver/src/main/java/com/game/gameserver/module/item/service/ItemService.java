package com.game.gameserver.module.item.service;

import com.game.gameserver.dictionary.StaticDataManager;
import com.game.gameserver.dictionary.entity.EquipData;
import com.game.gameserver.dictionary.entity.ItemData;
import com.game.gameserver.dictionary.entity.MedicamentData;
import com.game.gameserver.module.item.dao.ItemMapper;
import com.game.gameserver.module.item.entity.*;
import com.game.gameserver.module.item.model.ItemModel;
import com.game.gameserver.module.item.model.ItemProperty;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.item.ItemCmd;
import com.game.gameserver.util.TransFromUtil;
import com.game.protocol.ItemProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品Service
 *
 * @author kangkang
 */
@Component
public class ItemService {
    private final static Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemMapper itemMapper;

    public Bag createBagEntity(int playerId) {
        return null;
    }

    public EquipBar createEquipBarEntity(int playerId) {
        return null;
    }

    /**
     * 读取用户背包
     */
    public Bag loadBagEntity(int playerId) {
        List<ItemModel> itemModelList = itemMapper.getItemList(playerId, ItemProperty.BAG);
        Bag bag = new Bag();
        List<Item> items = new ArrayList<>();
        for (ItemModel itemModel : itemModelList) {
            Item item = null;
            ItemData itemData = StaticDataManager.getInstance().getItemDict().get(itemModel.getItemId());
            if (itemData == null) {
                logger.warn("load itemData error by itemId '{}'", itemModel.getItemId());
                continue;
            }
            if (itemModel.getItemType().equals(ItemProperty.EQUIP)) {
                EquipData equipData = StaticDataManager.getInstance().getEquipDict().get(itemData.getAttachId());
                Equip equip = new Equip(itemModel, itemData, equipData);
                equip.init();
                item = equip;
            }
            if (itemModel.getItemType().equals(ItemProperty.MEDICAMENT)) {
                MedicamentData medicamentData = StaticDataManager.getInstance().getMedicamentDict()
                        .get(itemData.getAttachId());
                Medicament medicament = new Medicament(itemModel, itemData, medicamentData);
                medicament.init();
                item = medicament;
            }
            if (item == null) {
                continue;
            }
            items.add(item);
        }
        bag.init(items);
        return bag;
    }

    /**
     * 读取用户装备栏
     */
    public EquipBar loadEquipEntity(int playerId) {
        List<ItemModel> itemModelList = itemMapper.getItemList(playerId, ItemProperty.EQUIP_BAR);
        List<Equip> equips = new ArrayList<>();
        for (ItemModel itemModel : itemModelList) {
            ItemData itemData = StaticDataManager.getInstance().getItemDict().get(itemModel.getItemId());
            if (itemData == null) {
                logger.warn("load itemData error by itemId '{}'", itemModel.getItemId());
                continue;
            }
            EquipData equipData = StaticDataManager.getInstance().getEquipDict().get(itemData.getAttachId());
            Equip equip = new Equip(itemModel, itemData, equipData);
            equips.add(equip);
        }
        EquipBar equipBar = new EquipBar();
        equipBar.init(equips);
        return equipBar;
    }

    /**
     * 打开背包动作 同步背包数据
     *
     * @param playerObject
     * @return void
     */
    public void openBag(PlayerObject playerObject) {
        Bag bagEntity = playerObject.getBag();
        if (bagEntity == null) {
            Message tipMsg = TransFromUtil.createTipMessage(0, "打开背包出错");
            playerObject.getChannel().writeAndFlush(tipMsg);
            return;
        }
        ItemProtocol.Bag bag = TransFromUtil.createBag(bagEntity);
        Message message = MessageUtil.createMessage(ModuleKey.ITEM_MODEL, ItemCmd.OPEN_BAG, bag.toByteArray());
        playerObject.getChannel().writeAndFlush(message);
    }

    /**
     * 打开装备栏
     *
     * @param playerObject
     * @return void
     */
    public void openEquipBar(PlayerObject playerObject) {
        EquipBar equipBar = playerObject.getEquipBar();
        if (equipBar == null) {
            Message tipMsg = TransFromUtil.createTipMessage(0, "打开装备栏出错");
            playerObject.getChannel().writeAndFlush(tipMsg);
            return;
        }
        ItemProtocol.EquipBar equipBarProtocol = TransFromUtil.createEquipBar(equipBar);
        Message message = MessageUtil.createMessage(ModuleKey.ITEM_MODEL, ItemCmd.OPEN_EQUIP_BAR, equipBarProtocol
                .toByteArray());
        playerObject.getChannel().writeAndFlush(message);
    }

    public void takeEquip(){

    }

    /**
     * 同步玩家装备栏数据
     * @param playerObject
     * @return void
     */
    private void syncEquipBar(PlayerObject playerObject){

    }

    private void syncBar(PlayerObject playerObject){

    }
}
