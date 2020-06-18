package com.game.module.player;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/15 11:09
 */
@Data
public class OtherPlayerInfo {
    private long id;
    private String name;
    private int level;
    private int careerId;
    private int golds;
    private int sceneId;
    private PlayerBattle playerBattle;
}
