package com.game.util;
import com.game.module.bag.entity.Bag;
import com.game.module.bag.entity.Cell;
import com.game.module.equip.entity.Equip;
import com.game.module.equip.entity.EquipBar;
import com.game.module.player.entity.Player;
import com.game.module.player.model.Property;
import com.game.module.player.model.Role;
import com.game.protocol.BagProtocol;
import com.game.protocol.EquipProtocol;
import com.game.protocol.PlayerProtocol;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 转换工具
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class TransFromUtil {
    /*public static PlayerProtocol.RoleInfo roleTransFromPlayerProtocolRoleInfo(Role role){
        PlayerProtocol.RoleInfo.Builder builder = PlayerProtocol.RoleInfo.newBuilder();
        builder.setId(role.getId());
        builder.setName(role.getName());
        builder.setLevel(role.getLevel());
        builder.setCareer(role.getCareer());
        return builder.build();
    }
    */

    public static Role playerProtocolRoleInfoTransFromRole(PlayerProtocol.RoleInfo roleInfo){
        Role role = new Role();
        role.setId(roleInfo.getId());
        role.setName(roleInfo.getName());
        role.setLevel(roleInfo.getLevel());
        role.setCareer(roleInfo.getCareer());
        return role;
    }

    public static Player playerProtocolPlayerInfoTransFromPlayer(PlayerProtocol.PlayerInfo playerInfo){
        Player player = new Player();
        Property property = new Property();
        EquipBar equipBar = new EquipBar();
        List<Equip> equips = new ArrayList<>();

        // 基础属性
        player.setId(playerInfo.getId());
        player.setName(playerInfo.getName());
        player.setCareer(playerInfo.getCareer());
        player.setLevel(playerInfo.getLevel());
        player.setSceneId(player.getSceneId());

        // 战斗属性
        property.setHp(playerInfo.getProperty().getHp());
        property.setMp(playerInfo.getProperty().getMp());
        property.setPhyAttack(playerInfo.getProperty().getPhyAttack());
        property.setMagicAttack(playerInfo.getProperty().getMagicAttack());
        property.setPhyDefense(playerInfo.getProperty().getPhyDefense());
        property.setMagicDefense(playerInfo.getProperty().getMagicDefense());
        property.setAttackSpeed(playerInfo.getProperty().getAttackSpeed());
        property.setMoveSpeed(playerInfo.getProperty().getMoveSpeed());
        player.setProperty(property);

        //装备栏
        for(EquipProtocol.EquipInfo equipInfo:playerInfo.getEquipList()){
            equips.add(equipProtocolEquipInfoTransFromEquip(equipInfo));
        }
        equipBar.init(equips);
        equipBar.bind(player);
        player.setEquipBar(equipBar);
        return player;
    }

    public static Equip equipProtocolEquipInfoTransFromEquip(EquipProtocol.EquipInfo equipInfo){
        Equip equip = new Equip();
        equip.setId(equipInfo.getId());
        equip.setName(equipInfo.getName());
        equip.setLevel(equipInfo.getLevel());
        equip.setQuality(equipInfo.getQuality());
        equip.setPart(equipInfo.getPart());
        equip.setDurability(equipInfo.getDurability());
        equip.setMaxDurability(equipInfo.getMaxDurability());
        equip.setHp(equipInfo.getHp());
        equip.setMp(equipInfo.getMp());
        equip.setPhyAttack(equipInfo.getPhyAttack());
        equip.setPhyDefense(equipInfo.getPhyDefense());
        equip.setMagicAttack(equipInfo.getMagicAttack());
        equip.setMagicDefense(equipInfo.getMagicDefense());
        equip.setAttackSpeed(equipInfo.getAttackSpeed());
        equip.setMoveSpeed(equipInfo.getMoveSpeed());
        return equip;
    }

    public static Bag bagProtocolBagInfoTransFromBag(BagProtocol.BagInfo bagInfo){
        Bag bag = new Bag();
        bag.setId(bagInfo.getBagId());
        bag.setName(bagInfo.getBagName());
        List<Cell> cells = new ArrayList<>();
        for(BagProtocol.CellInfo cellInfo:bagInfo.getCellInfoList()){
            cells.add(bagProtocolCellInfoTransFromCell(cellInfo));
        }
        bag.init(cells);
        return bag;
    }

    public static Cell bagProtocolCellInfoTransFromCell(BagProtocol.CellInfo cellInfo){
        Cell cell = new Cell();
        cell.setBagIndex(cellInfo.getBagIndex());
        cell.setItemId(cellInfo.getItemId());
        cell.setItemName(cellInfo.getItemName());
        cell.setCount(cellInfo.getCount());
        return cell;
    }
}
