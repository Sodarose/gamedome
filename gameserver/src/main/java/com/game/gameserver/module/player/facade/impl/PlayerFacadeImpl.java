package com.game.gameserver.module.player.facade.impl;

import com.game.gameserver.module.account.facade.AccountFacade;
import com.game.gameserver.module.account.model.Account;
import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.bag.facade.BagFacade;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.equip.entity.EquipBar;
import com.game.gameserver.module.equip.facade.EquipFacade;
import com.game.gameserver.module.player.dao.PlayerMapper;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.facade.PlayerFacade;
import com.game.gameserver.module.player.factory.PlayerFactory;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerModel;
import com.game.gameserver.module.scene.facade.SceneFacade;
import com.game.gameserver.util.TransFromUtil;
import com.game.protocol.PlayerProtocol;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色接口
 * @author xuewenkang
 * @date 2020/5/25 0:10
 */
@Service
public class PlayerFacadeImpl implements PlayerFacade {

    private final static Logger logger = LoggerFactory.getLogger(PlayerFacadeImpl.class);

    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private EquipFacade equipFacade;
    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private SceneFacade sceneFacade;
    @Autowired
    private BagFacade bagFacade;

    /**
     * 返回账户上的所有角色
     * @param accountId 账户ID
     * @return java.util.List<com.game.gameserver.module.player.entity.PlayerEntity>
     */
    @Override
    public List<PlayerModel> getPlayListByAccountId(Integer accountId) {
        return playerMapper.findPlayerListByAccountId(accountId);
    }

    /**
     * 根据角色Id 登录角色
     * @param roleId 角色Id
     * @param channel 通道
     * @return com.game.gameserver.module.player.vo.PlayerVo
     */
    @Override
    public PlayerProtocol.PlayerInfo loginRoleByRoleId(Integer roleId, Channel channel) {
        Account account = channel.attr(AccountFacade.ACCOUNT_ATTRIBUTE_KEY).get();
        if(account==null){
            channel.close();
            logger.error("系统异常：账户未登录 但却进入角色登录 ");
            return null;
        }
        PlayerModel playerModel = playerMapper.getRoleByRoleId(roleId);
        if(playerModel==null){
            logger.error("该玩家没有该角色{}",roleId);
            return null;
        }
        Player player = PlayerFactory.createPlayerEntity(playerModel);

        // 加载玩家 装备
        EquipBar equipBar = equipFacade.getEquipBarByRoleId(roleId);
        equipBar.bind(player);
        player.setEquipBar(equipBar);

        // 加载玩家 背包
        Bag bag = bagFacade.getBagByRoleId(roleId,0);
        bag.bind(player);
        player.setBag(bag);


        // 加载玩家 技能栏
        player.setAccount(account);
        player.setChannel(channel);
        // 将玩家放入管理器
        playerManager.putPlayer(player);
        // 将玩家放入场景
        sceneFacade.entrySceneById(player.getId(), player.getSceneId());
        // 返回Player VO类
        channel.attr(PLAYER_ENTITY_ATTRIBUTE_KEY).compareAndSet(null, player);
        return TransFromUtil.playerTransFromPlayerProtocolPlayerInfo(player);
    }


}
