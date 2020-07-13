package com.game.gameserver.module.instance.model;

import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.instance.type.InstanceEnum;
import com.game.gameserver.util.GameUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 副本模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:29
 */
public class InstanceObject {
    private final static Logger logger = LoggerFactory.getLogger(InstanceObject.class);
    /**
     * 阶段间隔5s
     */
    public final static Long DURATION = TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS);

    /**
     * 副本唯一ID
     */
    private final Long id;
    /**
     * 副本静态配置
     */
    private final int instanceConfigId;
    /**
     * 副本内玩家
     */
    private final List<Long> currPlayers = new ArrayList<>();
    /**
     * 当前回合的怪物
     */
    private List<Long> currMonsters = new ArrayList<>();
    /**
     * 当前副本内召唤物
     */
    private List<Long> currPets = new ArrayList<>();
    /**
     * 副本当前关卡
     */
    private volatile int currRound;
    /**
     * 副本创建时间
     */
    private Long creatTime;
    /**
     * 到下一个阶段开始时间
     */
    private Long nexRoundTime;
    /**
     * 副本结束时间
     */
    private Long endTime;
    /**
     * 副本回收时间
     */
    private Long recoveryTime;
    /**
     * 副本状态(运行/通关失败/通关成功)
     */
    private volatile InstanceEnum state;
    /**
     * 回收标志
     */
    private volatile boolean recovery = false;

    public InstanceObject(int instanceConfigId) {
        this.id = GameUUID.getInstance().generate();
        this.instanceConfigId = instanceConfigId;
    }

    public void initialize() {
        this.currRound = 0;
        this.state = InstanceEnum.RUNNING;
        this.creatTime = System.currentTimeMillis();
        // 计算结束时间
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceConfigId);
        Long limitTime = TimeUnit.MILLISECONDS.convert(instanceConfig.getLimitTime(), TimeUnit.SECONDS);
        this.endTime = creatTime + limitTime;
        this.recoveryTime = 0L;
        //loadMonster();
    }

    private void loadMonster() {
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceConfigId);
        if (instanceConfig == null) {
            return;
        }
     /*   InstanceMonsterConfig instanceMonsterConfig = StaticConfigManager.getInstance().getInstanceMonsterConfigMap()
                .get(instanceConfig.getInstanceMonsterConfigId());
        if (instanceMonsterConfig == null) {
            return;
        }*/

    }


    public Long getId() {
        return id;
    }

    public void entry(List<Long> playerIds) {
        this.currPlayers.addAll(playerIds);
    }

    public void exit(Long playerId) {
        this.currPlayers.remove(playerId);
    }

    public boolean hasPlayer(Long playerId) {
        return currPlayers.contains(playerId);
    }

    public List<Long> getCurrPlayers() {
        return currPlayers;
    }

    public InstanceEnum getState() {
        return state;
    }

    /**
     * 移除死去的怪物Id
     *
     * @param monsterId
     */
    public void removeDealMonster(Long monsterId) {
        currMonsters.remove(monsterId);
    }

    /**
     * 当前回合的怪物是否已经被清空
     *
     * @param
     * @return boolean
     */
    public boolean isEmptyMonster() {
        return currMonsters.isEmpty();
    }


    public void doFailed() {
        state = InstanceEnum.FAILED;
    }

    public void doSuccess() {
        state = InstanceEnum.SUCCESS;
    }

    public void setRecovery(boolean recovery) {
        this.recovery = recovery;
    }

    public boolean isRecovery() {
        return recovery;
    }

    public void setState(InstanceEnum state) {
        this.state = state;
    }

    public Long getEndTime() {
        return endTime;
    }

    public int getInstanceConfigId() {
        return instanceConfigId;
    }

    public int getCurrRound() {
        return currRound;
    }

    public void addMonsters(List<Long> monsters) {
        currMonsters.addAll(monsters);
    }

    public void addPet(long petId) {
        currPets.add(petId);
    }

    public void remove(long petId) {
        currPets.remove(petId);
    }

    public void setCurrRound(int currRound) {
        this.currRound = currRound;
    }

    public void setNexRoundTime(Long nexRoundTime) {
        this.nexRoundTime = nexRoundTime;
    }

    public void setRecoveryTime(Long recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public Long getRecoveryTime() {
        return recoveryTime;
    }

    public Long getNexRoundTime() {
        return nexRoundTime;
    }

    public List<Long> getCurrMonsters() {
        return currMonsters;
    }

}
