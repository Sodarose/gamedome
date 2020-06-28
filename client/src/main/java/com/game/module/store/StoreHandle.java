package com.game.module.store;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.WordPage;
import com.game.protocol.Message;
import com.game.protocol.Store;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.game.util.TransFromUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/17 9:30
 */
@Component
@ModuleHandler(module = ModuleKey.STORE_MODULE)
public class StoreHandle extends BaseHandler {
    private Map<String, Commodity> commodityMap = new HashMap<>();

    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private WordPage wordPage;

    /**
     * 请求商品列表
     *
     * @param
     * @return void
     */
    public void requestCommodityList() {
        Message message = MessageUtil.createMessage(ModuleKey.STORE_MODULE, StoreCmd.LIST, null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = StoreCmd.LIST)
    public void receiveCommodityList(Message message) {
        try {
            Store.CommodityList commodityList = Store.CommodityList.parseFrom(message.getData());
            for (Store.CommodityInfo info : commodityList.getCommodityInfosList()) {
                Commodity commodity = TransFromUtil.transFromCommodity(info);
                commodityMap.put(commodity.getGoodsName(),commodity);
            }
            wordPage.clean();
            wordPage.printStore(commodityMap);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    /**
     * 购买商品
     *
     * @param goodsName 商品名称
     * @param num       商品数量
     * @return void
     */
    public void requestBuyCommodity(String goodsName, int num) {
        Commodity commodity = commodityMap.get(goodsName);
        if(commodity==null){
            wordPage.print("商品不存在,请刷新商店");
            return;
        }
        Store.BuyCommodityReq.Builder builder = Store.BuyCommodityReq.newBuilder();
        builder.setCommodityId(commodity.getId());
        builder.setNum(num);
        Message message = MessageUtil.createMessage(ModuleKey.STORE_MODULE,StoreCmd.BUY,builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = StoreCmd.BUY)
    public void receiveBuyCommodityResult(Message message) {
        try {
            Store.BuyCommodityRes res = Store.BuyCommodityRes.parseFrom(message.getData());
            wordPage.print(res.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 出售商品
     *
     * @param goodsName
     * @param num
     * @return void
     */
    public void requestShellGoods(String goodsName, int num) {

    }


    @CmdHandler(cmd = StoreCmd.SELL)
    public void receiveShellCommodityResult(Message message) {

    }
}
