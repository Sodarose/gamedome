package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.game.gameserver.module.task.entity.TaskProgress;
import lombok.Data;

import java.util.ArrayList;
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
     * 任务完成条件 角色行为_目标_条件要求数量|角色行为_目标_条件要求数量
     */
    @JSONField(name = "taskRequire")
    private String taskRequire;

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
     * 道具奖励 道具基础Id_道具数量|道具基础Id_道具数量
     */
    @JSONField(name = "props")
    private String props;

    /**
     * 装备奖励  装备奖励id|装备奖励Id
     */
    @JSONField(name = "equips")
    private String equips;

    /**
     * 道具奖励
     */
    @JSONField(serialize = false)
    private Map<Integer, Integer> propAwards;

    /**
     * 装备奖励
     */
    @JSONField(serialize = false)
    private List<Integer> equipAwards;

    public Map<Integer, Integer> getPropAwards() {
        if (propAwards != null) {
            return propAwards;
        }
        String[] propAwardStrs = props.split("\\|");
        propAwards = new HashMap<>(propAwardStrs.length);
        for (String propAwardStr : propAwardStrs) {
            String[] propAward = propAwardStr.split("_");
            if (propAward.length != 2) {
                continue;
            }
            int propConfigId = Integer.parseInt(propAward[0]);
            int num = Integer.parseInt(propAward[2]);
            propAwards.put(propConfigId, num);
        }
        return propAwards;
    }

    public List<Integer> getEquipAwards() {
        if (equipAwards != null) {
            return equipAwards;
        }
        equipAwards = new ArrayList<>();
        String[] equipAwardStrs = equips.split("\\|");
        for (String equipAward : equipAwardStrs) {
            int equipConfigId = Integer.parseInt(equipAward);
            equipAwards.add(equipConfigId);
        }
        return equipAwards;
    }

    public List<TaskProgress> parseTaskRequire() {
        List<TaskProgress> taskProgresses = new ArrayList<>();
        String[] requireStrs = taskRequire.split("\\|");
        for (String requireStr : requireStrs) {
            String[] require = requireStr.split("_");
            // 行为类型
            int type = Integer.parseInt(require[0]);
            // 任务目标
            int target = Integer.parseInt(require[1]);
            // 要求数量
            int amount = Integer.parseInt(require[2]);
            // 创建任务要求/进度
            TaskProgress taskProgress = new TaskProgress(type, target, amount);
            // 加入
            taskProgresses.add(taskProgress);
        }
        return taskProgresses;
    }
}
