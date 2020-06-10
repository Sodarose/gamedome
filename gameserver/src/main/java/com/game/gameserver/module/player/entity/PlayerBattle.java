package com.game.gameserver.module.player.entity;

import com.game.gameserver.module.buffer.model.Buffer;
import lombok.Data;

import java.util.List;

/**
 * 玩家角色战斗属性
 *
 * @author xuewenkang
 * @date 2020/6/10 10:22
 */
@Data
public class PlayerBattle {
    /** hp */
    private int hp;
    /** 当前hp */
    private int currHp;
    /** mp */
    private int mp;
    /** 当前mp */
    private int currMp;
    /** 攻击力 */
    private int attack;
    /** 防御力 */
    private int defense;
    /** buff列表 */
    private List<Buffer> bufferList;
}
