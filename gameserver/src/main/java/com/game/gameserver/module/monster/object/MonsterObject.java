package com.game.gameserver.module.monster.object;

import com.game.gameserver.dictionary.entity.MonsterData;
import com.game.gameserver.module.monster.entity.MonsterEntity;
import com.game.gameserver.module.player.entity.PropertyEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author xuewenkang
 */
@Data
public class MonsterObject {
    private int id;
    private MonsterData monsterData;
    private PropertyEntity propertyEntity;

    public MonsterObject(int id,MonsterData monsterData){
        this.id = id;
        this.monsterData = monsterData;
    }

    public void init(){
        BeanUtils.copyProperties(monsterData.getPropertyData(),propertyEntity);
    }
}
