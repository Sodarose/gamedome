package com.game.module.player.model;

import com.game.protocol.PlayerProtocol;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/26 15:38
 */
@Data
public class Player {
    private Integer id;
    private String  name;
    private Integer level;
    private Integer career;

    public Player(){}

    public Player(PlayerProtocol.SimplePlayerInfo simplePlayerInfo){
        this.id = simplePlayerInfo.getId();
        this.name = simplePlayerInfo.getName();
        this.level = simplePlayerInfo.getLevel();
        this.career = simplePlayerInfo.getCareer();
    }
}
