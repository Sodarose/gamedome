package com.game.gameserver.module.task.manager;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.monster.event.MonsterDeadEvent;
import com.game.gameserver.module.player.event.LoginEvent;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.task.dao.TaskMapper;
import com.game.gameserver.module.task.entity.PlayerTask;
import com.game.gameserver.module.task.entity.TaskProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务管理器
 *
 * @author xuewenkang
 * @date 2020/6/29 16:20
 */
@Listener
@Component
public class TaskManager {
    private final static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    /**
     * 本地缓存
     */
    private volatile static Map<Long, PlayerTask> LOCAL_PLAYER_TASK_MAP = new ConcurrentHashMap<>();

    public Map<Long, PlayerTask> getPlayerTaskMap() {
        return LOCAL_PLAYER_TASK_MAP;
    }

    public PlayerTask getPlayerTask(long playerId) {

        return LOCAL_PLAYER_TASK_MAP.get(playerId);
    }

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private TaskMapper taskMapper;

    private void loadPlayerTask(long playerId) {
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return;
        }
        PlayerTask playerTask = new PlayerTask(playerId);
        LOCAL_PLAYER_TASK_MAP.put(playerId, playerTask);
    }

    @EventHandler(type = EventType.EVENT_TYPE_LOGIN)
    public void handleLoginEvent(LoginEvent event) {
        logger.info("任务模块处理用户登录事件———同步———执行读取{}用户任务数据", event.getPlayerId());
        long playerId = event.getPlayerId();
        // 加载用户任务数据
        loadPlayerTask(playerId);
    }

    @EventHandler(type = EventType.EVENT_TYPE_MONSTER_DEAD)
    public void handleMonsterDeadEvent(MonsterDeadEvent event) {
        // 获取角色人物
        PlayerTask playerTask = LOCAL_PLAYER_TASK_MAP.get(event.getPlayerId());
        if (playerTask == null) {
            return;
        }
        // 获取人物进度列表
        List<TaskProgress> taskProgresses = playerTask.getAllTaskProgress();
        // 便利任务进度 找到符合行为的进度
        for (TaskProgress taskProgress : taskProgresses) {
            // 如果进度已经完成了 则跳过
            if (taskProgress.isComplete()) {
                continue;
            }
            // 判断是否是杀怪，并且怪物目标相同
            if (taskProgress.getType() == EventType.EVENT_TYPE_MONSTER_DEAD &&
                    taskProgress.getTarget() == event.getMonsterConfigType()) {
                // 进度增加
                taskProgress.addNum(event.getValue());
                // 判断进度是否完成
                if (taskProgress.getAmount() == taskProgress.getNum()) {
                    // 设置进度已经完成
                    taskProgress.setComplete(true);
                }
            }
        }
    }
}
