package com.game.gameserver.module.trade.model;

import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易模型
 *
 * @author xuewenkang
 * @date 2020/7/7 9:40
 */
@Data
public class TradeBoard {
    /** id*/
    private long id;

    /** 发起的玩家 */
    private Player initiator;

    /** 接受交易的玩家 */
    private Player accepter;


    public TradeBoard(Player initiator, Player accepter){
        this.id = GameUUID.getInstance().generate();
        this.initiator = initiator;
        this.accepter = accepter;
        initiator.setCurrTrade(id);
        accepter.setCurrTrade(id);
        tradeBarMap.put(initiator.getPlayerEntity().getId(),new TradeBar());
        tradeBarMap.put(accepter.getPlayerEntity().getId(),new TradeBar());
    }

    /** 双方交易版 */
    public Map<Long,TradeBar> tradeBarMap = new HashMap<>();
}
