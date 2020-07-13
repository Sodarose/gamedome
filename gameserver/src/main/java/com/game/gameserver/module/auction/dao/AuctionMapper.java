package com.game.gameserver.module.auction.dao;

import com.game.gameserver.module.auction.entity.AuctionItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/7 10:10
 */
@Repository
@Mapper
public interface AuctionMapper {
    List<AuctionItem> selectAuctionItemList();

    AuctionItem select();

    int update(AuctionItem auctionItem);

    int insert(AuctionItem auctionItem);

    int delete(long auctionItemId);
}
