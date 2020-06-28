package com.game.gameserver.module.cooltime.entity;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * CD 容器 装在玩家、怪物、宝宝的CD
 *
 * @author xuewenkang
 * @date 2020/6/20 5:40
 */
public class UnitCoolTime {
    /**
     * 冷却容器 key 冷却物品的Id（可以是技能 也可以是物品） value 冷却实体
     */
    private final Map<Integer, CoolTime> coolTimeMap = new ConcurrentHashMap<>(16);
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void putCoolTimeMap(int skillId, CoolTime coolTime) {
        coolTimeMap.put(skillId, coolTime);
    }

    public CoolTime getCoolTime(int cdId) {
        return coolTimeMap.get(cdId);
    }

    public void removeCoolTime(int cdId) {
        coolTimeMap.remove(cdId);
    }

    /**
     * 清理已经超时的cd实体
     *
     * @param
     * @return void
     */
    public void cleanOutTimeCoolTime() {
        long currTime = System.currentTimeMillis();
        Iterator<Map.Entry<Integer, CoolTime>> iterator
                = coolTimeMap.entrySet().iterator();
        while (iterator.hasNext()){
            CoolTime coolTime = iterator.next().getValue();
            if(currTime>=coolTime.getEndTime()){
                iterator.remove();
            }
        }
    }

    public boolean hasSkillCoolTime(int skillId) {
        return coolTimeMap.containsKey(skillId);
    }
}
