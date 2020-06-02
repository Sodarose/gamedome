package com.game.gameserver.module.player.object;

import com.game.gameserver.module.account.model.Account;
import com.game.gameserver.module.item.entity.BagEntity;
import com.game.gameserver.module.item.entity.EquipBarEntity;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.player.entity.PropertyEntity;
import com.game.protocol.ItemProtocol;
import com.game.protocol.PlayerProtocol;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * 玩家角色实体
 * @author xuewenkang
 * @date 2020/5/25 0:10
 */
@Data
public class PlayerObject {

    /** 角色基本属性 */
    PlayerEntity playerEntity;
    /** 可变的 角色属性 */
    private PropertyEntity propertyEntity;
    /** account */
    private Account account;
    /** channel */
    private Channel channel;
    /** 装备栏*/
    private EquipBarEntity equipBarEntity;
    /** 背包 */
    private BagEntity bagEntity;

    /**
     * 得到玩家同步数据
     * */
    public PlayerProtocol.PlayerInfo getPlayerInfo(){
        PlayerProtocol.PlayerInfo.Builder builder = PlayerProtocol.PlayerInfo.newBuilder();
        builder.setId(playerEntity.getId());
        builder.setLevel(playerEntity.getLevel());
        builder.setName(playerEntity.getName());
        builder.setCareer(playerEntity.getCareer());
        builder.setSceneId(playerEntity.getSceneId());
        PlayerProtocol.PropertyInfo propertyInfo = propertyEntity.getProperInfo();
        ItemProtocol.EquipBarInfo equipBarInfo = equipBarEntity.getEquipBarInfo();
        ItemProtocol.BagInfo bagInfo = bagEntity.getBagInfo();

        builder.setPropertyInfo(propertyInfo);
        builder.setEquipBar(equipBarInfo);
        builder.setBagInfo(bagInfo);
        return  builder.build();
    }


}
