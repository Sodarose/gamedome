package com.game.gameserver.module.store.service.impl;

import com.game.gameserver.common.config.CommodityConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.goods.entity.Goods;
import com.game.gameserver.module.goods.manager.GoodsManager;
import com.game.gameserver.module.goods.model.PlayerBag;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.store.manager.StoreManager;
import com.game.gameserver.module.store.entity.Commodity;
import com.game.gameserver.module.store.service.StoreService;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.Store;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private StoreManager storeManager;
    @Autowired
    private GoodsManager goodsManager;

    /**
     * 获得商品列表
     *
     * @return java.util.List<com.game.gameserver.module.store.vo.CommodityVo>
     */
    @Override
    public Store.CommodityList getCommodityList() {
        List<Commodity> commodities = storeManager.commodityList();
        return ProtocolFactory.createCommodityList(commodities);
    }

    /**
     * 用户购买商品
     *
     * @param playerObject 角色
     * @param commodityId  商品Id
     * @param num          购买数量
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Store.BuyCommodityRes bugCommodity(PlayerObject playerObject, int commodityId, int num) {
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
        int playerGolds = playerObject.getPlayer().getGolds();
        if (playerGolds < price) {
            return createBuyCommodityRes(1003, "价钱不足");
        }
        // 判断背包空间是否足够放入物品
        boolean space = goodsManager.hasSpace(playerObject, commodityConfig.getGoodsType()
                , commodityConfig.getGoodsId(), num);
        if (!space) {
            return createBuyCommodityRes(1004, "空间不足");
        }
        boolean result = goodsManager.addGoods(playerObject, commodityConfig.getGoodsType()
                , commodityConfig.getGoodsId(), num);
        if (!result) {
            return createBuyCommodityRes(1004, "购买失败");
        }
        // 扣除金钱
        playerGolds -= price;
        playerObject.getPlayer().setGolds(playerGolds);
        return createBuyCommodityRes(0, "购买成功");
    }

    /**
     * 出售物品
     *
     * @param playerObject 角色
     * @param goodsId      物品id
     * @param bagType      物品所在的背包类型
     * @param bagIndex     物品所在的背包位置
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Store.SellGoodsRes sellCommodity(PlayerObject playerObject, int goodsId, int bagType, int bagIndex) {
        
        return null;
    }

    private Store.BuyCommodityRes createBuyCommodityRes(int code, String msg) {
        Store.BuyCommodityRes.Builder builder = Store.BuyCommodityRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        return builder.build();
    }
}
