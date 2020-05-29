package com.game.gameserver.module.bag.facade.impl;

import com.game.gameserver.module.bag.dao.BagMapper;
import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.bag.entity.Cell;
import com.game.gameserver.module.bag.facade.BagFacade;
import com.game.gameserver.module.bag.model.BagModel;
import com.game.gameserver.module.bag.model.CellModel;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.equip.facade.EquipFacade;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.facade.ItemFacade;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.util.TransFromUtil;
import com.game.protocol.BagProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/27 9:59
 */
@Service
public class BagFacadeImpl implements BagFacade {

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private ItemFacade itemFacade;
    @Autowired
    private EquipFacade equipFacade;
    @Autowired
    private BagMapper bagMapper;

    /**
     * 根据RoleId 返回该角色的背包
     * @param roleId
     * @param type
     * @return com.game.gameserver.module.bag.entity.Bag
     */
    @Override
    public Bag getBagByRoleId(Integer roleId,Integer type) {
        BagModel bagModel = bagMapper.getBagByRoleId(roleId,type);
        Bag bag = new Bag(bagModel.getId(),bagModel.getBagName(),bagModel.getBagType(),bagModel.getRoleId());
        // 背包中的道具
        List<Item> items = itemFacade.getItemMapByBagId(bag.getId());
        // 背包中的装备
        List<Equip> equips = equipFacade.getEquipMapByBagId(bag.getId());
        bag.init(items,equips);
        return bag;
    }

}
