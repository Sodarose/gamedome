package com.game.gameserver.module.player.helper;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.fsm.state.player.PlayerState;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.entity.Role;

import java.util.List;

/**
 * 角色辅助类
 *
 * @author xuewenkang
 * @date 2020/7/2 9:36
 */
public class PlayerHelper {

    public static String buildRoleList(List<Role> roles) {
        StringBuilder sb = new StringBuilder("角色列表:").append("\n");
        roles.forEach(
                role -> {
                    sb.append("id:").append(role.getId()).append("\n");
                    sb.append("姓名:").append(role.getName()).append("\n");
                    CareerConfig careerConfig = StaticConfigManager.getInstance().getCareerConfigMap()
                            .get(role.getCareerId());
                    sb.append("职业:").append(careerConfig == null ? "" : careerConfig.getName()).append("\n");
                    sb.append("等级:").append(role.getLevel()).append("\n");
                    sb.append("\n");
                }
        );
        return sb.toString();
    }

    public static String buildSimplePlayerMsg(Player player){
        StringBuilder sb = new StringBuilder();
        sb.append("id:").append(player.getPlayerEntity().getId()).append("\n");
        sb.append("name:").append(player.getPlayerEntity().getName()).append("\n");
        CareerConfig careerConfig = StaticConfigManager.getInstance().getCareerConfigMap()
                .get(player.getPlayerEntity().getCareerId());
        sb.append("career:").append(careerConfig == null ? "" : careerConfig.getName()).append("\n");
        sb.append("level:").append(player.getPlayerEntity().getLevel()).append("\n");
        sb.append("HP:").append(player.getPlayerBattle().getHp()).append("/")
                .append(player.getPlayerBattle().getMaxHp()).append("\n");
        return sb.toString();
    }


    public static String buildCareerList(List<CareerConfig> careerConfigs) {
        StringBuilder sb = new StringBuilder("职业列表:").append("\n");
        careerConfigs.forEach(careerConfig -> {
            sb.append("id:").append(careerConfig.getId()).append("\n");
            sb.append("职业名称:").append(careerConfig.getName()).append("\n");
            sb.append("职业介绍:").append(careerConfig.getDesc()).append("\n");
        });
        return sb.toString();
    }

    public static String buildplayer(Player player) {
        StringBuilder sb = new StringBuilder("角色信息:").append("\n");
        // 基本信息
        sb.append("基本信息:").append("\n");
        sb.append("id:").append(player.getPlayerEntity().getId()).append("\n");
        sb.append("名称:").append(player.getPlayerEntity().getName()).append("\n");
        sb.append("等级:").append(player.getPlayerEntity().getLevel()).append("\n");
        CareerConfig careerConfig = StaticConfigManager.getInstance().getCareerConfigMap()
                .get(player.getPlayerEntity().getCareerId());
        sb.append("职业:").append(careerConfig == null ? "" : careerConfig.getName()).append("\n");
        sb.append("状态:").append(buildPlayerStateMsg(player)).append("\n");
        sb.append("金币:").append(player.getPlayerEntity().getGolds()).append("\n");
        sb.append("经验").append(player.getPlayerEntity().getExpr()).append("\n");
        sb.append("\n");
        // 战斗属性
        sb.append("战斗属性:").append("\n");
        sb.append("HP:").append(player.getPlayerBattle().getHp()).append("/")
                .append(player.getPlayerBattle().getMaxHp()).append("\n");
        sb.append("MP:").append(player.getPlayerBattle().getMp()).append("/")
                .append(player.getPlayerBattle().getMaxMp()).append("\n");
        sb.append("攻击力:").append(player.getPlayerBattle().getAttack()).append("\n");
        sb.append("防御力:").append(player.getPlayerBattle().getDefense()).append("\n");
        return sb.toString();
    }

    public static String buildPlayerStateMsg(Player player){
        if(player.getState()== PlayerState.PLAYER_LIVE){
            return "正常";
        }
        if(player.getState()== PlayerState.PLAYER_ATTACK){
            return "攻击";
        }
        if(player.getState()==PlayerState.PLAYER_TAKE_OFF){
            return "脱战";
        }
        if(player.getState()==PlayerState.PLAYER_DEAD){
            return "死亡";
        }
        return "";
    }
}
