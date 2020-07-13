package com.game.gameserver.module.cooltime.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.thread.DefaultThreadFactory;
import com.game.gameserver.module.cooltime.entity.UnitCoolTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author xuewenkang
 * @date 2020/6/3 13:59
 */
@Listener
@Component
public class CoolTimeManager {
    private final static Logger logger = LoggerFactory.getLogger(CoolTimeManager.class);

    public static CoolTimeManager instance;

    public CoolTimeManager() {
        instance = this;
    }

    private final Map<Long, UnitCoolTime> unitCoolTimeMap = new ConcurrentHashMap<>();

    private ScheduledExecutorService scheduledExecutorService;
    private Worker worker;

    public UnitCoolTime getUnitCoolTime(Long unitId) {
        return unitCoolTimeMap.get(unitId);
    }

    /**
     * 创建单位的CD组件
     *
     * @param unitId
     * @return void
     */
    public void createCoolTime(Long unitId){

    }

    public void removeUnitCoolTime(Long unitId) {
        unitCoolTimeMap.remove(unitId);
    }

    public void startCoolTimeWorker() {
        if (scheduledExecutorService != null) {
            return;
        }
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new
                DefaultThreadFactory("CD Worker"));
        worker = new Worker();
        scheduledExecutorService.scheduleAtFixedRate(worker, 0, 1, TimeUnit.SECONDS);
    }

    private class Worker implements Runnable {

        @Override
        public void run() {
            for (Map.Entry<Long, UnitCoolTime> entry : unitCoolTimeMap.entrySet()) {
                UnitCoolTime unitCoolTime = entry.getValue();
                // 清除已经过期的cd实体
                unitCoolTime.cleanOutTimeCoolTime();
            }
        }
    }

}