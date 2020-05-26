package com.game.module.gui;

import com.game.module.equip.entity.Equip;
import com.game.module.equip.entity.EquipBar;
import com.game.module.player.StaticData;
import com.game.module.player.entity.Player;
import com.game.module.player.model.Role;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * @author: xuewenkang
 * @date: 2020/5/13 11:47
 */
@Component
public class WordPage extends JTextArea {

    /**
     * GUI 展示内容
     */
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

    public void print(String text){
        builder.append(text);
        builder.append("\n");
        refresh();
    }

    public void clean(){
        builder = new StringBuilder();
        refresh();
    }

    /**
     * 展示角色选择
     * @param roleList
     * @return void
     */
    public void print(List<Role> roleList){
        builder.append("\n");
        for(Role role:roleList){
            builder.append("角色名：").append(role.getName()).append("\t");
        }
        builder.append("\n");
        for(Role role:roleList){
            builder.append("职业：").append(role.getCareer()).append("\t");
        }
        builder.append("\n");
        for(Role role:roleList){
            builder.append("等级：").append(role.getLevel()).append("\t");
        }
        builder.append("\n");
        refresh();
    }

    /**
     * 打印角色信息
     * @param player
     * @return void
     */
    public void print(Player player){
        builder.append("姓名：\t").append(player.getName()).append("\n");
        builder.append("职业：\t").append(StaticData.careerMap.get(player.getCareer())).append("\n");
        builder.append("等级: \t").append(player.getLevel()).append("\n");
        builder.append("\n");
        builder.append("生命值：").append(player.getProperty().getHp()).append("\t");
        builder.append("魔法值：").append(player.getProperty().getMp()).append("\t");
        builder.append("\n");
        builder.append("物理攻击力：").append(player.getProperty().getPhyAttack()).append("\n");
        builder.append("物理防御力：").append(player.getProperty().getPhyDefense()).append("\n");
        builder.append("魔法攻击力：").append(player.getProperty().getMagicAttack()).append("\n");
        builder.append("魔法防御力：").append(player.getProperty().getMagicDefense()).append("\n");
        builder.append("攻击速度：").append(player.getProperty().getMagicDefense()).append("\n");
        builder.append("移动速度：").append(player.getProperty().getMagicDefense()).append("\n");
        builder.append("\n");
        refresh();
        print(player.getEquipBar());
    }

    /**
     * 打印装备栏
     * @param equipBar
     * @return void
     */
    public void print(EquipBar equipBar){
        for(int i=0;i<EquipBar.MAX_EQUIP_LENGTH;i++){
            Equip equip = equipBar.getEquips()[i];
            builder.append(StaticData.equipPart.get(i)).append(":\t")
                    .append(equip==null?"空":equip.getName()).append("\n");
        }
        refresh();
    }

    public void print(Equip equip){

    }
}
