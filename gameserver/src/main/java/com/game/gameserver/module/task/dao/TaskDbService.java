package com.game.gameserver.module.task.dao;

import com.game.gameserver.common.db.BaseDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/13 16:05
 */
@Repository
public class TaskDbService extends BaseDbService {
    @Autowired
    private TaskMapper taskMapper;

}
