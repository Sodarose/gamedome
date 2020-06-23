package com.game.gameserver.module.item.manager;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.game.gameserver.common.config.EquipConfig;
import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.common.config.PropConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.email.entity.Email;
import com.game.gameserver.module.email.manager.EmailManager;
import com.game.gameserver.module.item.dao.EquipMapper;
import com.game.gameserver.module.item.dao.PropMapper;
import com.game.gameserver.module.item.entity.*;
import com.game.gameserver.module.item.type.BagType;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.player.entity.Player;
import com.game.protocol.ItemProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * 物品管理器
 *
 * @author kangkang
 */
@Listener
@Component
public class ItemManager {
    private final static Logger logger = LoggerFactory.getLogger(ItemManager.class);
    public static ItemManager instance;

    private final Map<Long, EquipBag> equipBagMap = new ConcurrentHashMap<>(16);
    private final Map<Long, Bag> playerBagMap = new ConcurrentHashMap<>(16);

    @Autowired
    private EquipMapper equipMapper;
    @Autowired
    private PropMapper propMapper;
    @Autowired
    private EmailManager emailManager;

    public ItemManager() {
        instance = this;
    }

    public void loadPlayerItem(Player player) {
        loadEquipBag(player);
        loadPlayerBag(player);
    }

    /**
     * 读取装备栏
     */
    public void loadEquipBag(Player player) {
        // 创建物品栏
        EquipBag equipBag = new EquipBag(6);
        // 从数据库读取装备栏装备数据
        // equipMapper.getEquipEntityList(player.getId(), BagType.EQUIP_BAG);
        List<Equip> equipList = new ArrayList<>();
        equipBag.initialize(equipList);
        // 放入缓存
        equipBagMap.put(player.getId(), equipBag);
    }

    /**
     * 读取背包
     */
    public void loadPlayerBag(Player player) {
        Bag playerBag = new Bag(player.getBagCapacity(), BagType.PLAYER_BAG);
        // equipMapper.getEquipEntityList(player.getId(), BagType.PLAYER_BAG);
        List<Equip> equipList = new ArrayList<>();
        // propMapper.getPropList(player.getId(), BagType.PLAYER_BAG);
        List<Prop> propList = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        items.addAll(equipList);
        items.addAll(propList);
        // 初始化
        playerBag.initialize(items);
        // 放入缓存
        playerBagMap.put(player.getId(), playerBag);

        // 添加一些测试数据
        EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap().get(1);
        Equip equip = Equip.valueOf(equipConfig);
        equip.setPlayerId(player.getId());
        addEquipInPlayerBag(player.getId(),equip);
        PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(1);
        Prop prop = Prop.valueOf(propConfig);
        prop.setPlayerId(player.getId());
        addPropInPlayerBag(player.getId(),prop);

    }

    /**
     * 添加邮件附件道具
     *
     * @param email
     * @return void
     */
    public void addEmailAttachments(Email email) {
        if(email==null){
            return;
        }
        List<Item> items = email.getAttachments();
        for(Item item:items){
            item.setPlayerId(email.getPlayerId());
            addItemInPlayerBag(email.getPlayerId(),item);
        }
    }

    /** 添加商品 */
    public void addItemByStore(Long playerId,Item item){
        addItemInPlayerBag(playerId,item);
    }

    /**
     * 添加副本奖励
     *
     * @param instanceConfig
     * @return void
     */
    public void addInstanceAward(Long playerId, InstanceConfig instanceConfig) {
        // 生成道具
        List<Integer> equipList = instanceConfig.getEquipAward();
        List<Integer> propList = instanceConfig.getPropAward();
        List<Equip> equipAwards = new ArrayList<>();
        List<Prop> propsAwards = new ArrayList<>();
        for (Integer equipId : equipList) {
            EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap().get(equipId);
            if (equipConfig == null) {
                continue;
            }
            Equip equip = Equip.valueOf(equipConfig);
            equip.setPlayerId(playerId);
            if(!addEquipInPlayerBag(playerId,equip)){
                equipAwards.add(equip);
            }
        }
        for (Integer propsId : propList) {
            PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(propsId);
            if (propConfig == null) {
                continue;
            }
            Prop prop = Prop.valueOf(propConfig);
            prop.setNum(20);
            prop.setPlayerId(playerId);
            if(!addPropInPlayerBag(playerId,prop)){
                propsAwards.add(prop);
            }
        }
        // 空间不够则添加到邮件
        if(!equipAwards.isEmpty()||!propsAwards.isEmpty()){
            Email email = new Email("副本奖励",0L,"系统管理员","背包空间不足" +
                    "，故放入邮件中");
            for(Equip equip:equipAwards){
                email.addAttachments(equip);
            }
            for(Prop prop:propsAwards){
                email.addAttachments(prop);
            }
            // 发送邮件
            emailManager.deliverEmail(playerId,email);
        }
    }


    /**
     * 添加道具到背包
     *
     * @param item
     * @return boolean
     */
    public boolean addItemInPlayerBag(Long playerId, Item item) {
        Bag playerBag = playerBagMap.get(playerId);
        if(playerBag==null){
            return false;
        }
        if(item.getItemType()==ItemType.EQUIP){
            return addEquipInPlayerBag(playerId,item);
        }
        if(item.getItemType()==ItemType.PROP){
            return addPropInPlayerBag(playerId,item);
        }
        return false;
    }

    public boolean addEquipInPlayerBag(Long playerId,Item item){
        Bag playerBag = playerBagMap.get(playerId);
        if(playerBag==null){
            return false;
        }
        Lock lock = playerBag.getWriteLock();
        lock.lock();
        try {
            if(playerBag.getCapacity()<=playerBag.getRawData().length){
                return false;
            }
            Item[] items = playerBag.getRawData();
            for (int i = 0; i < items.length; i++) {
                if (items[i]==null) {
                    items[i] = item;
                    item.setBagIndex(i);
                    item.setBagIndex(BagType.PLAYER_BAG);
                    break;
                }
            }
            return true;
        }finally {
            lock.unlock();
        }
    }

    /** 暂时简单弄下 */
    public boolean addPropInPlayerBag(Long playerId,Item item){
        Bag playerBag = playerBagMap.get(playerId);
        if(playerBag==null){
            return false;
        }
        Lock lock = playerBag.getWriteLock();
        lock.lock();
        try {
            if(playerBag.getCapacity()<=playerBag.getRawData().length){
                return false;
            }
            Item[] items = playerBag.getRawData();
            PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(item.getItemId());
            for (int i = 0; i < items.length; i++) {
                if (items[i]==null) {
                    items[i] = item;
                    break;
                }
            }
            return true;
        }finally {
            lock.unlock();
        }
    }

}
