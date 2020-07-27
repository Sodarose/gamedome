package com.game.gameserver.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.game.gameserver.module.task.model.Award;
import com.game.gameserver.module.task.model.TaskCondition;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成就资源类
 *
 * @author xuewenkang
 * @date 2020/7/2 11:58
 */
@Data
public class AchievementConfig {
    /** 成就Id */
    @JSONField(name = "id")
    int id;

    /**
     * 成就名称
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 成就描述
     */
    @JSONField(name = "description")
    private String description;

    /**
     * 成就类型
     */
    @JSONField(name = "kind")
    private int kind;

    @JSONField(name = "type")
    private int type;

    /**
     * 等级限制
     */
    @JSONField(name = "limitLevel")
    private int limitLevel;

    /**
     * 任务完成条件
     */
    @JSONField(name = "taskCondition")
    private String taskCondition;

    /**
     * 任务奖励 经验奖励
     */
    @JSONField(name = "exprAward")
    private int exprAward;

    /**
     * 金币奖励
     */
    @JSONField(name = "goldAward")
    private int goldAward;

    /**
     * 任务条件表
     */
    @JSONField(serialize = false)
    private Map<Integer, TaskCondition> taskConditionMap;

    /**
     * 任务奖励
     */
    @JSONField(name = "itemAward")
    private List<Award> itemAward;


    public Map<Integer, TaskCondition> getTaskConditionMap() {
        if (taskConditionMap != null) {
            return taskConditionMap;
        }
        List<TaskCondition> taskConditions = JSON.parseArray(taskCondition,TaskCondition.class);
        taskConditionMap = new HashMap<>();
        taskConditions.forEach(condition -> {
            taskConditionMap.put(condition.getTarget(),condition);
        });
        return taskConditionMap;
    }

}
