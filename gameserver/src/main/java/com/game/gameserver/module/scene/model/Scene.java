package com.game.gameserver.module.scene.model;

import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.npc.model.Npc;
import com.game.gameserver.module.pet.model.Pet;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.scene.SceneType;

import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/17 16:38
 */
public interface Scene {
    Map<Long, Player> getPlayerMap();

    Map<Long, Monster> getMonsterMap();

    Map<Long, Npc> getNpcMap();

    Map<Long, Pet> getPetMap();

    SceneType getSceneType();
}
