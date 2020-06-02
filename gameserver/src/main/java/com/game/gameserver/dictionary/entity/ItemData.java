package com.game.gameserver.dictionary.entity;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:24
 */
@Data
public class ItemData {
    private int id;
    private String name;
    private int type;
    private int level;
    private boolean overlay;
    private int attachId;
    private String desc;
}
