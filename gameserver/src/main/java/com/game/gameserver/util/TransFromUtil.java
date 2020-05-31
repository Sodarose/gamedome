package com.game.gameserver.util;

import com.game.gameserver.context.ServerContext;
import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.module.item.entity.Bag;
import com.game.gameserver.module.item.entity.Cell;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.module.player.model.Player;
import com.game.protocol.BagProtocol;
import com.game.protocol.EquipProtocol;
import com.game.protocol.PlayerProtocol;
import org.springframework.context.ApplicationContext;

/**
 * 转换工具
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class TransFromUtil {
    public static PlayerProtocol.RoleInfo roleTransFromPlayerProtocolRoleInfo(Player player){
        ApplicationContext applicationContext = ServerContext.getApplication();
        DictionaryManager dictionaryManager = applicationContext.getBean(DictionaryManager.class);
        PlayerProtocol.RoleInfo.Builder builder = PlayerProtocol.RoleInfo.newBuilder();
        builder.setId(player.getId());
        builder.setName(player.getName());
        builder.setLevel(player.getLevel());
        builder.setCareer(dictionaryManager.getRoleCareerName(player.getId()));
        return builder.build();
    }

    public static PlayerProtocol.PlayerInfo playerTransFromPlayerProtocolPlayerInfo(PlayerObject playerObject){
        PlayerProtocol.PlayerInfo.Builder builder = PlayerProtocol.PlayerInfo.newBuilder();
        builder.setId(playerObject.getId());
        builder.setName(playerObject.getName());
        builder.setLevel(playerObject.getLevel());
        builder.setCareer(playerObject.getCareer());
        builder.setSceneId(playerObject.getSceneId());

        // 属性
        PlayerProtocol.PropertyInfo.Builder propertyBuilder = PlayerProtocol.PropertyInfo.newBuilder();
        propertyBuilder.setHp(playerObject.getProperty().getHp());
        propertyBuilder.setMp(playerObject.getProperty().getMagicAttack());
        propertyBuilder.setPhyAttack(playerObject.getProperty().getPhyAttack());
        propertyBuilder.setMagicAttack(playerObject.getProperty().getMagicAttack());
        propertyBuilder.setPhyDefense(playerObject.getProperty().getPhyDefense());
        propertyBuilder.setMagicDefense(playerObject.getProperty().getMagicDefense());
        builder.setProperty(propertyBuilder.build());

        // 装备
        for(Equip equip : playerObject.getEquipBar().getEquipEntities()){
            if(equip ==null){
                continue;
            }
            builder.addEquip(equipTransFromEquipProtocolEquipInfo(equip));
        }

        return builder.build();
    }

    public static EquipProtocol.EquipInfo equipTransFromEquipProtocolEquipInfo(Equip equip){
        EquipProtocol.EquipInfo.Builder builder = EquipProtocol.EquipInfo.newBuilder();
        builder.setName(equip.getDictItem().getName());
        builder.setLevel(equip.getDictItem().getLevel());
        builder.setQuality(equip.getDictItem().getQuality());
        builder.setPart(equip.getDictEquip().getPart());
        builder.setDurability(equip.getDurability());
        builder.setMaxDurability(equip.getDictEquip().getMaxDurability());
        builder.setHp(equip.getDictEquip().getHp());
        builder.setMp(equip.getDictEquip().getMp());
        builder.setPhyAttack(equip.getDictEquip().getPhyAttack());
        builder.setMagicAttack(equip.getDictEquip().getMagicAttack());
        builder.setPhyDefense(equip.getDictEquip().getPhyDefense());
        builder.setMagicDefense(equip.getDictEquip().getMagicDefense());
        builder.setAttackSpeed(equip.getDictEquip().getAttackSpeed());
        builder.setMoveSpeed(equip.getDictEquip().getMoveSpeed());
        return builder.build();
    }

    public static BagProtocol.BagInfo bagTransFromBagProtocolBagInfo(Bag bag){
        BagProtocol.BagInfo.Builder bagBuilder = BagProtocol.BagInfo.newBuilder();
        bagBuilder.setBagId(bag.getId());
        bagBuilder.setBagName(bag.getName());
        for(Cell cell:bag.getNotNullCellList()){
            bagBuilder.addCellInfo(cellTransFromBagProtocolCellInfo(cell));
        }
        return bagBuilder.build();
    }

    public static BagProtocol.CellInfo cellTransFromBagProtocolCellInfo(Cell cell){
        BagProtocol.CellInfo.Builder builder = BagProtocol.CellInfo.newBuilder();
        builder.setId(cell.getItem().getId());
        builder.setItemType(cell.getItem().getItemType());
        builder.setItemName(cell.getItem().getDictItem().getName());
        builder.setItemCount(cell.getItem().getItemCount());
        builder.setBagIndex(cell.getItem().getBagIndex());
        return builder.build();
    }

}
