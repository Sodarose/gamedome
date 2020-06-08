package com.game.module.gui;

import com.game.module.player.entity.PlayerObject;
import com.game.module.player.model.Player;
import com.game.protocol.ItemProtocol;
import com.game.util.StaticData;
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

    public WordPage() {
        setBorder(BorderFactory.createLineBorder(Color.white, 3));
        setFont(new Font("宋体", Font.PLAIN, 16));
        setBackground(Color.BLACK);
        setForeground(Color.white);
        setLineWrap(true);
        setWrapStyleWord(true);
        setText(builder.toString());
    }

    public void refresh() {
        setText(builder.toString());
        validate();
        repaint();
    }

    public void print(String text) {
        builder.append(text);
        builder.append("\n");
        refresh();
    }

    public void clean() {
        builder = new StringBuilder();
        refresh();
    }

    /**
     * 展示角色选择
     *
     * @param playerList
     * @return void
     */
    public void print(List<Player> playerList) {
        builder.append("\n");
        for (Player player : playerList) {
            builder.append("角色名：").append(player.getName()).append("\t");
        }
        builder.append("\n");
        for (Player player : playerList) {
            builder.append("职业：").append(player.getCareer()).append("\t");
        }
        builder.append("\n");
        for (Player player : playerList) {
            builder.append("等级：").append(player.getLevel()).append("\t");
        }
        builder.append("\n");
        refresh();
    }

    /**
     * 打印角色信息
     *
     * @param playerObject
     * @return void
     */
    public void print(PlayerObject playerObject) {
        clean();
    /*    builder.append("姓名：\t").append(playerObject.getName()).append("\n");
        builder.append("职业：\t").append(StaticData.careerMap.get(playerObject.getCareer())).append("\n");
        builder.append("等级: \t").append(playerObject.getLevel()).append("\n");
        builder.append("\n");
        builder.append("生命值：").append(playerObject.getPropertyInfo().getHp()).append("\t");
        builder.append("魔法值：").append(playerObject.getPropertyInfo().getMp()).append("\t");
        builder.append("\n");
        builder.append("物理攻击力：").append(playerObject.getPropertyInfo().getPhyAttack()).append("\n");
        builder.append("物理防御力：").append(playerObject.getPropertyInfo().getPhyDefense()).append("\n");
        builder.append("魔法攻击力：").append(playerObject.getPropertyInfo().getMagicAttack()).append("\n");
        builder.append("魔法防御力：").append(playerObject.getPropertyInfo().getMagicDefense()).append("\n");
        builder.append("攻击速度：").append(playerObject.getPropertyInfo().getMagicDefense()).append("\n");
        builder.append("移动速度：").append(playerObject.getPropertyInfo().getMagicDefense()).append("\n");
        builder.append("\n");*/
        refresh();
    }

    /*public void print(Bag bag){
        clean();
    *//*    builder.append(bag.getName()).append("\n");
        Cell[] cells = bag.getCells();
        for(int i=0;i<cells.length;i++){
            if(i%6==0){
                builder.append("\n");
            }
            Cell cell = cells[i];
            ItemProtocol.ItemInfo itemInfo = cell.getItemInfo();
            builder.append("【").append(itemInfo==null?"空":itemInfo.getName()+":"+(itemInfo.getCount()==1?"":itemInfo.getCount()))
                    .append("】").append("\t");
        }*//*
        refresh();
    }*/

   /* public void print(EquipBar equipBar){
        for(int i = 0; i< EquipBar.MAX_EQUIP_LENGTH; i++){
            ItemProtocol.ItemInfo itemInfo = equipBar.getItemInfos()[i];
            builder.append(StaticData.equipPart.get(i)).append(":\t")
                    .append(itemInfo==null?"空":itemInfo.getName()).append("\n");
        }
        refresh();
    }*/
}
