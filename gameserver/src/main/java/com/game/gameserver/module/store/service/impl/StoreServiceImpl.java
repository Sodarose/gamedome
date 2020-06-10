package com.game.gameserver.module.store.service.impl;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.store.service.StoreService;
import com.game.gameserver.module.store.vo.CommodityVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商店服务类
 *
 * @author xuewenkang
 * @date 2020/6/10 17:13
 */
@Service
public class StoreServiceImpl implements StoreService {

    /**
     * 获得商品列表
     *
     * @return java.util.List<com.game.gameserver.module.store.vo.CommodityVo>
     */
    @Override
    public List<CommodityVo> getCommodityList() {
        return null;
    }

    /**
     * 用户购买商品
     *
     * @param playerId    角色Id
     * @param commodityId 商品Id
     * @param count       购买数量
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Result bugCommodity(int playerId, int commodityId, int count) {
        return null;
    }

    /**
     * 出售物品
     *
     * @param playerId 角色id
     * @param goodsId  物品id
     * @param bagType  物品所在的背包类型
     * @param bagIndex 物品所在的背包位置
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Result sellCommodity(int playerId, int goodsId, int bagType, int bagIndex) {
        return null;
    }


}
