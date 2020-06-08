package com.game.gameserver.module.instance.object;

import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.common.config.InstanceMonsterConfig;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.state.StateMachine;
import com.game.gameserver.module.team.object.TeamObject;

import java.util.Map;

/**
 * 副本
 *
 * @author xuewenkang
 * @date 2020/6/8 16:29
 */
public class InstanceObject implements Unit {

    /** 副本唯一ID */
    private int id;
    /** 副本静态数据 */
    private InstanceConfig instanceConfig;
    /** 副本怪物配置 */
    private InstanceMonsterConfig instanceMonsterConfig;
    /** 玩家队伍 */
    private TeamObject playerTeam;
    /** 怪物列表 */
    private Map<Integer,Integer> monsterMap;
    /** npc列表 */
    private Map<Integer,Integer> npcMap;
    /** 副本状态机 */
    private StateMachine<InstanceObject> stateMachine;

    /**
     * 状态更新
     * */
    @Override
    public void update() {

    }


}
