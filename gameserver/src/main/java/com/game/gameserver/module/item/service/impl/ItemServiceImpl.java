package com.game.gameserver.module.item.service.impl;

import com.game.gameserver.module.item.entity.Bag;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.manager.ItemManager;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.ItemProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author kangkang
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemManager itemManager;

    /**
     * 获取玩家背包数据
     *
     * @param playerObject
     * @return com.game.protocol.ItemProtocol.PlayerBag
     */
    @Override
    public ItemProtocol.PlayerBag getPlayerBag(PlayerObject playerObject) {
        Bag bag = itemManager.getPlayerBag(playerObject.getPlayer().getId());
        List<Item> items = Arrays.stream(bag.getRawData()).filter(
                Objects::nonNull
        ).collect(Collectors.toList());
        return ProtocolFactory.createPlayerBag(items);
    }

    /**
     * 使用道具
     *
     * @param playerObject
     * @param itemId
     * @param bagIndex
     * @param num
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    @Override
    public ItemProtocol.ItemResult useItem(PlayerObject playerObject, Long itemId, int bagIndex, int num) {
        return null;
    }

    /**
     * 丢弃道具
     *
     * @param playerObject
     * @param itemId
     * @param bagIndex
     * @param num
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    @Override
    public ItemProtocol.ItemResult dropItem(PlayerObject playerObject, Long itemId, int bagIndex, int num) {
        return null;
    }

    /**
     * 移动道具
     *
     * @param playerObject
     * @param itemId
     * @param bagIndex
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    @Override
    public ItemProtocol.ItemResult moveItem(PlayerObject playerObject, Long itemId, int bagIndex) {
        return null;
    }

    /**
     * 整理背包
     *
     * @param playerObject
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    @Override
    public ItemProtocol.ItemResult clearBag(PlayerObject playerObject) {
        return null;
    }
}
