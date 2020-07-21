package com.game.gameserver.module.instance.model;

import com.game.gameserver.common.config.InstanceCheckPointConfig;
import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.module.instance.type.InstanceEnum;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.npc.model.Npc;
import com.game.gameserver.module.pet.model.Pet;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.scene.SceneType;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.net.modelhandler.instance.InstanceCmd;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/17 15:49
 */
@Data
public class Instance implements Scene {
    private final static Logger logger = LoggerFactory.getLogger(Instance.class);
    /** 下一关准备时间 */
    public final static long NEXT_CHECKPOINT_TIME = 10000;
    /** 副本成功/失败 后的 停留时间 */
    public final static long BEFORE_FINISH = 60000;

    /** 副本Id */
    private Long id;
    /** 副本配置 */
    private InstanceConfig instanceConfig;
    /** 副本资源配置 */
    private InstanceCheckPointConfig instanceCheckpointConfig;
    /** 副本当前关卡数 */
    private int currRound;
    /** 当前关卡 */
    private CheckPoint checkPoint;
    /** 副本创建时间 */
    private long creatTime;
    /** 副本失败时间 */
    private long failedTime;
    /** 副本成功/失败 结束时间 */
    private long endTime;
    /** 副本状态 */
    private InstanceEnum state;
    /** 是否已经发放奖励 */
    private boolean reward;
    /** 关卡通关时间 */
    private long checkpointTime;

    @Override
    public Map<Long, Player> getPlayerMap() {
        return checkPoint.getPlayerMap();
    }

    @Override
    public Map<Long, Monster> getMonsterMap() {
        return checkPoint.getMonsterMap();
    }

    @Override
    public Map<Long, Npc> getNpcMap() {
        return checkPoint.getNpcMap();
    }

    @Override
    public Map<Long, Pet> getPetMap() {
        return checkPoint.getPetMap();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.INSTANCE;
    }

    public int getRoundAmount(){
        return getInstanceCheckpointConfig().getRoundAmount();
    }

    public String getName(){
        return instanceConfig.getName();
    }

    public String getDesc(){
        return instanceConfig.getDesc();
    }
}
