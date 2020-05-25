package com.game.gameserver.dictionary.dict;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/21 15:59
 */
@Data
public class DictNpc {
    private Integer id;
    private String name;
    private String talkPath;
    private String talkContent;
    private String description;
}
