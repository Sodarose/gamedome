package com.game.gameserver.module.instance.service.impl;

import com.game.gameserver.common.Result;
import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.module.instance.event.EntryInstanceEvent;
import com.game.gameserver.module.instance.event.ExitInstanceEvent;
import com.game.gameserver.module.instance.manager.InstanceManager;
import com.game.gameserver.module.instance.model.InstanceObject;
import com.game.gameserver.module.instance.service.InstanceService;
import com.game.gameserver.module.instance.type.InstanceEnum;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.entity.Team;
import com.game.gameserver.module.team.manager.TeamManager;
import com.game.gameserver.module.team.service.TeamService;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.InstanceProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类
 *
 * @author xuewenkang
 * @date 2020/6/10 18:22
 */
@Service
public class InstanceServiceImpl implements InstanceService {

    @Autowired
    private InstanceManager instanceManager;
    @Autowired
    private TeamManager teamManager;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerManager playerManager;

    /**
     * 获取副本信息列表
     *
     * @return com.game.protocol.InstanceProtocol.InstanceInfoListRes
     */
    @Override
    public InstanceProtocol.InstanceInfoListRes getInstanceInfoList() {
        Map<Integer, InstanceConfig> instanceConfigMap = StaticConfigManager.getInstance()
                .getInstanceConfigMap();
        List<InstanceConfig> instanceConfigs = new ArrayList<>();
        for (Map.Entry<Integer, InstanceConfig> entry : instanceConfigMap.entrySet()) {
            instanceConfigs.add(entry.getValue());
        }
        return ProtocolFactory.createInstanceInfoListRes(0, "success", instanceConfigs);
    }

    /**
     * 请求进入副本
     *
     * @param playerObject
     * @param instanceConfigId
     * @param teamWay
     * @return com.game.protocol.InstanceProtocol.EntryInstanceRes
     */
    @Override
    public InstanceProtocol.EntryInstanceRes entryInstanceInfo(PlayerObject playerObject, int instanceConfigId, boolean teamWay) {
        if(playerObject.getInstanceId()!=null){
            return ProtocolFactory.createEntryInstanceRes(1000,"已经在副本内",null);
        }
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceConfigId);
        if (instanceConfig == null) {
            return ProtocolFactory.createEntryInstanceRes(1000,"不存在副本",null);
        }
        // 进入副本的人员
        List<Long> players = new ArrayList<>();
        // 创建副本
        InstanceObject instanceObject = new InstanceObject(instanceConfigId);
        instanceObject.initialize();
        // 如果是组队的方式进入
        if (teamWay) {
            Team team = teamManager.getTeamObject(playerObject.getTeamId());
            if (team == null) {
                return ProtocolFactory.createEntryInstanceRes(1001, "当前玩家并没有队伍", null);
            }
            if (!playerObject.getPlayer().getId().equals(team.getCaptainId())) {
                return ProtocolFactory.createEntryInstanceRes(1002, "你并不是队长", null);
            }
            // 等级限制
            for (Long playerId : team.getMembers()) {
                PlayerObject player = playerManager.getPlayerObject(playerId);
                if (playerObject.getPlayer().getLevel() <instanceConfig.getMinLevel()) {
                    return ProtocolFactory.createEntryInstanceRes(1003, "队伍中有不适合的队员", null);
                }
                player.setInstanceId(instanceObject.getId());
                players.add(playerId);
            }
            team.setInstanceId(instanceObject.getId());
        } else {
            // 判断等级限制
            if(playerObject.getPlayer().getLevel()<instanceConfig.getMinLevel()){
                return ProtocolFactory.createEntryInstanceRes(1004, "等级不足", null);
            }
            playerObject.setInstanceId(instanceObject.getId());
            players.add(playerObject.getPlayer().getId());
        }
        // 进入副本
        instanceObject.entry(players);
        // 存入管理器
        instanceManager.put(instanceObject);
        // 发送进入副本事件
        EntryInstanceEvent event = new EntryInstanceEvent(players);
        EventBus.EVENT_BUS.fire(event);
        return ProtocolFactory.createEntryInstanceRes(0,"进入副本",instanceObject);
    }

    @Override
    public InstanceProtocol.ExitInstanceRes exitInstance(PlayerObject playerObject) {
        if(playerObject.getInstanceId()==null){
            return ProtocolFactory.createExitInstanceRes(1001,"玩家没有进入副本");
        }
        InstanceObject instanceObject = instanceManager.getInstanceMap().get(playerObject.getInstanceId());
        if(instanceObject==null){
            playerObject.setInstanceId(null);
            return ProtocolFactory.createExitInstanceRes(1002,"该副本不存在");
        }
        // 如果是组队进入的 则退出队伍
        if(playerObject.getTeamId()!=null){
            teamService.exitTeam(playerObject);
        }
        instanceObject.exit(playerObject.getPlayer().getId());
        playerObject.setInstanceId(null);
        // 发出退出副本事件
        ExitInstanceEvent event = new ExitInstanceEvent(playerObject.getPlayer().getId());
        EventBus.EVENT_BUS.fire(event);
        return ProtocolFactory.createExitInstanceRes(0,"退出副本成功");
    }


}
