package com.game.gameserver.entity;

import lombok.Data;

/**
 * @author xuwenkang
 */
@Data
public class BaseRole {
    private Integer id;
    private Integer name;
    private Integer ph;
    private Integer mp;
    private String description;
}
