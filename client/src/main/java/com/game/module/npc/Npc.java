package com.game.module.npc;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/1 15:17
 */
@Data
public class Npc {
    private Long id;
    private Integer level;
    private String name;
    private Integer career;

    public Npc(){

    }
}
