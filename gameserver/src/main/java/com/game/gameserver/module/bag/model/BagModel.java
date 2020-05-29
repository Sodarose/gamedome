package com.game.gameserver.module.bag.model;

import lombok.Data;

/**
 * 背包数据  对应数据库
 * @author xuewenkang
 * @date 2020/5/27 9:35
 */
@Data
public class BagModel {
    private Integer id;
    private String  bagName;
    private Integer bagType;
    private Integer roleId;
}
