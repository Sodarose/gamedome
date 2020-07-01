package com.game.module.gui;

import com.game.context.ClientGameContext;
import com.game.module.monster.Monster;
import com.game.module.npc.Npc;
import com.game.module.player.OtherPlayerInfo;
import com.game.module.scene.SceneInfo;
import com.game.protocol.Actor;
import com.game.protocol.InstanceProtocol;
import com.game.protocol.PlayerProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/6/1 9:34
 */
@Component
public class ScenePage extends JTextArea {

    @Autowired
    private ClientGameContext clientGameContext;
    /**
     * GUI 展示内容
     */
    private StringBuilder builder = new StringBuilder(16);


    public ScenePage() {
        setBorder(BorderFactory.createLineBorder(Color.white, 3));
        setFont(new Font("宋体", Font.PLAIN, 16));
        setBackground(Color.BLACK);
        setForeground(Color.white);
        setLineWrap(true);
        setWrapStyleWord(true);
        setSize(1000, 300);
        setMinimumSize(new Dimension(1000, 300));
        setText(builder.toString());
    }

    public void printSceneInfo(SceneInfo sceneInfo) {
        builder.append("场景信息:\n");
        builder.append("地图名：").append(sceneInfo.getName()).append("\n");
        builder.append("简介：").append(sceneInfo.getDescription()).append("\n");
        builder.append("\n");
        builder.append("玩家个数：").append(sceneInfo.getPlayerCount())
                .append("\n");
        builder.append("周围玩家：");
        for (Map.Entry<Long, OtherPlayerInfo> entry : sceneInfo.getPlayerMap().entrySet()) {
            // 打印名字和
            builder.append(entry.getValue().getName())
                    .append("(").append(entry.getValue().getPlayerBattle().getCurrHp()).append(")")
                    .append("\t");
        }
        builder.append("\n");
        builder.append("周围怪物：");
        for (Map.Entry<Long, Monster> entry : sceneInfo.getMonsterMap().entrySet()) {
            builder.append(entry.getValue().getName())
                    .append("(").append(entry.getValue().getCurrHp()).append(")")
                    .append("\t");
        }
        builder.append("\n");
        builder.append("周围npc：");
        for (Map.Entry<Long, Npc> entry : sceneInfo.getNpcMap().entrySet()) {
            builder.append(entry.getValue().getName()).append("\t");
        }
        builder.append("\n");
        refresh();
    }

    public void print(InstanceProtocol.InstanceInfo instanceInfo) {
        builder.append("场景信息:\n");
        builder.append("地图名：").append(instanceInfo.getName()).append("\n");
        builder.append("简介：").append(instanceInfo.getDescription()).append("\n");
        Long currTime = System.currentTimeMillis();
        Long endTime = instanceInfo.getEndTime();
        if (endTime >= currTime) {
            builder.append("剩余时间：").append(TimeUnit.SECONDS.convert(
                    endTime - currTime, TimeUnit.MILLISECONDS))
                    .append("\n");
        }
        Long rTime = instanceInfo.getRecoveryTime();
        if (rTime >= currTime) {
            builder.append("强制退出时间：").append(TimeUnit.SECONDS.convert(
                    rTime - currTime, TimeUnit.MILLISECONDS)).append("\n");
        }
        builder.append("\n");
        builder.append("周围玩家：");
        for (PlayerProtocol.OtherPlayerInfo playerInfo : instanceInfo.getPlayerListList()) {
            builder.append(playerInfo.getName()).append("(").append(playerInfo.getPlayerBattle().getCurrHp()).append(")").append("\t");
        }
        builder.append("\n");
        builder.append("周围怪物：");
        for (Actor.MonsterInfo monsterInfo : instanceInfo.getMonsterListList()) {
            builder.append(monsterInfo.getName()).append("(").append(monsterInfo.getCurrHp()).append(")").append("\t");
        }
        builder.append("\n");
        refresh();
    }

    public void refresh() {
        setText(builder.toString());
        this.repaint();
        this.validate();
    }

    public void clean() {
        builder = new StringBuilder();
        refresh();
    }
}
