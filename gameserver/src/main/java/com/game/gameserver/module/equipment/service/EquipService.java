package com.game.gameserver.module.equipment.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.equipment.dao.EquipBarDbService;
import com.game.gameserver.module.equipment.entity.EquipBarEntity;
import com.game.gameserver.module.equipment.helper.EquipHelper;
import com.game.gameserver.module.equipment.model.EquipBar;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/12 4:22
 */
@Service
public class EquipService {
    private final static Logger logger = LoggerFactory.getLogger(EquipService.class);

    @Autowired
    private EquipBarDbService equipBarDbService;
    @Autowired
    private BackBagService backBagService;
    /**
     * 读取用户装备栏
     *
     * @param playerDomain
     * @return void
     */
    public void loadPlayerEquipBar(Player playerDomain){
        logger.info("加载用户装备栏");
        EquipBarEntity equipBarEntity = equipBarDbService.select(playerDomain.getPlayerEntity().getId());
        EquipBar equipBar = new EquipBar(equipBarEntity);
        // 解析Json 获得道具表
        Map<Integer, Item> itemMap = JSON.parseObject(equipBarEntity.getItems(),
                new TypeReference<Map<Integer,Item>>(){});
        // 初始化道具 并放入背包
        itemMap.forEach((key,value)->{
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(value.getItemConfigId());
            value.setItemConfig(itemConfig);
            equipBar.getEquipMap().put(key,value);
        });
        // 放入玩家实体
        playerDomain.setEquipBar(equipBar);
        NotificationHelper.syncPlayerEquipBar(playerDomain);
    }

    /**
     * 展示装备栏信息
     *
     * @param playerDomain
     * @return void
     */
    public void showEquipBar(Player playerDomain){
        EquipBar equipBar = playerDomain.getEquipBar();
        NotificationHelper.notifyPlayer(playerDomain, EquipHelper.buildEquipBar(equipBar));
        NotificationHelper.syncPlayerEquipBar(playerDomain);
    }


    /**
     * 脱下装备
     *
     * @param playerDomain
     * @param part 装备部位
     * @return void
     */
    public void takeEquip(Player playerDomain, int part){
        EquipBar equipBar = playerDomain.getEquipBar();
        Item item = equipBar.getEquipMap().get(part);
        if(item==null){
            NotificationHelper.notifyPlayer(playerDomain,"该部位没有装备");
            return;
        }
        // 将装备放入背包
        boolean result = backBagService.addItem(playerDomain,item);
        if(!result){
            return;
        }
        // 移除该装备
        item = equipBar.getEquipMap().remove(part);
        // 发出装备变更事件

        // 通知
        NotificationHelper.notifyPlayer(playerDomain, MessageFormat.format("装备{0}已经卸下",
                item.getItemConfig().getName()));
        // 同步客户端
        NotificationHelper.syncPlayerEquipBar(playerDomain);
    }

    /**
     * 穿戴装备
     *
     * @param playerDomain
     * @param bagIndex
     * @return void
     */
    public void putEquip(Player playerDomain, int bagIndex){
        EquipBar equipBar = playerDomain.getEquipBar();
        Item equip = backBagService.getItem(playerDomain,bagIndex);
        if(equip==null||!equip.getItemConfig().getType().equals(ItemType.EQUIP.getType())){
            NotificationHelper.notifyPlayer(playerDomain,"该位置没有装备或该位置存放的不是装备");
            return;
        }
        // 背包移除该装备
        equip = backBagService.removeItem(playerDomain,bagIndex);
        equip.setBagIndex(equip.getItemConfig().getPart());
        // 装备栏卸下当前部位的装备
        Item takeEquip = equipBar.getEquipMap().remove(equip.getItemConfig().getPart());
        // 穿上装备
        equipBar.getEquipMap().put(equip.getBagIndex(),equip);
        // 放入背包
        backBagService.addItem(playerDomain,takeEquip);
        // 发出装备变更事件
        // 通知
        NotificationHelper.notifyPlayer(playerDomain, MessageFormat.format("装备{0}已经穿上",
                equip.getItemConfig().getName()));
        // 同步客户端
        NotificationHelper.syncPlayerEquipBar(playerDomain);
    }
}
