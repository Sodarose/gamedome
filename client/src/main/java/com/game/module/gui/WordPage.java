package com.game.module.gui;

import com.game.module.item.ItemType;
import com.game.module.player.PlayerInfo;
import com.game.module.player.SimplePlayerInfo;
import com.game.module.store.Commodity;
import com.game.protocol.*;
import com.game.util.StaticData;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;
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

    public void printPlayerBag(ItemProtocol.PlayerBag playerBag) {
        ItemProtocol.ItemInfo[] bag = new ItemProtocol.ItemInfo[36];
        for (ItemProtocol.ItemInfo itemInfo : playerBag.getItemListList()) {
            bag[itemInfo.getBagIndex()] = itemInfo;
        }
        for (int i = 0; i < bag.length; i++) {
            ItemProtocol.ItemInfo itemInfo = bag[i];
            builder.append("[").append(itemInfo == null ? "空" : itemInfo.getItemName() + ":" + itemInfo.getItemNum())
                    .append("]").append("\t");
            if (i != 0 && i % 6 == 0) {
                builder.append("\n");
            }
        }
        refresh();
    }


    public void printEmailList(List<EmailProtocol.EmailInfo> emailInfos) {
        if (emailInfos == null || emailInfos.size() == 0) {
            builder.append("邮箱为空");
        } else {
            for (EmailProtocol.EmailInfo info : emailInfos) {
                builder.append("id:").append(info.getId()).append("\n");
                builder.append("标题:").append(info.getTitle()).append("\n");
                builder.append("发送者:").append(info.getSendName()).append("\n");
                builder.append("内容:").append(info.getContent()).append("\n");
                builder.append("附件");
                for (String itemName : info.getAttachmentsList()) {
                    builder.append(itemName).append("\t");
                }
                builder.append("\n");
                builder.append("金币:").append(info.getGolds()).append("\n");
                builder.append("状态:").append(info.getState() == 1 ? "以读" : "未读").append("\n");
            }
        }
        refresh();
    }


    public void printInstanceInfo(List<InstanceProtocol.InstanceConfigInfo> instanceInfos) {
        for (InstanceProtocol.InstanceConfigInfo info : instanceInfos) {
            builder.append("副本名称:").append(info.getInstanceName()).append("\n");
            builder.append("副本类型:").append("闯关类型").append("\n");
            builder.append("副本难度:").append(info.getDiff()).append("\n");
            builder.append("开放时间:").append("全天开放").append("\n");
            builder.append("限制时间:").append(info.getLimitTime()).append("\n");
            builder.append("需要组队:").append(info.getNeedTeam()).append("\n");
            builder.append("等级限制:").append(info.getMaxNum()).append("\n");
            builder.append("经验奖励:").append(info.getExprAward()).append("\n");
            builder.append("金币奖励:").append(info.getGoldAward()).append("\n");
            builder.append("装备奖励:").append(info.getEquipAwardList().toString()).append("\n");
            builder.append("道具奖励:").append(info.getPropAwardList().toString()).append("\n");
            builder.append("\n");
        }
        refresh();
    }


    public void printStore(Map<String, Commodity> commodityMap) {
        for (Map.Entry<String, Commodity> entry : commodityMap.entrySet()) {
            Commodity commodity = entry.getValue();
            builder.append(commodity.getGoodsType() == ItemType.EQUIP ? "装备" : "道具").append(":")
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
    public void printPlayerInfo(PlayerProtocol.PlayerInfo playerInfo) {
        builder.append("姓名：\t").append(playerInfo.getName()).append("\n");
        builder.append("职业：\t").append(StaticData.careerMap.get(playerInfo.getCareerId())).append("\n");
        builder.append("等级: \t").append(playerInfo.getLevel()).append("\n");
        builder.append("状态：\t").append(playerInfo.getState()).append("\n");
        builder.append("战斗模式：\t").append(playerInfo.getFighterModel()).append("\n");
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

    public void printTeamList(List<TeamProtocol.TeamInfo> teamInfos) {
        for (TeamProtocol.TeamInfo teamInfo : teamInfos) {
            printTeamInfo(teamInfo);
        }
        refresh();
    }

    public void printTeamInfo(TeamProtocol.TeamInfo teamInfo) {
        builder.append("队伍Id:").append(teamInfo.getId()).append("\n");
        builder.append("队伍名称:").append(teamInfo.getTeamName()).append("\n");
        builder.append("队长Id:").append(teamInfo.getCaptainId()).append("\n");
        builder.append("人数:").append(teamInfo.getCurrNum()).append("/").append(teamInfo.getMaxNum()).append("\n");
        builder.append("状态:").append(teamInfo.getInstance() ? "副本中" : "待机").append("\n");
        builder.append("队员:");
        for (String name : teamInfo.getMemberNameList()) {
            builder.append(name).append("\t");
        }
        builder.append("\n");
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
    public void printMonsterList(List<Actor.MonsterInfo> infos){
        for(Actor.MonsterInfo monsterInfo:infos){
            printMonster(monsterInfo);
        }
        refresh();
    }

    public void printMonster(Actor.MonsterInfo monsterInfo) {
        builder.append("id:").append(monsterInfo.getId()).append("\n");
        builder.append("姓名:").append(monsterInfo.getName()).append("\n");
        builder.append("等级:").append(monsterInfo.getLevel()).append("\n");
        builder.append("HP:").append(monsterInfo.getCurrHp()).append("/").append(monsterInfo.getHp()).append("\n");
        builder.append("MP").append(monsterInfo.getCurrMp()).append("/").append(monsterInfo.getMp()).append("\n");
        builder.append("攻击力:").append(monsterInfo.getAttack()).append("\n");
        builder.append("防御力:").append(monsterInfo.getDefense()).append("\n");
        builder.append("\n");
        refresh();
    }
}
