package com.game.gameserver.dictionary.dict;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/21 16:22
 */
@Data
public class DictSkill {
    private Integer id;
    private String name;
    private Integer type;
    private Integer baseAttack;
    private Integer needMp;
    private String description;
}
