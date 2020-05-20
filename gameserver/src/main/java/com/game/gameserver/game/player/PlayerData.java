package com.game.gameserver.game.player;

import java.util.Map;

/**
 * 用户游戏数据
 * @author xuewenkang
 * @date 2020/5/20 10:45
 */
public class PlayerData {
    private PlayerDao playerDao;
    
    /**
     * 人物基础属性值
     */
    private Map<Integer,Integer> property;

    /**
     * 人物计算后的属性值
     */
    private Map<Integer,Integer> propertyTemp;


}
