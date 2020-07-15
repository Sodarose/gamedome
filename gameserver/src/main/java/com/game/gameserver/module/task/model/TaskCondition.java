package com.game.gameserver.module.task.model;

import lombok.Data;

import java.util.List;

/**
 * 任务完成条件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:04
 */
@Data
public class TaskCondition {
    private int target;
    /** 要求值 */
    private int amount;
    /** 条件描述*/
    private String desc;
}
