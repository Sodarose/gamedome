package com.game.gameserver.dictionary.dict;

import lombok.Data;
import lombok.ToString;

/**
 * 场景
 * @author xuewenkang
 * @date 2020/5/21 16:20
 */
@Data
@ToString
public class DictScene {
    private Integer id;
    private String name;
    private String way;
    private String monsterGenerateStrategy;
    private String npcGenerateStrategy;
    private Integer maxCount;
    private Integer minCount;
    private Integer frequency;
    private Integer freshCount;
    private String description;
}
