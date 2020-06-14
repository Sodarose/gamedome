package com.game.gameserver.module.goods.manager;

import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.entity.Prop;
import com.game.gameserver.module.goods.model.EquipContainer;
import com.game.gameserver.module.goods.model.PropContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 物品管理器
 * */
@Component
public class GoodsManager {
    private final static Logger logger = LoggerFactory.getLogger(GoodsManager.class);

    /** 角色装备列表 */
    private final static Map<Integer, EquipContainer> playerEquipMap = new ConcurrentHashMap<>(16);
    /** 角色道具列表 */
    private final static Map<Integer, PropContainer> playerPropMap = new ConcurrentHashMap<>(16);

    public static GoodsManager instance;

    public GoodsManager(){
        instance = this;
    }


    /** 数据存档 */
    public void save(){

    }
}
