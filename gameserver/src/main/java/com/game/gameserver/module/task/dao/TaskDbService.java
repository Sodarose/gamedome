package com.game.gameserver.module.task.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.task.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/13 16:05
 */
@Repository
public class TaskDbService extends BaseDbService {
    @Autowired
    private TaskMapper taskMapper;


    public List<TaskEntity> selectTaskEntityList(long playerId) {
        return taskMapper.selectTaskEntityList(playerId);
    }

    public TaskEntity select(long taskId, long playerId) {
        return taskMapper.select(taskId, playerId);
    }

    public int insert(TaskEntity taskEntity) {
        return taskMapper.insert(taskEntity);
    }

    public void insertAsync(TaskEntity taskEntity) {
        submit(() -> {
            int i = taskMapper.insert(taskEntity);
        });
    }

    public int update(TaskEntity taskEntity) {
        return taskMapper.insert(taskEntity);
    }

    public void updateAsync(TaskEntity taskEntity) {
        submit(() -> {
            int i = update(taskEntity);
        });
    }

    public int delete(long taskId, long playerId) {
        return taskMapper.delete(taskId, playerId);
    }

    public void deleteAsync(long taskId, long playerId) {
        submit(() -> {
            int i = delete(taskId,playerId);
        });
    }
}
