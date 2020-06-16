package com.game.gameserver.module.pk.model;

import com.game.gameserver.module.player.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 竞技场
 *
 * @author xuewenkang
 * @date 2020/6/12 16:49
 */
public class Arena {
    /** 竞技场唯一Id */
    private final int id;
    /** 竞技场名称 */
    private final String title;
    /** 竞技场最大人数 */
    private final int maxCount;
    /** 玩家列表 */
    private final List<Player> playerList = new ArrayList<>();

    public Arena(int id,String title,int maxCount){
        this.id = id;
        this.title = title;
        this.maxCount = maxCount;
    }

}
