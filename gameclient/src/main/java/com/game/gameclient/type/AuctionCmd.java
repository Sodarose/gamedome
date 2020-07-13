package com.game.gameserver.net.modelhandler.auction;

/**
 * @author xuewenkang
 * @date 2020/7/13 6:09
 */
public interface AuctionCmd {
    int SHOW_AUCTION_HOUSE = 1001;
    int SHOW_ME_AUCTION = 1002;
    int PUSH_AUCTION = 1003;
    int TAKE_AUCTION = 1004;
    int AUCTION = 1005;
    int FIXED_PRICE = 1006;
}
