package com.game.gameserver.module.goods.manager;

import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.entity.Goods;
import com.game.gameserver.module.goods.type.GoodsType;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.GameUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品管理器
 *
 * @author kangkang
 */
@Component
public class GoodsManager {
    private final static Logger logger = LoggerFactory.getLogger(GoodsManager.class);


    /**
     * 读取用户装备栏
     *
     * @param player
     * @return void
     */
    public void loadPlayerEquip(Player player) {

    }

    /**
     * 读取用户背包
     *
     * @param player
     * @return void
     */
    public void loadPlayerBag(Player player) {

    }

    /**
     * 创建物品
     *
     * @param playerObject 用户
     * @param goodsType    物品类型
     * @param goodsId      物品Id
     * @return List<Goods>
     */
    public List<Goods> createGoods(PlayerObject playerObject, int goodsType, int goodsId, int num) {
        List<Goods> goods = new ArrayList<>();
        if (GoodsType.EQUIP == goodsType) {
            Equip equip = new Equip();
            equip.setId(GameUUID.getInstance().generate());
            equip.setPlayerId(playerObject.getPlayer().getId());

        }

        if (GoodsType.PROP == goodsType) {

        }

        return goods;
    }

    /**
     * 添加物品
     *
     * @param playerObject
     * @param goodsType
     * @param goodsId
     * @param num
     * @return boolean
     */
    public boolean addGoods(PlayerObject playerObject, int goodsType, int goodsId, int num){
        return false;
    }

    /**
     * 判断背包是否有足够空间放入某件物品
     *
     * @param playerObject
     * @param goodsType
     * @param goodsId
     * @param num
     * @return boolean
     */
    public boolean hasSpace(PlayerObject playerObject, int goodsType, int goodsId, int num) {
        return false;
    }
}
