package com.game.gameserver.module.monster.entity;

import com.game.gameserver.module.player.model.Property;
import lombok.Data;

@Data
public class Monster {
    private Integer id;
    private String name;
    private Integer level;
    private Integer title;
    private Property property;

}
