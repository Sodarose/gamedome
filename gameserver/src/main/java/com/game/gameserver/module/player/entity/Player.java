package com.game.gameserver.module.player.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * 用户数据库存储对象
 *
 * @author xuewenkang
 * @date 2020/6/9 20:21
 */
@Data
public class Player {
    /** id */
    private Integer id;
    /** 姓名 */
    private String name;
    /** 等级 */
    private Integer level;
    /** 职业 */
    private Integer careerId;
    /** 场景Id */
    private Integer sceneId;
    /** 金币数量 */
    private Integer golds;
    /** 账号ID */
    private Integer userId;
    /** 创建时间 */
    private LocalDate createTime;
    /** 更新时间 */
    private LocalDate updateTime;
}