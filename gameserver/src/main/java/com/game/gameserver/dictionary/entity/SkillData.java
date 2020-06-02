package com.game.gameserver.dictionary.entity;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:15
 */
@Data
public class SkillData {
    private int id;
    private String name;
    private int career;
    private int level;
    private int coolTime;
    private String formula;
    private String desc;
}
