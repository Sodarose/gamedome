package com.game.gameserver.module.instance.manager;

import com.game.gameserver.common.DefaultThreadFactory;
import com.game.gameserver.common.config.*;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.instance.model.InstanceObject;
import com.game.gameserver.module.instance.model.InstanceRule;
import com.game.gameserver.module.monster.event.MonsterDeadEvent;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.team.manager.TeamManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 副本管理器
 *
 * @author xuewenkang
 * @date 2020/6/8 18:29
 */
@Component
@Listener
public class InstanceManager {
    private final static Logger logger = LoggerFactory.getLogger(InstanceManager.class);

    @Autowired
    private MonsterManager monsterManager;
    @Autowired
    private TeamManager teamManager;

    private ScheduledExecutorService scheduledExecutorService;
    private Worker worker;


    /** 副本对象*/
    private Map<Long, InstanceObject> instanceObjectMap = new ConcurrentHashMap<>(1);
    /** 待移除副本 */
    private Map<Long, InstanceObject> removeMap = new ConcurrentHashMap<>(1);

    public static InstanceManager instance;

    public InstanceManager() {
        instance = this;
    }


    public void startInstanceWorker() {
        if (scheduledExecutorService != null) {
            return;
        }
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new
                DefaultThreadFactory("副本Worker"));
        Worker worker = new Worker();
        scheduledExecutorService.scheduleAtFixedRate(worker, 0, 200, TimeUnit.MILLISECONDS);
    }

    public void put(InstanceObject instanceObject){
        instanceObjectMap.put(instanceObject.getId(),instanceObject);
    }

    public void addRemoveInstance(InstanceObject instanceObject){
        removeMap.put(instanceObject.getId(),instanceObject);
    }

    public Map<Long, InstanceObject> getInstanceMap() {
        return instanceObjectMap;
    }

    /**
     * 处理怪物死亡事件
     *
     * @param monsterDeadEvent
     * @return void
     */
    @EventHandler(type = EventType.MONSTER_DEAD)
    public void handleMonsterDead(MonsterDeadEvent monsterDeadEvent) {
        MonsterObject monsterObject = monsterManager.getMonster(monsterDeadEvent.getMonsterId());
        if (monsterObject.getAddrId() == null) {
            return;
        }
        InstanceObject instanceObject = instanceObjectMap.get(monsterObject.getAddrId());
        if (instanceObject == null) {
            return;
        }
        // 从副本中移除该怪物
        instanceObject.removeDealMonster(monsterObject.getId());
    }


    private class Worker implements Runnable {
        @Override
        public void run() {
            //logger.info("副本机制运行中");
            for(Map.Entry<Long, InstanceObject> entry : removeMap.entrySet()){
                instanceObjectMap.remove(entry.getKey());
            }
            removeMap.clear();
            for (Map.Entry<Long, InstanceObject> entry : instanceObjectMap.entrySet()) {
                InstanceObject instanceObject = entry.getValue();
                try {
                    InstanceRule.getInstanceRule().action(instanceObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
