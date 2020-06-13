package com.game.gameserver.module.goods.service;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.goods.model.PlayerBag;

/**
 * 道具背包服务类
 *
 * @author xuewenkang
 * @date 2020/6/10 16:41
 */
public interface PropService {
    /**
     * 根据角色Id 加载当前角色道具背包
     *
     * @param playerId
     * @return com.game.gameserver.module.goods.model.PlayerBag
     */
    PlayerBag loadPropsBag(int playerId);


    /**
     * 使用道具
     *
     * @param playerId
     * @param propId
     * @param count
     * @return com.game.gameserver.common.Result
     */
    Result useProp(int playerId,int propId,int count);
}
