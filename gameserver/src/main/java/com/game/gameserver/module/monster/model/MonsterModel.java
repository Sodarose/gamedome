package com.game.gameserver.module.monster.model;

import com.game.gameserver.module.player.model.Property;
import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 */
@Data
public class Monster {
    private Integer id;
    private String name;
    private Integer level;
    private Integer title;
    private Property property;
}
