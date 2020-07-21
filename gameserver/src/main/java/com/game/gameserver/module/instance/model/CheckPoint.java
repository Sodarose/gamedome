package com.game.gameserver.module.instance.model;

import com.game.gameserver.common.config.CheckPointConfig;
import com.game.gameserver.module.instance.type.CheckPointState;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.npc.model.Npc;
import com.game.gameserver.module.pet.model.Pet;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 副本关卡
 *
 * @author xuewenkang
 * @date 2020/7/17 16:00
 */
@Data
public class CheckPoint {
    /** 关卡id */
    private int round;
    /** 怪物 */
    private Map<Long, Monster> monsterMap = new ConcurrentHashMap<>();
    /** 玩家 */
    private Map<Long, Player> playerMap = new ConcurrentHashMap<>();
    /** npc */
    private Map<Long, Npc> npcMap = new ConcurrentHashMap<>();
    /** 召唤物 */
    private Map<Long, Pet> petMap = new ConcurrentHashMap<>();
    /** 副本通关条件 */
    private int condition;
    /** 当前关卡状态 */
    private CheckPointState state;
    /** 关卡信息配置 */
    private CheckPointConfig checkPointConfig;

}
