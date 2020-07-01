package com.game.util;


import com.game.module.monster.Monster;
import com.game.module.npc.Npc;
import com.game.module.player.OtherPlayerInfo;
import com.game.module.player.PlayerBattle;
import com.game.module.player.PlayerInfo;
import com.game.module.player.SimplePlayerInfo;
import com.game.module.scene.SceneInfo;
import com.game.module.store.Commodity;
import com.game.protocol.Actor;
import com.game.protocol.PlayerProtocol;
import com.game.protocol.SceneProtocol;
import com.game.protocol.Store;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * 转换工具
 *
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class TransFromUtil {

    public static SimplePlayerInfo transFromSimplePlayerInfo(PlayerProtocol.SimplePlayerInfo protocol) {
        SimplePlayerInfo simplePlayerInfo = new SimplePlayerInfo();
        BeanUtils.copyProperties(protocol, simplePlayerInfo);
        return simplePlayerInfo;
    }

    public static Commodity transFromCommodity(Store.CommodityInfo info) {
        Commodity commodity = new Commodity();
        commodity.setId(info.getId());
        commodity.setGoodsType(info.getGoodsType());
        commodity.setGoodsId(info.getGoodsId());
        commodity.setGoodsName(info.getGoodsName());
        commodity.setStoreType(info.getShoreType());
        commodity.setOriginalPrice(info.getOriginalPrice());
        commodity.setPrice(info.getPrice());
        commodity.setLimitCount(info.getLimitCount());
        return commodity;
    }

    public static PlayerInfo transFromPlayerInfo(PlayerProtocol.PlayerInfo info) {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setId(info.getId());
        playerInfo.setName(info.getName());
        playerInfo.setCareerId(info.getCareerId());
        playerInfo.setLevel(info.getLevel());
        playerInfo.setGolds(info.getGolds());
        playerInfo.setSceneId(info.getSceneId());
        playerInfo.setPlayerBattle(transFromPlayerBattle(info.getPlayerBattle()));
        return playerInfo;
    }

    public static PlayerBattle transFromPlayerBattle(PlayerProtocol.PlayerBattle info) {
        PlayerBattle playerBattle = new PlayerBattle();
        playerBattle.setHp(info.getHp());
        playerBattle.setMp(info.getMp());
        playerBattle.setCurrHp(info.getCurrHp());
        playerBattle.setCurrMp(info.getCurrMp());
        playerBattle.setDefense(info.getDefense());
        playerBattle.setAttack(info.getAttack());
        return playerBattle;
    }

    public static SceneInfo transFromSceneInfo(SceneProtocol.SceneInfo info) {
        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setId(info.getId());
        sceneInfo.setName(info.getName());
        sceneInfo.setDescription(info.getDescription());
        sceneInfo.setPlayerCount(info.getPlayersCount());

        for (Map.Entry<Long, Actor.MonsterInfo> entry : info.getMonstersMap().entrySet()) {
            Monster monster = transFromMonster(entry.getValue());
            assert monster != null;
            sceneInfo.getMonsterMap().put(monster.getId(), monster);
        }
        for (Map.Entry<Long, Actor.NpcInfo> entry : info.getNpcsMap().entrySet()) {
            Npc npc = transFromNpc(entry.getValue());
            assert npc != null;
            sceneInfo.getNpcMap().put(npc.getId(), npc);
        }
        for (Map.Entry<Long, PlayerProtocol.OtherPlayerInfo> entry : info.getPlayersMap().entrySet()) {
            OtherPlayerInfo otherPlayerInfo = transFromOtherPlayerInfo(entry.getValue());
            sceneInfo.getPlayerMap().put(otherPlayerInfo.getId(), otherPlayerInfo);
        }
        return sceneInfo;
    }

    public static Monster transFromMonster(Actor.MonsterInfo info) {
        Monster monster = new Monster();
        monster.setId(info.getId());
        monster.setName(info.getName());
        monster.setLevel(info.getLevel());
        monster.setState(info.getState());
        monster.setAttack(info.getAttack());
        monster.setDefense(info.getDefense());
        monster.setHp(info.getHp());
        monster.setMp(info.getMp());
        monster.setCurrHp(info.getCurrHp());
        monster.setCurrMp(info.getCurrMp());
        return monster;
    }

    public static Npc transFromNpc(Actor.NpcInfo info) {
        Npc npc = new Npc();
        npc.setName(info.getName());
        npc.setId(info.getId());
        npc.setLevel(info.getLevel());
        return npc;
    }

    public static OtherPlayerInfo transFromOtherPlayerInfo(PlayerProtocol.OtherPlayerInfo info) {
        OtherPlayerInfo otherPlayerInfo = new OtherPlayerInfo();
        otherPlayerInfo.setId(info.getId());
        otherPlayerInfo.setName(info.getName());
        otherPlayerInfo.setCareerId(info.getCareerId());
        otherPlayerInfo.setLevel(info.getLevel());
        otherPlayerInfo.setPlayerBattle(transFromPlayerBattle(info.getPlayerBattle()));
        return otherPlayerInfo;
    }
}
