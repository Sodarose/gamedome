package com.game.gameserver.net.modelhandler.goods;

import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/5/27 14:37
 */
@ModuleHandler(module = ModuleKey.ITEM_Module)
@Component
public class GoodsHandle extends BaseHandler {

}
