package com.game.gameserver.module.instance.model;

import com.game.gameserver.common.config.*;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.state.StateMachine;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.npc.manager.NpcManager;
import com.game.gameserver.module.npc.model.NpcObject;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.model.TeamObject;
import com.game.gameserver.util.GenIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 副本模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:29
 */
public class InstanceObject implements Unit {

    private final static Logger logger = LoggerFactory.getLogger(InstanceObject.class);

    /** 副本唯一ID */
    private final int id;
    /** 副本静态数据 */
    private final InstanceConfig instanceConfig;
    /** 副本怪物配置 */
    private final InstanceMonsterConfig instanceMonsterConfig;
    /** 副本npc配置 */
    private final InstanceNpcConfig instanceNpcConfig;
    /** 玩家队伍 */
    private TeamObject teamObject;
    /** 玩家 */
    private final Map<Integer, PlayerObject> playerMap = new ConcurrentHashMap<>(4);
    /** 怪物列表 */
    private final Map<Integer,MonsterObject> monsterMap = new ConcurrentHashMap<>(15);
    /** npc列表 */
    private final Map<Integer, NpcObject> npcMap = new ConcurrentHashMap<>(1);
    /** 副本状态机 */
    private StateMachine<InstanceObject> stateMachine;


    public InstanceObject(InstanceConfig instanceConfig, InstanceMonsterConfig instanceMonsterConfig,
                          InstanceNpcConfig instanceNpcConfig){
        this.id = GenIdUtil.nextId();
        this.instanceConfig = instanceConfig;
        this.instanceMonsterConfig = instanceMonsterConfig;
        this.instanceNpcConfig = instanceNpcConfig;
    }

    public void initialize(){
        logger.info("instance {} initialize",instanceConfig.getName());
        loadInstanceMonsterConfig();
    }

    private void loadInstanceMonsterConfig(){
        if(instanceMonsterConfig==null){
            logger.info("scene {} don't have SceneMonsterConfig ",instanceConfig.getName());
            return;
        }
        for(InstanceMonster sceneMonster:instanceMonsterConfig.getInstanceMonsterList()){
            List<MonsterObject> monsterObjectList = MonsterManager.getInstance()
                    .createMonsterObjectList(sceneMonster.getMonsterId(),
                            sceneMonster.getCount());
            for(MonsterObject monsterObject:monsterObjectList){
                monsterMap.put(monsterObject.getId(),monsterObject);
            }
        }
    }

    private void loadInstanceNpcConfig(){

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
