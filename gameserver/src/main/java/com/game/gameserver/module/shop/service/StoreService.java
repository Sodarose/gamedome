package com.game.gameserver.module.store.service;

import com.game.gameserver.module.store.manager.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商店服务类
 *
 * @author xuewenkang
 * @date 2020/6/10 17:13
 */
@Service
public class StoreService {
    @Autowired
    private StoreManager storeManager;
/*
    *//**
     * 获得商品列表
     *
     * @return java.util.List<com.game.gameserver.module.store.vo.CommodityVo>
     *//*
    public Store.CommodityList getCommodityList() {
        List<Commodity> commodities = storeManager.commodityList();
        return ProtocolFactory.createCommodityList(commodities);
    }

    *//**
     * 用户购买商品
     *
     * @param player 角色
     * @param commodityId  商品Id
     * @param num          购买数量
     * @return com.game.gameserver.common.Result
     *//*
    public Store.BuyCommodityRes bugCommodity(Player player, int commodityId, int num) {
        Commodity commodity = storeManager.getCommodity(commodityId);
        if (num <= 0) {
            return createBuyCommodityRes(1001, "数量错误");
        }
        if (commodity == null) {
            return createBuyCommodityRes(1002, "商品不存在");
        }
        // 获取商品配置信息
        CommodityConfig commodityConfig = StaticConfigManager.getInstance().getCommodityConfigMap()
                .get(commodity.getCommodityId());
        if (commodityConfig == null) {
            return createBuyCommodityRes(1002, "商品不存在");
        }
        // 计算价格
        int price = commodityConfig.getPrice() * num;
        int playerGolds = player.getGolds();
        if (playerGolds < price) {
            return createBuyCommodityRes(1003, "价钱不足");
        }
        // 判断背包空间是否足够放入物品
      *//*  boolean space = itemManager.hasSpace(playerObject, commodityConfig.getGoodsType()
                , commodityConfig.getGoodsId(), num);*//*
       *//* if (!space) {
            return createBuyCommodityRes(1004, "空间不足");
        }
        boolean result = itemManager.addGoods(playerObject, commodityConfig.getGoodsType()
                , commodityConfig.getGoodsId(), num);*//*
      *//*  if (!result) {
            return createBuyCommodityRes(1004, "购买失败");
        }*//*
        // 扣除金钱
        playerGolds -= price;
        player.setGolds(playerGolds);
        return createBuyCommodityRes(0, "购买成功");
    }

    *//**
     * 出售物品
     *
     * @param player 角色
     * @param goodsId      物品id
     * @param bagType      物品所在的背包类型
     * @param bagIndex     物品所在的背包位置
     * @return com.game.gameserver.common.Result
     *//*
    public Store.SellGoodsRes sellCommodity(Player player, int goodsId, int bagType, int bagIndex) {
        
        return null;
    }

    private Store.BuyCommodityRes createBuyCommodityRes(int code, String msg) {
        Store.BuyCommodityRes.Builder builder = Store.BuyCommodityRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        return builder.build();
    }*/
}
