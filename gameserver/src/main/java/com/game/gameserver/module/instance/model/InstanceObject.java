package com.game.gameserver.module.instance.model;

import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.common.config.InstanceMonsterConfig;
import com.game.gameserver.common.config.InstanceNpcConfig;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.state.StateMachine;
import com.game.gameserver.module.team.model.TeamObject;
import com.game.gameserver.util.GenIdUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 副本模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:29
 */
public class InstanceObject implements Unit {

    /** 副本唯一ID */
    private final int id;
    /** 副本静态数据 */
    private final InstanceConfig instanceConfig;
    /** 副本怪物配置 */
    private final InstanceMonsterConfig instanceMonsterConfig;
    /** 副本npc配置 */
    private final InstanceNpcConfig instanceNpcConfig;
    /** 玩家队伍 */
    private TeamObject playerTeam;
    /** 怪物列表 */
    private final Map<Integer,Integer> monsterMap = new ConcurrentHashMap<>(15);
    /** npc列表 */
    private final Map<Integer,Integer> npcMap = new ConcurrentHashMap<>(1);
    /** 副本状态机 */
    private StateMachine<InstanceObject> stateMachine;


    public InstanceObject(InstanceConfig instanceConfig, InstanceMonsterConfig instanceMonsterConfig,
                          InstanceNpcConfig instanceNpcConfig){
        this.id = GenIdUtil.nextId();
        this.instanceConfig = instanceConfig;
        this.instanceMonsterConfig = instanceMonsterConfig;
        this.instanceNpcConfig = instanceNpcConfig;
    }

    /**
     * 状态更新
     * */
    @Override
    public void update() {

    }

    public int getId() {
        return id;
    }
}
