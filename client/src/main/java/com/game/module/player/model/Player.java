package com.game.module.player.model;

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
    private Integer careerId;
}
