package com.game.module.skill;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/4 1:38
 */
@Data
public class Skill {
    private long id;
    private String name;
    private int careerId;
    private int limitLevel;
    private int maxLearnLevel;
    private int coolTime;
    private String formula;
    private String desc;
    private int bagIndex;
    private int learnLevel;
    private int playerId;
}
