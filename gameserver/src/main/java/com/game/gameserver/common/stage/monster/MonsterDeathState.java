package com.game.gameserver.common.stage.monster;

import com.game.gameserver.common.stage.State;
import com.game.gameserver.dictionary.entity.MonsterData;
import com.game.gameserver.module.monster.object.MonsterObject;

/**
 * 怪物死亡状态
 *
 * @author xuewenkang
 * @date 2020/6/4 10:34
 */
public class MonsterDeathState implements State<MonsterObject> {

    private final static MonsterData

    @Override
    public void onEntry(MonsterObject monsterObject) {
        monsterObject.setState(MonsterStateConfig.DEATH);
        monsterObject.setDie(true);
    }

    @Override
    public void execute(MonsterObject monsterObject) {

    }

    @Override
    public void onExit(MonsterObject monsterObject) {

    }
}
