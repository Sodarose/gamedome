package com.game.gameserver.module.player.service;

import com.game.gameserver.module.item.entity.BagEntity;
import com.game.gameserver.module.item.entity.EquipBarEntity;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.player.dao.PlayerMapper;
import com.game.gameserver.module.player.entity.PropertyEntity;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.module.scene.service.SceneService;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.player.PlayerCmd;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.util.MessageUtil;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/25 10:35
 */
@Component
public class PlayerService {
    public final static AttributeKey<PlayerObject> PLAYER_ENTITY_ATTRIBUTE_KEY = AttributeKey.newInstance("PLAYER_ENTITY_ATTRIBUTE_KEY");

    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private ItemService itemService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private PlayerManager playerManager;

    /**
     * 返回账户角色列表
     * @param accountId
     * @return List<PlayerProtocol.SimplePlayerInfo> 角色信息
     * */
    public List<PlayerProtocol.SimplePlayerInfo> getPlayerList(Integer accountId){
        List<PlayerEntity> playerEntities = playerMapper.findPlayerListByAccountId(accountId);
        List<PlayerProtocol.SimplePlayerInfo> playerInfos = new ArrayList<>();
        for(PlayerEntity playerEntity : playerEntities){
            playerInfos.add(createSimplePlayerInfo(playerEntity));
        }
        return playerInfos;
    }

    /**
     * 登录角色
     * @param playerId 角色ID
     * @param channel 角色连接数据
     * @return void
     * */
    public void login(Integer playerId, Channel channel) {
        // 获得角色数据库信息
        PlayerEntity playerEntity = playerMapper.findPlayerByPlayerId(playerId);
        // 创建角色对象 并加载数据
        PlayerObject playerObject = new PlayerObject();
        // 基本信息
        playerObject.setPlayerEntity(playerEntity);
        playerObject.setChannel(channel);
        // 加载装备
        EquipBarEntity equipBarEntity = itemService.loadEquipBar(playerId);
        playerObject.setEquipBarEntity(equipBarEntity);
        // 加载背包
        BagEntity bagEntity = itemService.loadBagItem(playerId);
        playerObject.setBagEntity(bagEntity);
        // 初始化基础属性
        initProperty(playerObject);
        // 放入管理器
        playerManager.addPlayerObject(playerObject);
        // 进入场景
        sceneService.entryScene(playerObject,playerObject.getPlayerEntity().getSceneId());
        // 发送角色数据
        PlayerProtocol.PlayerInfo playerInfo =  playerObject.getPlayerInfo();
        Message syncMsg = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.LOGIN_ROLE,
                playerInfo.toByteArray());
        playerObject.getChannel().writeAndFlush(syncMsg);
    }

    /**
     * 初始化属性
     * */
    private void initProperty(PlayerObject playerObject){
        int level = playerObject.getPlayerEntity().getLevel();
        int career = playerObject.getPlayerEntity().getCareer();
        PropertyEntity propertyEntity = null;
        EquipBarEntity equipBarEntity = playerObject.getEquipBarEntity();
        Item[] items = equipBarEntity.getItems();
        for(Item item:items){
            if(item==null){
                continue;
            }
            propertyEntity.addEquipProperty(item);
        }
        playerObject.setPropertyEntity(propertyEntity);
    }

    private PlayerProtocol.SimplePlayerInfo createSimplePlayerInfo(PlayerEntity playerEntity){
        PlayerProtocol.SimplePlayerInfo.Builder builder = PlayerProtocol.SimplePlayerInfo.newBuilder();
        builder.setId(playerEntity.getId());
        builder.setName(playerEntity.getName());
        builder.setCareer(playerEntity.getCareer());
        builder.setLevel(playerEntity.getLevel());
        return builder.build();
    }

}
