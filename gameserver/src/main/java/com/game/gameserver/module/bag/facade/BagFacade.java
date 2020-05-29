package com.game.gameserver.module.bag.facade;

import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.bag.entity.Cell;
import com.game.protocol.BagProtocol;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/27 9:59
 */
public interface BagFacade {
    /***
     * 根据角色Id获得角色背包
     * @param roleId 角色ID
     * @param type 背包类型{0:普通背包 1:道具快捷栏}
     * @return com.game.gameserver.module.bag.entity.Bag
     */
    Bag getBagByRoleId(Integer roleId,Integer type);
}