package com.game.gameserver.util;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.item.entity.Equip;
import com.game.gameserver.module.item.entity.Prop;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.npc.model.NpcObject;
import com.game.gameserver.module.player.entity.PlayerBattle;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.model.SceneObject;
import com.game.gameserver.module.skill.entity.Skill;
import com.game.gameserver.module.skill.model.PlayerSkill;
import com.game.gameserver.module.store.entity.Commodity;
import com.game.gameserver.module.team.entity.Team;
import com.game.protocol.*;

import java.util.List;
import java.util.Map;

/**
 * 转换工具
 *
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class ProtocolFactory {

    /**
     * 将playerList 转换为 PlayerProtocol.PlayerList
     *
     * @param playerList
     * @return com.game.protocol.PlayerProtocol.PlayerList
     */
    public static PlayerProtocol.PlayerListRes createPlayerList(List<com.game.gameserver.module.player.entity.Player> playerList) {
        PlayerProtocol.PlayerListRes.Builder builder = PlayerProtocol.PlayerListRes.newBuilder();
        for (com.game.gameserver.module.player.entity.Player player : playerList) {
            builder.addPlayerInfoList(createSimplePlayerInfo(player));
        }
        return builder.build();
    }

    /**
     * 将player 转换为 PlayerProtocol.BriefPlayerInfo
     *
     * @param player
     * @return com.game.protocol.PlayerProtocol.BriefPlayerInfo
     */
    public static PlayerProtocol.SimplePlayerInfo createSimplePlayerInfo(com.game.gameserver.module.player.entity.Player player) {
        PlayerProtocol.SimplePlayerInfo.Builder builder = PlayerProtocol.SimplePlayerInfo.newBuilder();
        builder.setId(player.getId().intValue());
        builder.setName(player.getName());
        builder.setCareerId(player.getCareerId());
        builder.setLevel(player.getLevel());
        return builder.build();
    }

    public static PlayerProtocol.LoginPlayerRes createLoginPlayerRes(int code, String msg, PlayerObject playerObject) {
        PlayerProtocol.LoginPlayerRes.Builder builder = PlayerProtocol.LoginPlayerRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (playerObject != null) {
            builder.setPlayerInfo(createPlayerInfo(playerObject));
        }
        return builder.build();
    }

    public static PlayerProtocol.PlayerInfo createPlayerInfo(PlayerObject playerObject) {
        PlayerProtocol.PlayerInfo.Builder builder = PlayerProtocol.PlayerInfo.newBuilder();
        builder.setId(playerObject.getPlayer().getId());
        builder.setName(playerObject.getPlayer().getName());
        builder.setLevel(playerObject.getPlayer().getLevel());
        builder.setCareerId(playerObject.getPlayer().getCareerId());
        builder.setGolds(playerObject.getPlayer().getGolds());
        builder.setSceneId(playerObject.getPlayer().getSceneId());
        // 人物属性
        builder.setPlayerBattle(createPlayerBattle(playerObject.getPlayerBattle()));
        return builder.build();
    }


    public static GoodsProtocol.GoodsInfo createGoodsInfo(Equip equip) {
        return null;
    }

    public static GoodsProtocol.GoodsInfo createGoodsInfo(Prop prop) {
        return null;
    }

    public static PlayerProtocol.PlayerBattle createPlayerBattle(PlayerBattle playerBattle) {
        PlayerProtocol.PlayerBattle.Builder builder = PlayerProtocol.PlayerBattle.newBuilder();
        builder.setHp(playerBattle.getHp());
        builder.setMp(playerBattle.getMp());
        builder.setCurrHp(playerBattle.getCurrHp());
        builder.setCurrMp(playerBattle.getCurrMp());
        builder.setAttack(playerBattle.getAttack());
        builder.setDefense(playerBattle.getDefense());
        return builder.build();
    }

    public static PlayerProtocol.PlayerSkill createPlayerSkill(PlayerSkill playerSkill) {
        PlayerProtocol.PlayerSkill.Builder builder = PlayerProtocol.PlayerSkill.newBuilder();
        for (Skill skill : playerSkill.getSkillList()) {
            builder.addSkillInfo(createSkillInfo(skill));
        }
        return builder.build();
    }

    public static PlayerProtocol.SkillInfo createSkillInfo(Skill skill) {
        PlayerProtocol.SkillInfo.Builder builder = PlayerProtocol.SkillInfo.newBuilder();
        SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skill.getSkillId());
        builder.setId(skill.getId());
        builder.setName(skillConfig.getName());
        builder.setCareerId(skillConfig.getCareerId());
        builder.setLimitLevel(skillConfig.getLimitLevel());
        builder.setMaxLearnLevel(skillConfig.getMaxLearnLevel());
        builder.setCoolTime(skillConfig.getCoolTime());
        builder.setFormula(skillConfig.getFormula());
        builder.setDesc(skillConfig.getDesc());
        builder.setBagIndex(skill.getBagIndex());
        builder.setLearnLevel(skill.getLearnLevel());
        builder.setPlayerId(skill.getPlayerId());
        return builder.build();
    }


    /**
     * @param sceneObject
     * @return com.game.protocol.SceneProtocol.SceneInfo
     */
    public static SceneProtocol.SceneInfo createSceneInfo(SceneObject sceneObject) {
        SceneProtocol.SceneInfo.Builder builder = SceneProtocol.SceneInfo.newBuilder();
        builder.setId(sceneObject.getId());
        builder.setName(sceneObject.getSceneConfig().getName());
        builder.setDescription(sceneObject.getSceneConfig().getDesc());
        builder.setPlayerNum(sceneObject.getPlayerNum());
        for (Map.Entry<Long, PlayerObject> entry : sceneObject.getPlayerObjectMap().entrySet()) {
            builder.putPlayers(entry.getKey(), createOtherPlayerInfo(entry.getValue()));
        }
        for (Map.Entry<Long, MonsterObject> entry : sceneObject.getMonsterObjectMap().entrySet()) {
            builder.putMonsters(entry.getKey(), createMonster(entry.getValue()));
        }
        for (Map.Entry<Long, NpcObject> entry : sceneObject.getNpcObjectMap().entrySet()) {
            builder.putNpcs(entry.getKey(), createNpc(entry.getValue()));
        }
        return builder.build();
    }

    /**
     * @param playerObject
     * @return com.game.protocol.PlayerProtocol.OtherPlayerInfo
     */
    public static PlayerProtocol.OtherPlayerInfo createOtherPlayerInfo(PlayerObject playerObject) {
        PlayerProtocol.OtherPlayerInfo.Builder builder = PlayerProtocol.OtherPlayerInfo.newBuilder();
        builder.setId(playerObject.getPlayer().getId());
        builder.setName(playerObject.getPlayer().getName());
        builder.setLevel(playerObject.getPlayer().getLevel());
        builder.setCareerId(playerObject.getPlayer().getCareerId());
        builder.setPlayerBattle(createPlayerBattle(playerObject.getPlayerBattle()));
        return builder.build();
    }

    /**
     * @param monsterObject
     * @return com.game.protocol.SceneProtocol.Monster
     */
    public static Actor.MonsterInfo createMonster(MonsterObject monsterObject) {
        Actor.MonsterInfo.Builder builder = Actor.MonsterInfo.newBuilder();
        builder.setId(monsterObject.getId());
        builder.setName(monsterObject.getMonsterConfig().getName());
        builder.setLevel(monsterObject.getMonsterConfig().getLevel());
        builder.setHp(monsterObject.getProperty().getHp());
        builder.setMp(monsterObject.getProperty().getMp());
        builder.setAttack(monsterObject.getProperty().getAttack());
        builder.setDefense(monsterObject.getProperty().getDefense());
        builder.setCurrHp(monsterObject.getProperty().getCurrHp());
        builder.setCurrMp(monsterObject.getProperty().getCurrMp());
        return builder.build();
    }

    public static Actor.NpcInfo createNpc(NpcObject npcObject) {
        Actor.NpcInfo.Builder builder = Actor.NpcInfo.newBuilder();
        builder.setId(npcObject.getId());
        builder.setName(npcObject.getNpcConfig().getName());
        builder.setLevel(npcObject.getNpcConfig().getLevel());
        return builder.build();
    }

    public static Store.CommodityList createCommodityList(List<Commodity> commodities) {
        Store.CommodityList.Builder builder = Store.CommodityList.newBuilder();
        for (Commodity commodity : commodities) {
            builder.addCommodityInfos(createCommodityInfo(commodity));
        }
        return builder.build();
    }


    public static Store.CommodityInfo createCommodityInfo(Commodity commodity) {
        Store.CommodityInfo.Builder builder = Store.CommodityInfo.newBuilder();
        CommodityConfig commodityConfig = StaticConfigManager.getInstance().getCommodityConfigMap()
                .get(commodity.getCommodityId());
        if (commodityConfig == null) {
            return null;
        }

        builder.setId(commodityConfig.getId());
        builder.setGoodsType(commodityConfig.getGoodsType());
        builder.setGoodsId(commodityConfig.getGoodsId());

        if (ItemType.EQUIP == commodityConfig.getGoodsType()) {
            EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap()
                    .get(commodityConfig.getGoodsId());
            if (equipConfig == null) {
                return null;
            }
            builder.setGoodsName(equipConfig.getName());
        }

        if (ItemType.PROP == commodityConfig.getGoodsType()) {
            PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap()
                    .get(commodityConfig.getGoodsId());
            if (propConfig == null) {
                return null;
            }
            builder.setGoodsName(propConfig.getName());
        }
        builder.setShoreType(commodityConfig.getStoreType());
        builder.setOriginalPrice(commodityConfig.getOriginalPrice());
        builder.setPrice(commodityConfig.getOriginalPrice());
        builder.setLimitCount(commodityConfig.getLimitCount());
        return builder.build();
    }

    public static TeamProtocol.CreateTeamRes createCreateTeamRes(int code, String msg, Team team) {
        TeamProtocol.CreateTeamRes.Builder builder = TeamProtocol.CreateTeamRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if (team != null) {
            builder.setTeamInfo(createTeamInfo(team));
        }
        return builder.build();
    }

    public static TeamProtocol.TeamInfo createTeamInfo(Team team) {
        TeamProtocol.TeamInfo.Builder builder = TeamProtocol.TeamInfo.newBuilder();
        builder.setId(team.getId());
        builder.setCaptainId(team.getCaptainId());
        builder.setTeamName(team.getTeamName());
        builder.setCurrNum(team.getCurrNum());
        builder.setMaxNum(team.getMaxNum());
        builder.setFull(team.isFull());
        for (Long playerId : team.getMembers()) {
            PlayerObject playerObject = PlayerManager.instance.getPlayerObject(playerId);
            if (playerObject == null) {
                continue;
            }
            builder.addMemberName(playerObject.getPlayer().getName());
        }
        return builder.build();
    }

    public static TeamProtocol.EntryTeamRes createEntryRes(int code, String msg, Team team) {
        TeamProtocol.EntryTeamRes.Builder builder = TeamProtocol.EntryTeamRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if(team!=null){
            builder.setTeamInfo(createTeamInfo(team));
        }
        return builder.build();
    }
}
