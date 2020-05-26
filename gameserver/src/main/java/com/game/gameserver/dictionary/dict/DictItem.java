package com.game.gameserver.dictionary.dict;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/25 17:28
 */
@Data
public class DictItem {
    private Integer id;
    private String name;
    private Integer level;
    private Integer quality;
    private Integer type;
    private Integer overlay;
    private Integer effectId;
    private Integer equipId;
    private Integer limit;
    private String description;
}
