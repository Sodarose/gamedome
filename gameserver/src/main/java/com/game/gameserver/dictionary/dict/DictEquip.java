package com.game.gameserver.dictionary.dict;

import lombok.Data;

/**
 * 装备表
 * @author xuewenkang
 * @date 2020/5/21 15:44
 */
@Data
public class DictEquip {
    private Integer id;
    private Integer part;
    private Integer maxDurability;
    private String property1;
    private String property2;
    private String property3;
    private String property4;
    private String property5;
    private String property6;
}
