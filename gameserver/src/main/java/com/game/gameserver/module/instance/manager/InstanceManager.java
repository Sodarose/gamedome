package com.game.gameserver.module.instance.manager;

import com.game.gameserver.common.config.CheckPointConfig;
import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.event.InstanceEvent;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.email.service.EmailService;
import com.game.gameserver.module.email.type.SystemSender;
import com.game.gameserver.module.instance.helper.InstanceHelper;
import com.game.gameserver.module.instance.model.CheckPoint;
import com.game.gameserver.module.instance.model.Instance;
import com.game.gameserver.module.instance.type.CheckPointState;
import com.game.gameserver.module.instance.type.InstanceEnum;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.monster.service.MonsterService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerDataService;
import com.game.gameserver.module.scene.service.SceneService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/7/17 15:27
 */
@Component
public class InstanceManager {

    public final static Logger logger = LoggerFactory.getLogger(InstanceManager.class);

    /**
     * 本地副本缓存
     */
    private final static Map<Long, Instance> LOCAL_INSTANCE_MAP = new ConcurrentHashMap<>();

    /**
     * 待移除副本
     */
    private final static Map<Long, Instance> REMOVE_INSTANCE_MAP = new HashMap<>();

    /**
     * 副本Tick线程
     */
    private final static ThreadFactory INSTANCE_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("Instance-Tick-%d").setUncaughtExceptionHandler((t, e) -> e.printStackTrace()).build();

    /**
     * 场景TICK线程
     */
    private final static ScheduledThreadPoolExecutor INSTANCE_TICK_THREAD = new ScheduledThreadPoolExecutor(1
            , INSTANCE_THREAD_FACTORY);


    @Autowired
    private SceneService sceneService;

    @Autowired
    private BackBagService backBagService;

    @Autowired
    private MonsterService monsterService;

    @Autowired
    private PlayerDataService playerDataService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EmailService emailService;

    public void initialize() {
        logger.info("启动副本Tick线程");
        startTick();
    }


    private void startTick() {
        // 启动Tick
        INSTANCE_TICK_THREAD.scheduleAtFixedRate(this::tick,
                500, 1000, TimeUnit.MILLISECONDS);
    }

    private void tick() {
        // 先移除已经过期的副本
        REMOVE_INSTANCE_MAP.forEach((key, value) -> {
            LOCAL_INSTANCE_MAP.remove(key);
        });
        REMOVE_INSTANCE_MAP.clear();

        // 遍历副本 执行副本规则函数
        LOCAL_INSTANCE_MAP.forEach((key, value) -> {
            // 执行副本规则
            try {
                runInstanceRule(value);
                update(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 更新副本内活动生物的状态
     *
     * @param instance
     * @return void
     */
    private void update(Instance instance) {
        try {
            instance.getPlayerMap().forEach((key, value) -> {
                value.update();
            });

            instance.getMonsterMap().forEach((key, value) -> {
                value.update();
            });

            instance.getPetMap().forEach((key, value) -> {
                value.update();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 运行副本规则
     *
     * @param instance
     * @return void
     */
    private void runInstanceRule(Instance instance) {
        if (instance == null) {
            return;
        }
        // 副本内人员为空 直接回收
        if (instance.getCheckPoint().getPlayerMap().isEmpty()) {
            REMOVE_INSTANCE_MAP.put(instance.getId(), instance);
            return;
        }

        // 副本已经结束了 回收该副本
        if (instance.getState() == InstanceEnum.RECOVERY) {
            // 查看是否到达最大停留时间
            long currTime = System.currentTimeMillis();
            long recovery = instance.getEndTime() + Instance.BEFORE_FINISH;
            if (currTime < recovery) {
                // 提示信息
                long surplusTime = TimeUnit.SECONDS.convert(recovery - currTime, TimeUnit.MILLISECONDS);
                NotificationHelper.notifyScene(instance, MessageFormat.format("还有{0}秒，将强制回收副本",
                        surplusTime));
                return;
            }
            // 踢出用户
            doRecovery(instance);
            REMOVE_INSTANCE_MAP.put(instance.getId(), instance);
            return;
        }
        // 通关成功 执行成功函数
        if (instance.getState() == InstanceEnum.SUCCESS) {
            success(instance);
            return;
        }

        // 通关失败 执行失败函数
        if (instance.getState() == InstanceEnum.FAILED) {
            failed(instance);
            return;
        }

        // 检查副本是否失败
        long currTime = System.currentTimeMillis();
        long failedTime = instance.getFailedTime();
        if (currTime > failedTime) {
            instance.setState(InstanceEnum.FAILED);
            return;
        }

        // 副本当前关卡是否成功
        if (instance.getCheckPoint().getState() == CheckPointState.SUCCESS) {
            // 关卡总数
            int roundAmount = instance.getRoundAmount();
            // 当前关卡
            int currRound = instance.getCurrRound();
            // 副本通关成功
            if (currRound == roundAmount) {
                instance.setState(InstanceEnum.SUCCESS);
            } else {
                // 是否已经过了关卡前的准备时间
                long nextCheckPointTime = instance.getCheckpointTime() + Instance.NEXT_CHECKPOINT_TIME;
                if (currTime < nextCheckPointTime) {
                    long time = TimeUnit.SECONDS.convert(
                            nextCheckPointTime - currTime, TimeUnit.MILLISECONDS);
                    NotificationHelper.notifyScene(instance, MessageFormat
                            .format("进入下一关时间 {0}", time));
                    return;
                }
                // 创建一个新的关卡
                CheckPoint checkPoint = createNextCheckPoint(instance);
                // 移动玩家
                checkPoint.getPlayerMap().putAll(instance.getCheckPoint().getPlayerMap());
                instance.setCheckPoint(checkPoint);
            }
        }
        if (checkPointIsSuccess(instance.getCheckPoint())) {
            // 设置关卡通关时间
            instance.setCurrRound(instance.getCurrRound() + 1);
            instance.setCheckpointTime(System.currentTimeMillis());
            instance.getCheckPoint().setState(CheckPointState.SUCCESS);
        }
        // 同步客户端数据
        NotificationHelper.syncInstance(instance);
    }


    /**
     * 是否通关当前关卡 关卡所有怪物是否死亡
     *
     * @param checkPoint
     * @return boolean
     */
    private boolean checkPointIsSuccess(CheckPoint checkPoint) {
        for (Map.Entry<Long, Monster> entry : checkPoint.getMonsterMap().entrySet()) {
            if (!entry.getValue().isDead()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 创建一个下一个关卡
     *
     * @param instance
     * @return com.game.gameserver.module.instance.model.CheckPoint
     */
    private CheckPoint createNextCheckPoint(Instance instance) {
        // 当前关卡
        int currRound = instance.getCurrRound();
        // 获得关卡配置数据
        CheckPointConfig checkPointConfig = instance.getInstanceCheckpointConfig()
                .getCheckPointConfigList().get(currRound);
        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setCheckPointConfig(checkPointConfig);
        checkPoint.setRound(checkPointConfig.getRound());
        checkPoint.setCondition(checkPoint.getCondition());
        checkPoint.setState(CheckPointState.RUNNING);
        // 生成怪物放入该关卡
        List<Integer> monsterList = checkPointConfig.getMonsters();
        monsterList.forEach(monsterId -> {
            Monster monster = monsterService.createMonster(monsterId);
            monster.setCurrScene(instance);
            checkPoint.getMonsterMap().put(monster.getId(), monster);
        });
        return checkPoint;
    }

    /**
     * 发放奖励
     *
     * @param instance
     * @return void
     */
    private void sendReward(Instance instance) {
        InstanceConfig instanceConfig = instance.getInstanceConfig();
        // 获得副本内成员
        instance.getPlayerMap().forEach((key, value) -> {
            // 发放经验
            playerDataService.addExpr(value, instanceConfig.getExprAward());
            // 发放金币
            value.goldsChange(instanceConfig.getGoldAward());
            // 发放道具
            List<Item> items = new ArrayList<>();
            instanceConfig.getItemAward().forEach(award -> {
                        Item item = itemService.createItem(award.getItemId(), award.getNum());
                        items.add(item);
                    }
            );
            // 判断背包容量是否足够
            if (backBagService.hasSpace(value, items)) {
                items.forEach(item -> {
                    backBagService.addItem(value, item);
                });
                NotificationHelper.syncBackBag(value);
            } else {
                // 发到邮件去
                emailService.sendEmail(SystemSender.SYSTEM.getId(), value.getId(), "管理员:\t副本奖励",
                        "背包空间不足", 0, items);
            }
            NotificationHelper.notifyPlayer(value, InstanceHelper.buildInstanceAwardMsg(instanceConfig));
            NotificationHelper.syncPlayer(value);
        });
    }


    private void failed(Instance instance) {
        NotificationHelper.notifyScene(instance, "通关失败");
        // 结束时间
        instance.setEndTime(System.currentTimeMillis());
        instance.setState(InstanceEnum.RECOVERY);
    }

    private void success(Instance instance) {
        NotificationHelper.notifyScene(instance, "通关成功");
        // 结束时间
        instance.setEndTime(System.currentTimeMillis());
        // 发放奖励
        sendReward(instance);
        instance.setState(InstanceEnum.RECOVERY);
        instance.getPlayerMap().values().forEach(player -> {
            EventBus.EVENT_BUS.fire(new InstanceEvent(player, instance.getInstanceConfig().getId()));
        });
    }

    /**
     * 踢出所有玩家
     *
     * @param instance
     * @return void
     */
    private void doRecovery(Instance instance) {
        if (instance.getPlayerMap().isEmpty()) {
            return;
        }
        List<Player> players = new ArrayList<>(instance.getPlayerMap().values());
        // 将玩家放入原场景
        players.forEach(player -> {
            sceneService.initPlayerEntryScene(player);
        });
    }

    public void putInstance(long instanceId, Instance instance) {
        LOCAL_INSTANCE_MAP.put(instanceId, instance);
    }

    public void removeInstance(long instanceId) {
        LOCAL_INSTANCE_MAP.remove(instanceId);
    }

    public Instance getInstance(long instanceId) {
        return LOCAL_INSTANCE_MAP.get(instanceId);
    }
}
