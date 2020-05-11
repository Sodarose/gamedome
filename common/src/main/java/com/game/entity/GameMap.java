package com.game.entity;

import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 * 游戏地图
 */
@Data
public class GameMap {
    private Integer id;
    private String name;
    private String description;
    private String way;
    private List<GameMap> ways;
}
