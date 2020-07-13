package com.game.gameserver.module.guild.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * 公会实体
 *
 * @author xuewenkang
 * @date 2020/7/2 20:47
 */
@Data
public class Union {
    /** 公会id */
    private Long id;
    /** 公会名称 */
    private String name;
    /** 公会等级 */
    private Integer level;
    /** 当前公会经验值*/
    private Integer expr;
    /** 成员容量*/
    private Integer capacity;
    /** 工会仓库容量*/
    private Integer warehouseCapacity;
    /** 金币 */
    private Long golds;
    /** 公会公告*/
    private String announcement;
    /** 公会创建时间 */
    private LocalDate createTime;
    /** 更新时间 */
    private LocalDate updateTime;
}
