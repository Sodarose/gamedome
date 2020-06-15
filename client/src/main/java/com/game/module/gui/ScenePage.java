package com.game.module.gui;

import com.game.context.ClientGameContext;
import com.game.module.monster.Monster;
import com.game.module.npc.Npc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

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


    public ScenePage(){
        setBorder(BorderFactory.createLineBorder(Color.white,3));
        setFont(new Font("宋体", Font.PLAIN, 16));
        setBackground(Color.BLACK);
        setForeground(Color.white);
        setLineWrap(true);
        setWrapStyleWord(true);
        setSize(1000,300);
        setMinimumSize(new Dimension(1000,300));
        setText(builder.toString());
    }

    public void update(){
        clean();
        /*Scene scene = clientGameContext.getScene();
        builder.append("场景信息:\n");
        builder.append("地图名：\t").append(scene.getName()).append("\n");
        builder.append("简介：\t").append(scene.getDescription()).append("\n");
        builder.append("\n");
        builder.append("周围：\t\t\t\t\t\t\t\t\t\t\t").append("玩家个数：").append(scene.getPlayerCount())
                .append("\n");
        builder.append("玩家：");
        for(Map.Entry<Integer, Player> entry:scene.getPlayerMap().entrySet()){
            builder.append(entry.getValue().getName()).append("\t");
        }
        builder.append("\n");
        builder.append("怪物：");
        for(Map.Entry<Integer, Monster> entry:scene.getMonsterMap().entrySet()){
            builder.append(entry.getValue().getName()).append("\t");
        }
        builder.append("\n");
        builder.append("npc：");
        for(Map.Entry<Integer, Npc> entry:scene.getNpcMap().entrySet()){
            builder.append(entry.getValue().getName()).append("\t");
        }
        builder.append("\n");
        builder.append("\n");*/
        refresh();
    }

    public void refresh(){
        setText(builder.toString());
        this.repaint();
        this.validate();
    }

    public void clean(){
        builder = new StringBuilder();
        refresh();
    }
}
