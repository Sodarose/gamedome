package com.game.gameserver.module.player.object;

import com.game.gameserver.dictionary.dict.DictRoleLevelProperty;
import com.game.gameserver.module.account.model.Account;
import com.game.gameserver.module.item.entity.Bag;
import com.game.gameserver.module.item.entity.EquipBar;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.model.Property;
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
    Player player;
    /** 基础等级属性 */
    private DictRoleLevelProperty levelProperty;
    /** 可变的 角色属性 */
    private Property property;
    /** account */
    private Account account;
    /** channel */
    private Channel channel;
    /** 装备栏*/
    private EquipBar equipBar;
    /** 背包 */
    private Bag bag;


}
