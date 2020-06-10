package com.game.gameserver.module.store.service;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.store.vo.CommodityVo;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/10 17:12
 */
public interface StoreService {
    /**
     * 获得商品列表
     *
     * @param
     * @return java.util.List<com.game.gameserver.module.store.vo.CommodityVo>
     */
    List<CommodityVo> getCommodityList();

    /**
     * 用户购买商品
     *
     * @param playerId 角色Id
     * @param commodityId 商品Id
     * @param count 购买数量
     * @return com.game.gameserver.common.Result
     */
    Result bugCommodity(int playerId,int commodityId, int count);


    /**
     * 出售物品
     *
     * @param playerId 角色id
     * @param goodsId 物品id
     * @param bagType 物品所在的背包类型
     * @param bagIndex 物品所在的背包位置
     * @return com.game.gameserver.common.Result
     */
    Result sellCommodity(int playerId,int goodsId,int bagType,int bagIndex);
}
