package com.game.gameserver.module.instance.model;

import com.game.gameserver.common.config.*;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.GameUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * 副本唯一ID
     */
    private final Long id;
    /**
     * 副本静态配置
     */
    private final InstanceConfig instanceConfig;
    /**
     * 副本内玩家
     */
    private final Map<Long, PlayerObject> playerMap = new ConcurrentHashMap<>();
    /**
     * 副本Boss
     */
    private MonsterObject boss;
    /**
     * 创建时间
     */
    private Long creatTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 副本状态(运行/结束/通关失败/通关结束)
     */
    private volatile InstanceEnum state;
    /**
     * 通关最多留存时间
     */
    private Long overTime;

    public InstanceObject(InstanceConfig instanceConfig) {
        this.id = GameUUID.getInstance().generate();
        this.instanceConfig = instanceConfig;
    }

    /**
     * 状态更新
     */
    @Override
    public void update() {

    }

    public Long getId() {
        return id;
    }

    public void entry(PlayerObject... playerObject) {
        synchronized (playerMap) {
            for (PlayerObject player : playerObject) {
                if (playerMap.containsKey(player.getPlayer().getId())) {
                    continue;
                }
                playerMap.put(player.getPlayer().getId(), player);
            }
        }
    }

    public void exit(PlayerObject playerObject) {
        synchronized (playerMap) {
            if (!playerMap.containsKey(playerObject.getPlayer().getId())) {
                return;
            }
            playerMap.remove(playerObject.getPlayer().getId());
        }
    }
}
