package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 任务资源
 *
 * @author xuewenkang
 * @date 2020/6/29 14:52
 */
@Data
public class TaskConfig {
    /** 任务id */
    @JSONField(name = "id")
    private int id;

    /** 任务名称 */
    @JSONField(name = "name")
    private String name;

    /** 任务描述 */
    @JSONField(name = "description")
    private String description;

    /** 任务类型 主线 支线 副本*/
    @JSONField(name = "type")
    private int type;

    /** 是否可以取消 */
    @JSONField(name = "cancel")
    private boolean cancel;

    /** 挂载的NPC*/
    @JSONField(name = "npcId")
    private int npcId;

    /** 等级限制 */
    @JSONField(name = "limitLevel")
    private int limitLevel;

    /** 任务完成条件 条件类型（角色行为）_条件内容_条件要求数量|条件类型（角色行为）_条件内容_条件要求数量*/
    @JSONField(name = "condition")
    private String taskRequire;

    /** 任务奖励 经验奖励*/
    @JSONField(name = "expr")
    private int expr;

    /** 金币奖励*/
    @JSONField(name = "golds")
    private int golds;

    /** 道具奖励 道具基础Id_道具数量|道具基础Id_道具数量*/
    @JSONField(name = "props")
    private String props;

    /** 装备奖励  装备奖励id|装备奖励Id*/
    @JSONField(name = "equips")
    private String equips;
}
