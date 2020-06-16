package com.game.gameserver.module.player.entity;

import com.game.gameserver.common.config.CareerLevelProperty;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.model.EquipBag;
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
    private int hp = 1000;
    private int currHp = 1000;
    private int mp = 1000;
    private int currMp = 1000;
    private int attack = 1000;
    private int defense = 1000;
}
