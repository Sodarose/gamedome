package com.game.gameserver.module.player.service;

import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.dictionary.dict.DictRoleLevelProperty;
import com.game.gameserver.module.item.entity.Bag;
import com.game.gameserver.module.item.entity.EquipBar;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.player.dao.PlayerMapper;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.model.Property;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.module.scene.service.SceneService;
import com.game.protocol.PlayerProtocol;
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
    @Autowired
    private DictionaryManager dictionaryManager;


    /**
     * 返回账户角色列表
     * @param accountId
     * @return List<PlayerProtocol.SimplePlayerInfo> 角色信息
     * */
    public List<PlayerProtocol.SimplePlayerInfo> getPlayerList(Integer accountId){
        List<Player> players = playerMapper.findPlayerListByAccountId(accountId);
        List<PlayerProtocol.SimplePlayerInfo> playerInfos = new ArrayList<>();
        for(Player player:players){
            playerInfos.add(createSimplePlayerInfo(player));
        }
        return playerInfos;
    }

    /**
     * 登录角色
     * @param playerId 角色ID
     * @param channel 角色连接数据
     * @return void
     * */
    public void login(Integer playerId, Channel channel){
        // 获得角色数据库信息
        Player player = playerMapper.findPlayerByPlayerId(playerId);
        // 创建角色对象 并加载数据
        PlayerObject playerObject = new PlayerObject();
        // 基本信息
        playerObject.setPlayer(player);
        playerObject.setChannel(channel);
        // 加载装备
        EquipBar equipBar = itemService.loadEquipBar(playerId);
        playerObject.setEquipBar(equipBar);
        // 加载背包
        Bag bag = itemService.loadBagItem(playerId);
        playerObject.setBag(bag);
        // 初始化基础属性
        initProperty(playerObject);
        // 放入管理器
        playerManager.addPlayerObject(playerObject);
        // 进入场景
        sceneService.entryScene(playerObject,playerObject.getPlayer().getSceneId());
        // 同步角色数据

    }

    /**
     * 初始化属性
     * */
    private void initProperty(PlayerObject playerObject){
        int level = playerObject.getPlayer().getLevel();
        int career = playerObject.getPlayer().getCareer();
        DictRoleLevelProperty roleLevelProperty = dictionaryManager.getDictRoleLevelProperty(career,level);
        Property property = new Property(roleLevelProperty);
        EquipBar equipBar = playerObject.getEquipBar();
        property.init(equipBar.getEquips());
    }

    private PlayerProtocol.SimplePlayerInfo createSimplePlayerInfo(Player player){
        PlayerProtocol.SimplePlayerInfo.Builder builder = PlayerProtocol.SimplePlayerInfo.newBuilder();
        builder.setId(player.getId());
        builder.setName(player.getName());
        builder.setCareer(player.getCareer());
        builder.setLevel(player.getLevel());
        return builder.build();
    }

}
