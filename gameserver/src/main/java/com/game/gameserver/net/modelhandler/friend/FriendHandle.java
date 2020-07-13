package com.game.gameserver.net.modelhandler.friend;

import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/7/13 17:31
 */
@Component
@ModuleHandler(module = ModuleKey.FIGHTER_MODEL)
public class FriendHandle extends BaseHandler {
}
