package com.game.module.monster;

import com.game.module.player.model.Property;
import com.game.protocol.ActorProtocol;
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

    public Monster(ActorProtocol.SimpleMonsterInfo simpleMonsterInfo){
        this.id = simpleMonsterInfo.getId();
        this.level = simpleMonsterInfo.getLevel();
        this.name = simpleMonsterInfo.getName();
    }


}
