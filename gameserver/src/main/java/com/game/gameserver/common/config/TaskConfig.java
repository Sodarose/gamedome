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
 * 任务资源
 *
 * @author xuewenkang
 * @date 2020/6/29 14:52
 */
@Data
public class TaskConfig {
    /**
     * 任务id
     */
    @JSONField(name = "id")
    private int id;

    /**
     * 任务名称
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 任务描述
     */
    @JSONField(name = "description")
    private String description;

    /**
     * 任务类型 主线 支线 副本
     */
    @JSONField(name = "taskKind")
    private int kind;

    @JSONField(name = "type")
    private int type;

    /**
     * 是否可以取消
     */
    @JSONField(name = "cancel")
    private boolean cancel;

    /**
     * 挂载的NPC
     */
    @JSONField(name = "npcId")
    private int npcId;

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
    @JSONField(name = "expr")
    private int expr;

    /**
     * 金币奖励
     */
    @JSONField(name = "golds")
    private int golds;

    /**
     * 任务条件表
     */
    @JSONField(serialize = false)
    private Map<Integer, TaskCondition> taskConditionMap;

    /**
     * 任务奖励
     */
    @JSONField(name = "awards")
    private List<Award> awards;


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
