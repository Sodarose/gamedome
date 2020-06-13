package com.game.module.monster;

import com.game.module.player.model.Property;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/1 15:17
 */
@Data
public class Monster {
    private Integer  id;
    private String   name;
    private Integer  level;
    private Property property;

    public Monster(){}

}
