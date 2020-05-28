package com.game.gameserver.util;

import com.game.gameserver.context.ServerContext;
import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.bag.entity.Cell;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerModel;
import com.game.protocol.BagProtocol;
import com.game.protocol.EquipProtocol;
import com.game.protocol.ItemProtocol;
import com.game.protocol.PlayerProtocol;
import org.springframework.context.ApplicationContext;

/**
 * 转换工具
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class TransFromUtil {
    public static PlayerProtocol.RoleInfo roleTransFromPlayerProtocolRoleInfo(PlayerModel playerModel){
        ApplicationContext applicationContext = ServerContext.getApplication();
        DictionaryManager dictionaryManager = applicationContext.getBean(DictionaryManager.class);
        PlayerProtocol.RoleInfo.Builder builder = PlayerProtocol.RoleInfo.newBuilder();
        builder.setId(playerModel.getId());
        builder.setName(playerModel.getName());
        builder.setLevel(playerModel.getLevel());
        builder.setCareer(dictionaryManager.getRoleCareerName(playerModel.getId()));
        return builder.build();
    }

    public static PlayerProtocol.PlayerInfo playerTransFromPlayerProtocolPlayerInfo(Player player){
        PlayerProtocol.PlayerInfo.Builder builder = PlayerProtocol.PlayerInfo.newBuilder();
        builder.setId(player.getId());
        builder.setName(player.getName());
        builder.setLevel(player.getLevel());
        builder.setCareer(player.getCareer());
        builder.setSceneId(player.getSceneId());

        // 属性
        PlayerProtocol.PropertyInfo.Builder propertyBuilder = PlayerProtocol.PropertyInfo.newBuilder();
        propertyBuilder.setHp(player.getProperty().getHp());
        propertyBuilder.setMp(player.getProperty().getMagicAttack());
        propertyBuilder.setPhyAttack(player.getProperty().getPhyAttack());
        propertyBuilder.setMagicAttack(player.getProperty().getMagicAttack());
        propertyBuilder.setPhyDefense(player.getProperty().getPhyDefense());
        propertyBuilder.setMagicDefense(player.getProperty().getMagicDefense());
        builder.setProperty(propertyBuilder.build());

        // 装备
        for(Equip equip : player.getEquipBar().getEquipEntities()){
            if(equip ==null){
                continue;
            }
            builder.addEquip(equipTransFromEquipProtocolEquipInfo(equip));
        }

        return builder.build();
    }

    public static EquipProtocol.EquipInfo equipTransFromEquipProtocolEquipInfo(Equip equip){
        EquipProtocol.EquipInfo.Builder builder = EquipProtocol.EquipInfo.newBuilder();
        builder.setId(equip.getId());
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
        return null;
    }

    public static BagProtocol.CellInfo cellTransFromBafProtocolCellInfo(Cell cell){
        BagProtocol.CellInfo.Builder builder = BagProtocol.CellInfo.newBuilder();
        builder.setBagIndex(cell.getBagIndex());
        builder.setItemId(cell.getItem().getId());
        builder.setItemName(cell.getItem().getDictItem().getName());
        builder.setCount(cell.getCount());
        return builder.build();
    }

    public static ItemProtocol.ItemInfo  itemTransFromItemProtocolItemInfo(Item item){
        ItemProtocol.ItemInfo.Builder builder = ItemProtocol.ItemInfo.newBuilder();

        return builder.build();
    }
}
