package com.game.util;

import com.game.entity.GameRole;
import com.game.entity.Monster;
import com.game.entity.Npc;
import com.game.entity.Scene;
import com.game.pojo.GameMap;
import com.game.protocol.Protocol;

import java.util.Map;

/**
 * 协议工具类 主要用于Pojo、entity 与 Protocol 之间的类型转换
 * @author xuewenkang
 * @date 2020/5/14 11:59
 */
public class ProtocolUtil {

    /**
     * 实体Scene 生成 Protocol.Scene
     * @param scene 实体Scene
     * @return com.game.protocol.Protocol.Scene
     */
    public static Protocol.Scene sceneToProtocolScene(Scene scene) {
        Protocol.Scene.Builder sceneBuilder = Protocol.Scene.newBuilder();
        sceneBuilder.setMap(gameMapToProtocolMap(scene.getGameMap()));
        for (Map.Entry<Integer, GameRole> entry : scene.getRoles().entrySet()) {
            sceneBuilder.putRoles(entry.getKey(), gameRoleToProtocolRole(entry.getValue()));
        }
        for (Map.Entry<Integer, Monster> entry : scene.getMonsters().entrySet()) {
            sceneBuilder.putMonster(entry.getKey(), gameMonsterToProtocolMonster(entry.getValue()));
        }
        for (Map.Entry<Integer, Npc> entry : scene.getNpcs().entrySet()) {
            sceneBuilder.putNpc(entry.getKey(), gameNpcToProtocolNpc(entry.getValue()));
        }
        return sceneBuilder.build();
    }

    /**
     * 实体NPC 生成 Protocol.Npc
     * @param npc 实体Npc
     * @return com.game.protocol.Protocol.Npc
     */
    public static Protocol.Npc gameNpcToProtocolNpc(Npc npc) {
        Protocol.Npc.Builder builder = Protocol.Npc.newBuilder();
        builder.setId(npc.getId());
        builder.setName(npc.getName());
        builder.setPh(npc.getPh());
        builder.setMp(npc.getMp());
        builder.setPhyAttack(npc.getPhyAttack());
        builder.setPhyDefense(npc.getPhyDefense());
        builder.setMagicAttack(npc.getMagicAttack());
        builder.setMagicDefense(npc.getMagicDefense());
        builder.setMapId(npc.getMapId());
        builder.setStatus(npc.getStatus());
        return builder.build();
    }

    /**
     * 实体Monster 生成 Protocol.Monster
     * @param monster 实体Monster
     * @return com.game.protocol.Protocol.Monster
     */
    public static Protocol.Monster gameMonsterToProtocolMonster(Monster monster) {
        Protocol.Monster.Builder builder = Protocol.Monster.newBuilder();
        builder.setId(monster.getId());
        builder.setName(monster.getName());
        builder.setPh(monster.getPh());
        builder.setMp(monster.getMp());
        builder.setPhyAttack(monster.getPhyAttack());
        builder.setPhyDefense(monster.getPhyDefense());
        builder.setMagicAttack(monster.getMagicAttack());
        builder.setMagicDefense(monster.getMagicDefense());
        builder.setMapId(monster.getMapId());
        builder.setStatus(monster.getStatus());
        return builder.build();
    }

    /**
     * 实体Role 生成 Protocol.Role
     * @param gameRole 实体Role
     * @return com.game.protocol.Protocol.Role
     */
    public static Protocol.Role gameRoleToProtocolRole(GameRole gameRole) {
        Protocol.Role.Builder builder = Protocol.Role.newBuilder();
        builder.setId(gameRole.getId());
        builder.setName(gameRole.getName());
        builder.setPh(gameRole.getPh());
        builder.setMp(gameRole.getMp());
        builder.setPhyAttack(gameRole.getPhyAttack());
        builder.setPhyDefense(gameRole.getPhyDefense());
        builder.setMagicAttack(gameRole.getMagicAttack());
        builder.setMagicDefense(gameRole.getMagicDefense());
        builder.setMapId(gameRole.getMapId());
        builder.setStatus(gameRole.getStatus());
        return builder.build();
    }

    /**
     * 实体Map 生成 Protocol.Map
     * @param gameMap 实体Map
     * @return com.game.protocol.Protocol.Map
     */
    public static Protocol.Map gameMapToProtocolMap(GameMap gameMap) {
        Protocol.Map.Builder mapBuilder = Protocol.Map.newBuilder();
        mapBuilder.setId(gameMap.getId());
        mapBuilder.setName(gameMap.getName());
        mapBuilder.setDescription(gameMap.getDescription());
        if (gameMap.getWays() != null && gameMap.getWays().size() != 0) {
            for (GameMap map : gameMap.getWays()) {
                Protocol.Map tmp = Protocol.Map.newBuilder()
                        .setId(map.getId())
                        .setName(map.getName())
                        .setDescription(map.getDescription()).build();
                mapBuilder.addWays(tmp);
            }
        }
        return mapBuilder.build();
    }
}
