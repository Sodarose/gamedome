package com.game.game.page;

import com.game.config.RoleStatus;
import com.game.game.context.GameContext;
import com.game.protocol.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author: xuewenkang
 * @date: 2020/5/13 11:47
 */
@Component
public class WordPage extends JTextArea {


    @Autowired
    private GameContext gameContext;

    private StringBuilder builder = new StringBuilder(16);

    public WordPage(){
        setBorder(BorderFactory.createLineBorder(Color.white,3));
        setFont(new Font("宋体", Font.PLAIN, 16));
        setBackground(Color.BLACK);
        setForeground(Color.white);
        setText(builder.toString());
    }

    public void refresh(){
        setText(builder.toString());
        validate();
        repaint();
    }

    /**
     * description: 打印场景信息
     *
     * @param scene 场景
     * @return void
     */
    public void print(Protocol.Scene scene){
        String title = "场景\n";
        String map = "------"+scene.getMap().getName()+"-------"+"\n";
        String mapDes = scene.getMap().getDescription()+"\n\n";
        StringBuilder rolesBuilder = new StringBuilder("周围玩家:");
        StringBuilder npcsBuilder = new StringBuilder("周围NPC:");
        StringBuilder monstersBuilder = new StringBuilder("周围怪物:");
        StringBuilder waysBuilder = new StringBuilder("地图:");
        int i = 0;
        for(Map.Entry<Integer,Protocol.Role> entry:scene.getRolesMap().entrySet()){
            if(i==5){
                rolesBuilder.append("\n");
            }
            rolesBuilder.append(entry.getValue().getName()+"\t");
            i++;
        }
        rolesBuilder.append("\n");
        i = 0;
        for(Map.Entry<Integer,Protocol.Npc> entry:scene.getNpcMap().entrySet()){
            if(i==5){
                npcsBuilder.append("\n");
            }
            npcsBuilder.append(entry.getValue().getName()+"\t");
            i++;
        }
        npcsBuilder.append("\n");
        i = 0;
        for(Map.Entry<Integer,Protocol.Monster> entry:scene.getMonsterMap().entrySet()){
            if(i==5){
                monstersBuilder.append("\n");
            }
            monstersBuilder.append(entry.getValue().getName()+"\t");
            i++;
        }
        monstersBuilder.append("\n");
        i = 0;
        for(Protocol.Map way:scene.getMap().getWaysList()){
            if(i==5){
                waysBuilder.append("\n");
            }
            waysBuilder.append(way.getName()+"\t");
            i++;
        }
        waysBuilder.append("\n");
        i = 0;
        builder.append(title)
                .append(map)
                .append(mapDes)
                .append(rolesBuilder)
                .append(npcsBuilder)
                .append(monstersBuilder)
                .append(waysBuilder);
        builder.append("\n");
        refresh();
    }

    public void print(Protocol.Role role){
        StringBuilder roleBuilder = new StringBuilder();
        roleBuilder
                .append("角色\n")
                .append("名称:\t"+role.getName()+"\n")
                .append("生命值:\t"+role.getPh()+"\n")
                .append("法力值:\t"+role.getMp()+"\n")
                .append("物理攻击力:\t"+role.getPhyAttack()+"\n")
                .append("魔法攻击力:\t"+role.getMagicAttack()+"\n")
                .append("物理防御:\t"+role.getPhyDefense()+"\n")
                .append("魔法防御:\t"+role.getMagicAttack()+"\n")
                .append("状态:\t"+(RoleStatus.ROLE_LIVE.equals(role.getStatus())?"存活":"死亡")+"\n");
        builder.append(roleBuilder);
        builder.append("\n");
        refresh();
    }

    public void print(Protocol.Map map){
        StringBuilder mapBuilder = new StringBuilder();
        mapBuilder.append("地图:"+map.getName()+"\n")
                .append("简介:"+map.getDescription()+"\n");
        builder.append(mapBuilder);
        builder.append("\n");
        refresh();
    }

    public void print(Protocol.Monster monster){
        StringBuilder monsterBuilder = new StringBuilder();
        monsterBuilder
                .append("怪物\n")
                .append("名称:\t"+monster.getName()+"\n")
                .append("生命值:\t"+monster.getPh()+"\n")
                .append("法力值:\t"+monster.getMp()+"\n")
                .append("物理攻击力:\t"+monster.getPhyAttack()+"\n")
                .append("魔法攻击力:\t"+monster.getMagicAttack()+"\n")
                .append("物理防御:\t"+monster.getPhyDefense()+"\n")
                .append("魔法防御:\t"+monster.getMagicAttack()+"\n")
                .append("状态:\t"+(RoleStatus.ROLE_LIVE.equals(monster.getStatus())?"存活":"死亡")+"\n");
        builder.append(monsterBuilder);
        builder.append("\n");
        refresh();
    }

    public void print(Protocol.Npc npc){
        StringBuilder npcBuilder = new StringBuilder();
        npcBuilder
                .append("NPC\n")
                .append("名称:\t"+npc.getName()+"\n")
                .append("生命值:\t"+npc.getPh()+"\n")
                .append("法力值:\t"+npc.getMp()+"\n")
                .append("物理攻击力:\t"+npc.getPhyAttack()+"\n")
                .append("魔法攻击力:\t"+npc.getMagicAttack()+"\n")
                .append("物理防御:\t"+npc.getPhyDefense()+"\n")
                .append("魔法防御:\t"+npc.getMagicAttack()+"\n")
                .append("状态:\t"+(RoleStatus.ROLE_LIVE.equals(npc.getStatus())?"存活":"死亡")+"\n");
        builder.append(npcBuilder);
        builder.append("\n");
        refresh();
    }

    public void print(String text){
        builder.append(text+"\n");
        builder.append("\n");
        refresh();
    }

    public void clean(){
        builder = new StringBuilder();
        refresh();
    }
}
