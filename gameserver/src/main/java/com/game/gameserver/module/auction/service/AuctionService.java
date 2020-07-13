package com.game.gameserver.module.auction.service;

import com.game.gameserver.module.auction.dao.AuctionDbService;
import com.game.gameserver.module.auction.entity.AuctionItem;
import com.game.gameserver.module.auction.manager.AuctionHouseManager;
import com.game.gameserver.module.bag.entity.Item;
import com.game.gameserver.module.bag.service.BackBagService;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.util.GameUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/7/10 10:24
 */
@Service
public class AuctionService {

    @Autowired
    private AuctionHouseManager auctionHouseManager;
    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private BackBagService backBagService;
    @Autowired
    private AuctionDbService auctionDbService;

    /**
     * 上架拍卖品
     *
     * @param player 玩家
     * @param bagIndex 物品位置
     * @param num 上架数量
     * @return void
     */
    public void publishItem(Player player, int model, int bagIndex, int num,int price) {
        Item item = backBagService.takeItem(player,bagIndex,num);
        if(item==null){
            return;
        }
        // 生成拍卖品
        AuctionItem auctionItem = new AuctionItem(GameUUID.getInstance().generate(),
                model,
                item.getItemConfigId(),
                num,
                price,
                player.getId()
                );
        // 设置上架时间
        auctionItem.setPublishTime(System.currentTimeMillis());
        // 存入缓存
        auctionHouseManager.putAuctionItem(auctionItem);
        // 保存到数据库
        auctionDbService.insertAuctionItem(auctionItem);
    }

    
}
