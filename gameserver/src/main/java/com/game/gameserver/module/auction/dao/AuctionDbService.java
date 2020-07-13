package com.game.gameserver.module.auction.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.auction.entity.AuctionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/8 12:43
 */
@Repository
public class AuctionDbService extends BaseDbService {

    @Autowired
    private AuctionMapper auctionMapper;

    public List<AuctionItem> selectAuctionItemList() {
        return auctionMapper.selectAuctionItemList();
    }

    public int update(AuctionItem auctionItem){
        return auctionMapper.update(auctionItem);
    }

    public int insert(AuctionItem auctionItem){
        return auctionMapper.insert(auctionItem);
    }

    public int delete(long auctionItemId){
        return auctionMapper.delete(auctionItemId);
    }

    public void updateAsync(AuctionItem auctionItem) {
        submit(() -> {
            int i = update(auctionItem);
        });
    }

    public void insertAsync(AuctionItem auctionItem) {
        submit(() -> {
            int i = insert(auctionItem);
        });
    }

    public void deleteAsync(long auctionItemId) {
        submit(() -> {
            int i = delete(auctionItemId);
        });
    }

}
