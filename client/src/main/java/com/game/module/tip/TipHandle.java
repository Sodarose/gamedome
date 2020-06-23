package com.game.module.tip;

import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.TipPage;
import com.game.protocol.Message;
import com.game.protocol.TipProtocol;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/22 14:57
 */
@Component
@ModuleHandler(module = ModuleKey.TIP_MODULE)
public class TipHandle extends BaseHandler {

    @Autowired
    private TipPage tipPage;

    @CmdHandler(cmd = 0)
    public void receiveTipMsg(Message message){
        try {
            TipProtocol.TipMessage tipMessage = TipProtocol.TipMessage.parseFrom(message.getData());
            tipPage.print(tipMessage.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
