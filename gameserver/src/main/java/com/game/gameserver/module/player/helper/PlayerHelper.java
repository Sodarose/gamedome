package com.game.gameserver.module.player.helper;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.StaticConfigManager;
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

    public static String buildSimplePlayerMsg(Player playerDomain){
        StringBuilder sb = new StringBuilder();
        sb.append("id:").append(playerDomain.getPlayerEntity().getId()).append("\n");
        sb.append("name:").append(playerDomain.getPlayerEntity().getName()).append("\n");
        CareerConfig careerConfig = StaticConfigManager.getInstance().getCareerConfigMap()
                .get(playerDomain.getPlayerEntity().getCareerId());
        sb.append("career:").append(careerConfig == null ? "" : careerConfig.getName()).append("\n");
        sb.append("level:").append(playerDomain.getPlayerEntity().getLevel()).append("\n");
        sb.append("HP:").append(playerDomain.getPlayerBattle().getCurrHp()).append("/")
                .append(playerDomain.getPlayerBattle().getHp()).append("\n");
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

    public static String buildPlayerDomain(Player playerDomain) {
        StringBuilder sb = new StringBuilder("角色信息:").append("\n");
        // 基本信息
        sb.append("基本信息:").append("\n");
        sb.append("id:").append(playerDomain.getPlayerEntity().getId()).append("\n");
        sb.append("名称:").append(playerDomain.getPlayerEntity().getName()).append("\n");
        sb.append("等级:").append(playerDomain.getPlayerEntity().getLevel()).append("\n");
        CareerConfig careerConfig = StaticConfigManager.getInstance().getCareerConfigMap()
                .get(playerDomain.getPlayerEntity().getCareerId());
        sb.append("职业").append(careerConfig == null ? "" : careerConfig.getName()).append("\n");
        sb.append("金币:").append(playerDomain.getPlayerEntity().getGolds()).append("\n");
        sb.append("经验").append(playerDomain.getPlayerEntity().getExpr()).append("\n");
        sb.append("\n");
        // 战斗属性
        sb.append("战斗属性:").append("\n");
        sb.append("HP:").append(playerDomain.getPlayerBattle().getCurrHp()).append("/")
                .append(playerDomain.getPlayerBattle().getHp()).append("\n");
        sb.append("MP:").append(playerDomain.getPlayerBattle().getCurrMp()).append("/")
                .append(playerDomain.getPlayerBattle().getMp()).append("\n");
        sb.append("攻击力:").append(playerDomain.getPlayerBattle().getAttack()).append("\n");
        sb.append("防御力:").append(playerDomain.getPlayerBattle().getDefense()).append("\n");
        return sb.toString();
    }
}
