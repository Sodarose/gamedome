package com.game.module.game.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * player 基础属性 对应数据库
 * @author xuewenkang
 * @date 2020/5/25 10:14
 */
@Data
public class Role {
    private Integer id;
    private String name;
    private Integer level;
    private String career;
}
