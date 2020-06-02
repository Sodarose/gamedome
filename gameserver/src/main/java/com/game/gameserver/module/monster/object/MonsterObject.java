package com.game.gameserver.module.monster.object;

import com.game.gameserver.module.monster.entity.MonsterEntity;
import com.game.gameserver.module.player.entity.PropertyEntity;
import lombok.Data;

/**
 * @author xuewenkang
 */
@Data
public class MonsterObject {
    private MonsterEntity monsterEntity;
    private PropertyEntity propertyEntity;
}
