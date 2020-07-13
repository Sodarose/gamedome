package com.game.gameserver.module.trade.service;

import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.trade.helper.TradeHelper;
import com.game.gameserver.module.trade.manager.TradeManager;
import com.game.gameserver.module.trade.model.TradeBar;
import com.game.gameserver.module.trade.model.TradeBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author xuewenkang
 * @date 2020/7/8 11:14
 */
@Service
public class TradeService {

    @Autowired
    private TradeManager tradeManager;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private BackBagService backBagService;

    public void showTradeBoard(Player playerDomain){
        if(playerDomain.getCurrTrade()==null){
            NotificationHelper.notifyPlayer(playerDomain,"你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(playerDomain.getCurrTrade());
        NotificationHelper.notifyPlayer(playerDomain, TradeHelper.buildTradeBoard(tradeBoard));
    }


    /**
     * 发起交易
     *
     * @param playerDomain
     * @param targetId
     * @return void
     */
    public void initiateTrade(Player playerDomain, long targetId){
        Player target = playerService.getPlayer(targetId);
        if(target==null){
            NotificationHelper.notifyPlayer(playerDomain,"当前玩家不在线");
            return;
        }
        // 创建交易栏
        TradeBoard tradeBoard = new TradeBoard(playerDomain,target);
        tradeManager.putTradeBoard(tradeBoard.getId(),tradeBoard);
        // 发送邀请
        NotificationHelper.notifyPlayer(target, MessageFormat.format("玩家{0}请求交易,Id{1}",
                playerDomain.getPlayerEntity().getName(),tradeBoard.getId()+""));
    }

    /**
     * 交易回复
     *
     * @param playerDomain
     * @param tradeId
     * @param result
     * @return void
     */
    public void replyTrade(Player playerDomain, long tradeId, int result){
        TradeBoard tradeBoard = tradeManager.getTradeBoard(tradeId);
        if(tradeBoard==null){
            return;
        }
        // 接受交易
        if(result==0){
            NotificationHelper.notifyPlayer(tradeBoard.getInitiator(),"玩家接受交易");
        }else{
            // 拒绝交易 移除该交易
            tradeManager.removeTradeBoard(tradeBoard.getId());
            tradeBoard.getAccepter().setCurrTrade(null);
            tradeBoard.getInitiator().setCurrTrade(null);
            NotificationHelper.notifyPlayer(tradeBoard.getInitiator(),"玩家拒绝交易");
        }
    }

    /**
     * 放入道具到交易栏中
     *
     * @param playerDomain
     * @param bagIndex
     * @return void
     */
    public void putItem2Trade(Player playerDomain, int bagIndex){
        if(playerDomain.getCurrTrade()==null){
            NotificationHelper.notifyPlayer(playerDomain,"你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(playerDomain.getCurrTrade());
        TradeBar tradeBar =  tradeBoard.getTradeBarMap().get(playerDomain.getPlayerEntity().getId());
        Item item = backBagService.getItem(playerDomain,bagIndex);
        if(item==null){
            return;
        }
        tradeBar.getItemList().add(item);
        NotificationHelper.notifyPlayer(playerDomain,"放入道具到交易栏中");
    }

    public void putGolds2Trade(Player playerDomain, int price){
        if(playerDomain.getCurrTrade()==null){
            NotificationHelper.notifyPlayer(playerDomain,"你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(playerDomain.getCurrTrade());
        if(price>playerDomain.getPlayerEntity().getGolds()){
            NotificationHelper.notifyPlayer(playerDomain,"金币大于自身持有金币");
            return;
        }
        tradeBoard.getTradeBarMap().get(playerDomain.getPlayerEntity().getId()).setGolds(price);
        NotificationHelper.notifyPlayer(playerDomain,"放入"+price+"金币");
    }

    /**
     * 取消交易
     *
     * @param playerDomain
     * @return void
     */
    public void cancelTrade(Player playerDomain){
        if(playerDomain.getCurrTrade()==null){
            NotificationHelper.notifyPlayer(playerDomain,"你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(playerDomain.getCurrTrade());
        tradeBoard.getInitiator().setCurrTrade(null);
        tradeBoard.getAccepter().setCurrTrade(null);
        NotificationHelper.notifyPlayer(tradeBoard.getInitiator(),"交易被取消");
        NotificationHelper.notifyPlayer(tradeBoard.getAccepter(),"交易被取消");
        // 移除缓存
        tradeManager.removeTradeBoard(tradeBoard.getId());
    }

    /**
     * 确认交易信息
     *
     * @param playerDomain
     * @return void
     */
    public void affirmTrade(Player playerDomain){
        if(playerDomain.getCurrTrade()==null){
            NotificationHelper.notifyPlayer(playerDomain,"你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(playerDomain.getCurrTrade());
        // 确认交易
        tradeBoard.getTradeBarMap().get(playerDomain.getPlayerEntity().getId()).setAffirm(true);
        Player accepter = tradeBoard.getAccepter();
        Player initiator = tradeBoard.getInitiator();
        TradeBar accepterBar = tradeBoard.getTradeBarMap().get(accepter.getPlayerEntity().getId());
        TradeBar initiatorBar = tradeBoard.getTradeBarMap().get(initiator.getPlayerEntity().getId());
        if(accepterBar.isAffirm()&&initiatorBar.isAffirm()){
            NotificationHelper.notifyPlayer(accepter,"正在交易");
            NotificationHelper.notifyPlayer(initiator,"正在交易");
            // 执行交易
            processTrade(tradeBoard);
        }
    }

    /**
     * 处理交易
     *
     * @param tradeBoard
     * @return void
     */
    private void processTrade(TradeBoard tradeBoard){
        Player accepter = tradeBoard.getAccepter();
        Player initiator = tradeBoard.getInitiator();
        TradeBar accepterBar = tradeBoard.getTradeBarMap().get(accepter.getPlayerEntity().getId());
        TradeBar initiatorBar = tradeBoard.getTradeBarMap().get(initiator.getPlayerEntity().getId());
        // 双方交换道具与金币
        // 移除接受方道具和金币
        int accepterGolds = accepter.getPlayerEntity().getGolds()-accepterBar.getGolds();
        accepter.getPlayerEntity().setGolds(accepterGolds);
        // 移除道具
        for(Item item:accepterBar.getItemList()){
            backBagService.removeItem(accepter,item.getBagIndex());
        }

        // 移除发起方道具和金币
        int initiatorGolds = initiator.getPlayerEntity().getGolds()-initiatorBar.getGolds();
        initiator.getPlayerEntity().setGolds(initiatorGolds);
        // 移除道具
        for(Item item:initiatorBar.getItemList()){
            backBagService.removeItem(initiator,item.getBagIndex());
        }

        // 接受方增加道济和金币
        accepter.getPlayerEntity().setGolds(accepter.getPlayerEntity().getGolds()+initiatorBar.getGolds());
        for(Item item:initiatorBar.getItemList()){
            backBagService.addItem(accepter,item);
        }

        // 发起方增加道具和金币
        initiator.getPlayerEntity().setGolds(initiator.getPlayerEntity().getGolds()+accepterBar.getGolds());
        for(Item item:accepterBar.getItemList()){
            backBagService.addItem(initiator,item);
        }

        // 同步
        NotificationHelper.syncPlayer(initiator);
        NotificationHelper.syncPlayer(accepter);
        NotificationHelper.syncPlayerBackBag(initiator);
        NotificationHelper.syncPlayerBackBag(accepter);

        NotificationHelper.notifyPlayer(initiator,"交易成功");
        NotificationHelper.notifyPlayer(accepter,"交易成功");

        tradeBoard.getInitiator().setCurrTrade(null);
        tradeBoard.getAccepter().setCurrTrade(null);
        // 移除缓存
        tradeManager.removeTradeBoard(tradeBoard.getId());

        // 发出交易成功事件
    }
}
