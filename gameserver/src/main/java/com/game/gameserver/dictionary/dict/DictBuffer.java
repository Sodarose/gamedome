package com.game.gameserver.dictionary.dict;

import lombok.Data;
import lombok.ToString;

/**
 * Buff
 * @author xuewenkang
 * @date 2020/5/21 15:17
 */
@Data
@ToString
public class DictBuffer {
    private Integer id;
    private Integer type;
    private String name;
    private Integer lastTime;
    private String property1;
    private String property2;
    private String property3;
    private String property4;
    private String property5;
    private String property6;
    private String description;
}
