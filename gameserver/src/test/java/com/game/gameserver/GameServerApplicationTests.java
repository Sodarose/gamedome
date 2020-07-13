package com.game.gameserver;


import com.game.gameserver.module.backbag.dao.BackBagDbService;
import com.game.gameserver.module.backbag.model.BackBag;
import com.game.gameserver.module.equipment.dao.EquipBarDbService;
import com.game.gameserver.module.equipment.model.EquipBar;
import com.game.gameserver.module.item.model.Item;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class GameServerApplicationTests {
    private final static Logger logger = LoggerFactory.getLogger(GameServerApplicationTests.class);

    @Autowired
    private BackBagDbService backBagDbService;

    @Autowired
    private EquipBarDbService equipBarDbService;
    @Test
    void contextLoads() {

    }

    public void equip(){
        EquipBar equipBar = new EquipBar();
        equipBar.setPlayerId(2);
        equipBarDbService.insert(equipBar);
    }

    public void p(){
        BackBag backBag = new BackBag();
        backBag.setPlayerId(2L);
        backBag.setCapacity(36);
        Item item = new Item();
        item.setBagIndex(0);
        item.setNum(10);
        item.setItemConfigId(1001);
        backBag.getItemMap().put(item.getBagIndex(),item);
        item = new Item();
        item.setBagIndex(1);
        item.setNum(20);
        item.setItemConfigId(1002);
        backBag.getItemMap().put(item.getBagIndex(),item);
        // 装备
        item = new Item();
        item.setBagIndex(2);
        item.setNum(1);
        item.setDurability(45);
        item.setItemConfigId(1003);
        backBag.getItemMap().put(item.getBagIndex(),item);

        item = new Item();
        item.setBagIndex(3);
        item.setNum(1);
        item.setDurability(45);
        item.setItemConfigId(1004);
        backBag.getItemMap().put(item.getBagIndex(),item);


        item = new Item();
        item.setBagIndex(4);
        item.setNum(1);
        item.setDurability(45);
        item.setItemConfigId(1005);
        backBag.getItemMap().put(item.getBagIndex(),item);


        item = new Item();
        item.setBagIndex(5);
        item.setNum(1);
        item.setDurability(45);
        item.setItemConfigId(1006);
        backBag.getItemMap().put(item.getBagIndex(),item);


        item = new Item();
        item.setBagIndex(6);
        item.setNum(1);
        item.setDurability(45);
        item.setItemConfigId(1007);
        backBag.getItemMap().put(item.getBagIndex(),item);

        item = new Item();
        item.setBagIndex(7);
        item.setNum(1);
        item.setDurability(45);
        item.setItemConfigId(1008);
        backBag.getItemMap().put(item.getBagIndex(),item);


        item = new Item();
        item.setBagIndex(8);
        item.setNum(1);
        item.setDurability(45);
        item.setItemConfigId(1009);
        backBag.getItemMap().put(item.getBagIndex(),item);
        backBagDbService.insert(backBag);
    }

}
