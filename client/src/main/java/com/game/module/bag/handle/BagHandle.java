package com.game.module.bag.handle;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.bag.BagCmd;
import com.game.module.bag.entity.Bag;
import com.game.module.gui.WordPage;
import com.game.protocol.BagProtocol;
import com.game.protocol.Message;
import com.game.task.MessageDispatcher;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.TransFromUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author xuewenkang
 * @date 2020/5/27 15:32
 */
@Service
@ModuleHandler(module = ModuleKey.BAG_MODEL)
public class BagHandle extends BaseHandler {
    private final static Logger logger = LoggerFactory.getLogger(BagHandle.class);

    @Autowired
    private WordPage wordPage;
    @Autowired
    private MessageDispatcher dispatcher;
    @Autowired
    private ClientGameContext gameContext;

    @CmdHandler(cmd = BagCmd.OPEN_BAG)
    public void receiveOpenRes(Message message){
        try {
            BagProtocol.OpenBagRes openBagRes = BagProtocol.OpenBagRes.parseFrom(message.getData());
            BagProtocol.BagInfo bagInfo = openBagRes.getBagInfo();
            Bag bag = TransFromUtil.bagProtocolBagInfoTransFromBag(bagInfo);
            gameContext.getPlayer().setBag(bag);
            wordPage.print(bag);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

}
