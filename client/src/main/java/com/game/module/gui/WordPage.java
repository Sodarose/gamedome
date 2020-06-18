package com.game.module.gui;

import com.game.module.goods.GoodsType;
import com.game.module.player.PlayerInfo;
import com.game.module.player.SimplePlayerInfo;
import com.game.module.store.Commodity;
import com.game.util.StaticData;
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


    public void printStore(Map<String, Commodity> commodityMap) {
        for (Map.Entry<String, Commodity> entry : commodityMap.entrySet()) {
            Commodity commodity = entry.getValue();
            builder.append(commodity.getGoodsType() == GoodsType.EQUIP ? "装备" : "道具").append(":")
                    .append(commodity.getGoodsName()).append("\n");
            builder.append("原价:").append(commodity.getOriginalPrice()).append("\n");
            builder.append("现价:").append(commodity.getPrice()).append("\n");
            builder.append(commodity.getLimitCount() == 0 ? "" : "限购:" + "commodity.getLimitCount()").append("\n");
        }
        refresh();
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
     * @param roles 角色列表
     * @return void
     */
    public void printRoles(Map<String, SimplePlayerInfo> roles) {
        builder.append("\n");
        if (roles.size() == 0) {
            builder.append("未拥有角色！请先创建角色！\n");
            return;
        }
        for (Map.Entry<String, SimplePlayerInfo> entry : roles.entrySet()) {
            builder.append("角色名：").append(entry.getValue().getName()).append("\n");
            builder.append("职业：").append(StaticData.careerMap.get(entry.getValue().getCareerId())).append("\n");
            builder.append("等级：").append(entry.getValue().getLevel()).append("\n");
        }
        refresh();
    }

    /**
     * 打印角色信息
     *
     * @param playerInfo
     * @return void
     */
    public void printPlayerInfo(PlayerInfo playerInfo) {
        builder.append("姓名：\t").append(playerInfo.getName()).append("\n");
        builder.append("职业：\t").append(StaticData.careerMap.get(playerInfo.getCareerId())).append("\n");
        builder.append("等级: \t").append(playerInfo.getLevel()).append("\n");
        builder.append("金币: \t").append(playerInfo.getGolds()).append("\n");
        builder.append("\n");
        builder.append("生命值：").append(playerInfo.getPlayerBattle().getCurrHp()).append("/")
                .append(playerInfo.getPlayerBattle().getHp())
                .append("\t");
        builder.append("魔法值：").append(playerInfo.getPlayerBattle().getCurrMp()).append("/")
                .append(playerInfo.getPlayerBattle().getMp())
                .append("\t");
        builder.append("\n");
        builder.append("攻击力：").append(playerInfo.getPlayerBattle().getAttack()).append("\n");
        builder.append("防御力：").append(playerInfo.getPlayerBattle().getDefense()).append("\n");
        builder.append("\n");
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
