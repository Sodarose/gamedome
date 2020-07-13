package com.game.gameserver.module.trade.helper;

import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.trade.model.TradeBoard;

/**
 * @author xuewenkang
 * @date 2020/7/13 6:58
 */
public class TradeHelper {
    public static String buildTradeBoard(TradeBoard tradeBoard){
        StringBuilder sb  = new StringBuilder();
        sb.append("id:").append(tradeBoard.getId()).append("\n");
        sb.append("交易双方:").append(tradeBoard.getInitiator()).append("\t|\t").append(tradeBoard.getAccepter())
                .append("\n");
        sb.append("双方交易版:").append("\n");
        sb.append("id:").append(tradeBoard.getInitiator()).append("\n");
        sb.append("装备:").append("\n");
        for(Item item : tradeBoard.getTradeBarMap().get(tradeBoard.getInitiator().getPlayerEntity().getId()).getItemList()){
            sb.append(item.getItemConfig().getName()).append(":").append(item.getNum()).append("\n");
        }
        sb.append("金币:").append(tradeBoard.getTradeBarMap().get(tradeBoard.getInitiator().getPlayerEntity().getId()).getGolds()).append("\n");
        sb.append("是否确认:").append(tradeBoard.getTradeBarMap().get(tradeBoard.getInitiator().getPlayerEntity().getId()).isAffirm()).append("\n");
        sb.append("\n");
        sb.append("id:").append(tradeBoard.getAccepter()).append("\n");
        sb.append("装备:").append("\n");
        for(Item item : tradeBoard.getTradeBarMap().get(tradeBoard.getAccepter().getPlayerEntity().getId()).getItemList()){
            sb.append(item.getItemConfig().getName()).append(":").append(item.getNum()).append("\n");
        }
        sb.append("金币:").append(tradeBoard.getTradeBarMap().get(tradeBoard.getAccepter().getPlayerEntity().getId()).getGolds()).append("\n");
        sb.append("是否确认:").append(tradeBoard.getTradeBarMap().get(tradeBoard.getAccepter().getPlayerEntity().getId()).isAffirm()).append("\n");
        return sb.toString();
    }
}
