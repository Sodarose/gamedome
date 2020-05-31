package com.game.gameserver.module.npc.entity;

import com.game.gameserver.module.player.model.Property;
import lombok.Data;

@Data
public class Npc {
    private Integer  id;
    private String   name;
    private Integer  level;
    private Integer  career;
    private Property property;
}
