package com.game.gameserver.module.goods.service.impl;

import com.game.gameserver.module.goods.dao.EquipMapper;
import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.model.BagType;
import com.game.gameserver.module.goods.model.EquipBar;
import com.game.gameserver.module.goods.model.PlayerBag;
import com.game.gameserver.module.goods.service.EquipService;
import com.game.gameserver.module.player.entity.PlayerBattle;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 装备服务接口实现类
 *
 * @author xuewenkang
 * @date 2020/6/10 15:03
 */
@Service
public class EquipServiceImpl implements EquipService {

    private final static Logger logger = LoggerFactory.getLogger(EquipServiceImpl.class);

    @Autowired
    private EquipMapper equipMapper;
    @Autowired
    private PlayerService playerService;

    /**
     * 根据角色ID 加载角色装备栏
     *
     * @param playerId
     * @return com.game.gameserver.module.goods.model.EquipBar
     */
    @Override
    public EquipBar loadEquipBar(int playerId) {
        EquipBar equipBar = new EquipBar(playerId,6);
        List<Equip> equipList = equipMapper.getEquipEntityList(playerId, BagType.EQUIP_BAR);
        equipBar.initialize(equipList);
        return equipBar;
    }

    /**
     * 穿上装备
     *
     * @param equipId 装备Id
     * @return void
     */
    @Override
    public void putEquip(int playerId, int equipId) {
        PlayerObject playerObject = playerService.getPlayerObject(playerId);
        if (playerObject == null) {
            return;
        }
        // 拿到背包
        PlayerBag playerBag = playerObject.getPlayerBag();
        if (playerBag == null) {
            return;
        }
        // 拿到装备
        Equip equip = playerBag.getEquip(equipId);
        if (equip == null) {
            return;
        }
        // 拿到玩家战斗属性
        PlayerBattle playerBattle = playerObject.getPlayerBattle();
        if (playerBattle == null) {
            return;
        }
        // 拿到装备栏
        EquipBar equipBar = playerObject.getEquipBar();
        if (equipBar == null) {
            return;
        }
        // 背包移除该装备
        playerBag.removeEquip(equip.getId());
        // 穿上装备 放入装备栏
        Equip takeEquip = equipBar.put(equip);
        if (takeEquip != null) {
            // 减少该装备的属性
            playerBattle.removeEquipProperty(takeEquip);
            takeEquip.setBagIndex(equip.getBagIndex());
            // 卸下的装备放入背包
            playerBag.addEquip(equip);
        }
        // 增加穿上的装备的属性
        playerBattle.addEquipProperty(equip);
    }

    /**
     * 脱下装备
     *
     * @param equipId
     * @return void
     */
    @Override
    public void takeEquip(int playerId, int equipId) {
        PlayerObject playerObject = playerService.getPlayerObject(playerId);
        if (playerObject == null) {
            return;
        }
        // 拿到装备栏
        EquipBar equipBar = playerObject.getEquipBar();
        if (equipBar == null) {
            return;
        }
        // 卸下装备
        Equip takeEquip = equipBar.take(equipId);
        if(takeEquip==null){
            return;
        }
        // 拿到玩家战斗属性
        PlayerBattle playerBattle = playerObject.getPlayerBattle();
        if (playerBattle == null) {
            return;
        }
        // 移除该装备属性
        playerBattle.removeEquipProperty(takeEquip);
        // 拿到背包
        PlayerBag playerBag = playerObject.getPlayerBag();
        if (playerBag == null) {
            return;
        }
        // 将装备放入背包
        playerBag.addEquip(takeEquip);
    }

    /**
     * 得到装备栏所有装备实体
     *
     * @param playerId
     * @return java.util.List<com.game.gameserver.module.goods.entity.Equip>
     */
    @Override
    public List<Equip> getEquipList(int playerId) {
        PlayerObject playerObject = playerService.getPlayerObject(playerId);
        if (playerObject == null) {
            return null;
        }
        // 拿到装备栏
        EquipBar equipBar = playerObject.getEquipBar();
        if (equipBar == null) {
            return null;
        }
        return equipBar.getEquipList();
    }


}
