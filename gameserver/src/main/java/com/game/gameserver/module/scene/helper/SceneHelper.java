package com.game.gameserver.module.scene.helper;

import com.game.gameserver.common.config.SceneConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.monster.helper.MonsterHelper;
import com.game.gameserver.module.player.helper.PlayerHelper;
import com.game.gameserver.module.scene.model.GameScene;
import com.game.gameserver.module.scene.model.Scene;

/**
 * 场景助手  负责广播/同步场景信息/
 *
 * @author xuewenkang
 * @date 2020/6/30 20:49
 */
public class SceneHelper {
    public static String buildScene(GameScene scene){
        StringBuilder sb = new StringBuilder("场景信息").append("\n");
        sb.append("场景名称:").append(scene.getName()).append("\n");
        sb.append("场景介绍:").append(scene.getDesc()).append("\n");
        sb.append("玩家列表:");
        scene.getPlayerMap().values().forEach(player -> {
            sb.append(player.getPlayerEntity().getName())
                    .append(PlayerHelper.buildPlayerStateMsg(player))
                    .append(")").append("(")
                    .append(player.getPlayerBattle().getCurrHp()).append(")")
                    .append("\t");
        });
        sb.append("\n");
        sb.append("怪物列表:");
        scene.getMonsterMap().values().forEach(monster -> {
            //
            String state = "";
            sb.append(monster.getName()).append("(").append(MonsterHelper.buildStateMsg(monster)).append(")")
                    .append("(").append(monster.getCurrHp()).append(")")
                    .append("\t");
        });
        sb.append("\n");
        sb.append("npc列表:");
        scene.getNpcMap().values().forEach(npc -> {
            sb.append(npc.getName()).append("\t");
        });
        sb.append("\n");
        sb.append("召唤兽列表:");
        scene.getPetMap().values().forEach(pet -> {
            sb.append(pet.getName()).append("(").append(pet.getCurrHp()).append(")")
                    .append("\t");
        });
        sb.append("\n");
        sb.append("附近场景:");
        scene.getNeighbors().forEach(
                sceneId->{
                    SceneConfig sceneConfig = StaticConfigManager.getInstance().getSceneConfigMap().get(sceneId);
                    if(sceneConfig!=null){
                        sb.append(sceneConfig.getName()).append("(")
                                .append(sceneConfig.getId()).append(")").append("\t");
                    }
                }
        );
        return sb.toString();
    }

    public static String buildAio(Scene scene){
        StringBuilder sb = new StringBuilder("场景实体信息：").append("\n");
        sb.append("玩家实体:").append("\n");
        scene.getPlayerMap().values().forEach(player -> {
            sb.append("id:").append(player.getPlayerEntity().getId()).append("\n");
            sb.append("name:").append(player.getPlayerEntity().getName()).append("\n");
            sb.append("HP:").append(player.getPlayerBattle().getCurrHp()).append("/")
                    .append(player.getPlayerBattle().getHp())
                    .append("\n");
        });
        sb.append("\n");
        sb.append("怪物实体:").append("\n");
        scene.getMonsterMap().values().forEach(
                monster -> {
                    sb.append("id:").append(monster.getId()).append("\n");
                    sb.append("name:").append(monster.getName()).append("\n");
                    sb.append("HP:").append(monster.getCurrHp()).append("/")
                            .append(monster.getHp())
                            .append("\n");
                }
        );
        sb.append("\n");
        sb.append("NPC实体:").append("\n");
        scene.getNpcMap().forEach((key, value) -> {
            sb.append("id:").append(value.getNpcId()).append("\n");
            sb.append("name:").append(value.getName()).append("\n");
        });
        sb.append("召唤物实体:").append("\n");
        scene.getPetMap().values().forEach(
                monster -> {
                    sb.append("id:").append(monster.getId()).append("\n");
                    sb.append("name:").append(monster.getName()).append("\n");
                    sb.append("HP:").append(monster.getCurrHp()).append("/")
                            .append(monster.getHp())
                            .append("\n");
                }
        );
        sb.append("\n");
        return sb.toString();
    }
}
