package com.game.gameserver.module.goods.manager;

import com.game.gameserver.module.goods.dao.EquipMapper;
import com.game.gameserver.module.goods.dao.PropMapper;
import com.game.gameserver.module.goods.entity.Goods;
import com.game.gameserver.module.goods.model.EquipBag;
import com.game.gameserver.module.goods.model.PlayerBag;
import com.game.gameserver.module.player.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 物品管理器
 *
 * @author kangkang
 * */
@Component
public class GoodsManager {
    private final static Logger logger = LoggerFactory.getLogger(GoodsManager.class);

    @Autowired
    private EquipMapper equipMapper;
    @Autowired
    private PropMapper propMapper;

    /** 用户装备栏 */
    private final Map<Long,EquipBag> equipBagMap = new ConcurrentHashMap<>();
    /** 用户背包 */
    private final Map<Long,PlayerBag> playerBagMap = new ConcurrentHashMap<>();

    /**
     * 读取用户装备栏
     *
     * @param player
     * @return void
     */
    public void loadPlayerEquipBag(Player player){

    }

    /**
     * 读取用户背包
     *
     * @param player
     * @return void
     */
    public void loadPlayerProp(Player player){

    }

    public void addGoods(){

    }


}
