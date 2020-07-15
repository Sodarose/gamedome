package com.game.gameserver.module.task.dao;

import com.game.gameserver.module.task.entity.TaskEntity;
import com.game.gameserver.module.task.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/29 16:39
 */
@Repository
@Mapper
public interface TaskMapper {
    List<TaskEntity> selectTaskEntityList(long playerId);

    TaskEntity select(long taskId, long playerId);

    int insert(TaskEntity taskEntity);

    int update(TaskEntity taskEntity);

    int delete(long taskId, long playerId);
}
