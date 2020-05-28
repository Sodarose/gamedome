package com.game.gameserver.module.item.entity;

import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.cooltime.entity.CoolTime;
import lombok.Data;

/**
 * 道具实体
 * @author xuewenkang
 * @date 2020/5/25 17:52
 */
@Data
public class Item implements UseAble {
    private Integer id;
    private DictItem dictItem;
    private Integer itemType;
    private Integer roleId;

    private CoolTime coolTime;
}
