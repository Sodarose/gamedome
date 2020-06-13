package com.game.gameserver.module.goods.service.impl;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.goods.dao.EquipMapper;
import com.game.gameserver.module.goods.dao.PropMapper;
import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.entity.Goods;
import com.game.gameserver.module.goods.entity.Prop;
import com.game.gameserver.module.goods.model.BagType;
import com.game.gameserver.module.goods.model.PlayerBag;
import com.game.gameserver.module.goods.service.PropService;
import com.game.gameserver.module.player.entity.PlayerBattle;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/10 16:41
 */
@Service
public class PropServiceImpl implements PropService {

    @Autowired
    private EquipMapper equipMapper;
    @Autowired
    private PropMapper propMapper;
    @Autowired
    private PlayerService playerService;

    /**
     * 根据角色Id 加载当前角色道具背包
     *
     * @param playerId
     * @return com.game.gameserver.module.goods.model.PropsBag
     */
    @Override
    public PlayerBag loadPropsBag(int playerId) {
        // 创建背包
        PlayerBag playerBag = new PlayerBag(playerId, 36);
        List<Goods> goodsList = new ArrayList<>();
        // 读取背包中的装备
        List<Equip> equipList = equipMapper.getEquipEntityList(playerId, BagType.NORMAL_BAG);
        // 读取背包中道具
        List<Prop> props = propMapper.getPropList(playerId,BagType.NORMAL_BAG);
        goodsList.addAll(equipList);
        goodsList.addAll(props);
        playerBag.initialize(goodsList);
        return playerBag;
    }

    /**
     * 使用道具
     *
     * @param playerId
     * @param propId
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Result useProp(int playerId, int propId,int count) {
        PlayerObject playerObject = playerService.getPlayerObject(playerId);
        if (playerObject == null) {
            return Result.createResult(404,"用户不存在");
        }
        PlayerBag playerBag = playerObject.getPlayerBag();
        Prop prop = playerBag.getProp(propId);
        if(prop==null){
            return Result.createResult(404,"道具不存在");
        }
        if(prop.getCount()<count){
            return Result.createResult(1,"使用数量超出");
        }
        PlayerBattle playerBattle = playerObject.getPlayerBattle();
        if(playerBattle==null){
            return Result.createResult(1,"获取战斗属性错误");
        }
        // 拿到道具计算公式
        String formula = prop.getPropConfig().getFormula();
        if(!calculateFormula(playerBattle,formula)){
            return Result.createResult(1,"获取战斗属性错误");
        }
        return Result.createResult(0,"道具使用成功");
    }


    private boolean calculateFormula(PlayerBattle playerBattle,String formula){
        return false;
    }


}
