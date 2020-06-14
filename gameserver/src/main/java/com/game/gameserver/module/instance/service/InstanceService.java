package com.game.gameserver.module.instance.service;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.team.model.TeamObject;

/**
 * @author xuewenkang
 * @date 2020/6/10 18:22
 */
public interface InstanceService {

    /**
     * 进入副本
     *
     * @param teamObject
     * @return com.game.gameserver.common.Result
     */
    Result entryInstance(TeamObject teamObject);
}