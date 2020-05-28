package com.game.gameserver.module.bag.model;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/27 9:58
 */
@Data
public class CellModel {
    private Integer id;
    private Integer itemId;
    private Integer bagId;
    private Integer bagIndex;
    private Integer count;
}
