package com.game.gameserver.module.auction.helper;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.auction.entity.AuctionItem;
import com.game.gameserver.module.auction.type.AuctionModel;

import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/7 10:13
 */
public class AuctionHelper {
    public static String buildAuctionHouse(Map<Long, AuctionItem> map){
        StringBuilder sb = new StringBuilder("拍卖行信息:");
        sb.append("id").append("\t").append("名称").append("\t").append("竞拍模式").append("\t")
                .append("价格").append("\t").append("数量").append("\n");
        map.forEach((key, value) -> {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(value.getItemConfigId());
            sb.append(value.getId()).append("\t")
                    .append(itemConfig.getName()).append("\t")
                    .append(value.getModel()== AuctionModel.AUCTION?"竞拍":"一口价").append("\t")
                    .append(value.getPrice()).append("\t")
                    .append(value.getNum()).append("\t")
                    .append("\n");
        });
        return sb.toString();
    }

    public static String buildAuctionHouse(List<AuctionItem> auctionItems){
        StringBuilder sb = new StringBuilder();
        sb.append("id").append("\t").append("名称").append("\t").append("竞拍模式").append("\t")
                .append("价格").append("\t").append("数量").append("\n");
        auctionItems.forEach(value -> {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(value.getItemConfigId());
            sb.append(value.getId()).append("\t")
                    .append(itemConfig.getName()).append("\t")
                    .append(value.getModel()== AuctionModel.AUCTION?"竞拍":"一口价").append("\t")
                    .append(value.getPrice()).append("\t")
                    .append(value.getNum()).append("\t")
                    .append("\n");
        });
        return sb.toString();
    }
}
