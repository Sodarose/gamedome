package com.game.module.item;

import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.protocol.ItemProtocol;
import com.game.task.annotation.ModuleHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/1 16:33
 */
@ModuleHandler(module = ModuleKey.FIGHTER_MODEL)
public class ItemHandle extends BaseHandler {
    public Map<Long, ItemProtocol.ItemInfo> playerBag = new HashMap<>();
    public Map<Long, ItemProtocol> equipBag = new HashMap<>();

    public void playerBagReq(){

    }

    public void equipBag(){

    }
}
