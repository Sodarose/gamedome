package com.game.gameserver.module.player.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * player 基础属性 对应数据库 必要时保存在数据库的数据
 * @author xuewenkang
 * @date 2020/5/25 10:14
 */
@Data
public class PlayerEntity {
    private Integer id;
    private String name;
    private Integer level;
    private Integer career;
    private Integer sceneId;
    private Integer userId;
    private LocalDate createTime;
    private LocalDate updateTime;
}
