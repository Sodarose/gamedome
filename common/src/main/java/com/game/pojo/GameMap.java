package com.game.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 游戏地图
 * @author xuewenkang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameMap {
    
    /**
     * ID
     */
    private Integer id;
    
    /**
     * 地图名称
     */
    private String name;

    /***
     * 地图介绍
     */
    private String description;

    /**
     * 地图出口 格式 "1,2,3,4" 字符串中数字表示连接的其他地图ID
     */
    private String way;

    /**
     *  地图出口
     * */
    private List<GameMap> ways;
}
