package com.game.gameserver.module.cooltime.manager;

import com.game.gameserver.module.cooltime.entity.CoolTime;
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
@Component
public class CoolTimeManager {
    private final static Logger logger = LoggerFactory.getLogger(CoolTimeManager.class);

    public static CoolTimeManager instance;

    public CoolTimeManager(){
        instance = this;
    }

    private final  Map<Long, UnitCoolTime> playerCoolTimeMap = new ConcurrentHashMap<>();

    public UnitCoolTime getPlayerCoolTime(Long playerId){
        return playerCoolTimeMap.get(playerId);
    }

    public void removePlayerCoolTime(Long playerId){
        playerCoolTimeMap.remove(playerId);
    }
}