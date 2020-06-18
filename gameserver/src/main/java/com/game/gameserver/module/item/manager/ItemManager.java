package com.game.gameserver.module.item.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.module.item.dao.EquipMapper;
import com.game.gameserver.module.item.dao.PropMapper;
import com.game.gameserver.module.item.entity.*;
import com.game.gameserver.module.item.type.BagType;
import com.game.gameserver.module.player.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 物品管理器
 *
 * @author kangkang
 */
@Listener
@Component
public class ItemManager {
    private final static Logger logger = LoggerFactory.getLogger(ItemManager.class);

    private final Map<Long, EquipBag> equipBagMap = new ConcurrentHashMap<>();
    private final Map<Long, Bag> playerBagMap = new ConcurrentHashMap<>();
    private final Map<Long, Bag> playerWareHouse = new ConcurrentHashMap<>();

    @Autowired
    private EquipMapper equipMapper;
    @Autowired
    private PropMapper propMapper;

    public void loadPlayerItem(Player player) {
        loadEquipBag(player);
        loadPlayerItem(player);
        loadPlayerWareHouse(player);
    }

    /** 读取装备栏 */
    public void loadEquipBag(Player player) {
        // 创建物品栏
        EquipBag equipBag = new EquipBag(6);
        // 从数据库读取装备栏装备数据
        List<Equip> equipList = equipMapper.getEquipEntityList(player.getId(), BagType.EQUIP_BAG);
        equipBag.initialize(equipList);
        // 放入缓存
        equipBagMap.put(player.getId(),equipBag);
    }

    /** 读取背包 */
    public void loadPlayerBag(Player player) {
        Bag playerBag = new Bag(player.getBagCapacity(),BagType.PLAYER_BAG);
        List<Equip> equipList = equipMapper.getEquipEntityList(player.getId(),BagType.PLAYER_BAG);
        List<Prop>  propList = propMapper.getPropList(player.getId(),BagType.PLAYER_BAG);
        List<Item> items = new ArrayList<>();
        items.addAll(equipList);
        items.addAll(propList);
        // 初始化
        playerBag.initialize(items);
        // 放入缓存
        playerBagMap.put(player.getId(), playerBag);
    }

    /** 读取仓库 */
    public void loadPlayerWareHouse(Player player){
        Bag playerBag = new Bag(player.getBagCapacity(),BagType.WAREHOUSE);
        List<Equip> equipList = equipMapper.getEquipEntityList(player.getId(),BagType.WAREHOUSE);
        List<Prop>  propList = propMapper.getPropList(player.getId(),BagType.WAREHOUSE);
        List<Item> items = new ArrayList<>();
        items.addAll(equipList);
        items.addAll(propList);
        // 初始化
        playerBag.initialize(items);
        // 放入缓存
        playerWareHouse.put(player.getId(), playerBag);
    }


}
