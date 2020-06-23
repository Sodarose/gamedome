package com.game.gameserver.module.instance.service;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.entity.Team;
import com.game.protocol.InstanceProtocol;

/**
 * @author xuewenkang
 * @date 2020/6/10 18:22
 */
public interface InstanceService {
    /**
     * 获取副本信息列表
     *
     * @param
     * @return com.game.protocol.InstanceProtocol.InstanceInfoListRes
     */
    InstanceProtocol.InstanceInfoListRes getInstanceInfoList();

    /**
     * 请求进入副本
     *
     * @param playerObject
     * @param instanceConfigId
     * @param team
     * @return com.game.protocol.InstanceProtocol.EntryInstanceRes
     */
    InstanceProtocol.EntryInstanceRes entryInstanceInfo(PlayerObject playerObject,int instanceConfigId,boolean team);

    /**
     * 退出副本
     *
     * @param playerObject
     * @return com.game.protocol.InstanceProtocol.ExitInstanceRes
     */
    InstanceProtocol.ExitInstanceRes exitInstance(PlayerObject playerObject);
}
