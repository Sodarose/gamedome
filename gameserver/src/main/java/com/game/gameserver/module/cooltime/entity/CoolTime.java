package com.game.gameserver.module.cooltime.entity;

import lombok.Data;
import lombok.Getter;

/**
 * 冷却时间实体对象
 *
 * @author xuewenkang
 * @date 2020/6/3 12:28
 */
@Getter
public class CoolTime {
    /** 开始时间 */
    private final Long startTime;
    /** 冷却时间 */
    private final Long endTime;

    public CoolTime(long startTime,long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }
}