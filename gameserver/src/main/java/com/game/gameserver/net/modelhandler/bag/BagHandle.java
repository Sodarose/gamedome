package com.game.gameserver.net.modelhandler.bag;

import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import org.springframework.stereotype.Component;

/**
 * 背包Handle
 * @author xuewenkang
 * @date 2020/5/26 21:58
 */
@ModuleHandler(module = ModuleKey.BAG_MODEL)
@Component
public class BagHandle extends BaseHandler {
}
