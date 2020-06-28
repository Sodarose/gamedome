package com.game.gameserver.module.player.service.impl;

import com.game.gameserver.event.EventBus;
import com.game.gameserver.module.buffer.service.BufferService;
import com.game.gameserver.module.email.manager.EmailManager;
import com.game.gameserver.module.item.manager.ItemManager;
import com.game.gameserver.module.player.dao.PlayerMapper;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.event.LoginEvent;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.type.PlayerResultType;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.skill.service.SkillService;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.PlayerProtocol;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/9 20:01
 */
@Service
public class PlayerServiceImpl implements PlayerService {
    private final static Logger logger = LoggerFactory
            .getLogger(PlayerServiceImpl.class);

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private SceneManager sceneManager;
    @Autowired
    private ItemManager itemManager;
    @Autowired
    private SkillService skillService;
    @Autowired
    private BufferService bufferService;
    @Autowired
    private EmailManager emailManager;

    /**
     * 登录用户角色
     *
     * @param playerId
     * @return com.game.protocol.PlayerProtocol.LoginRes
     */
    @Override
    public PlayerProtocol.LoginPlayerRes loginRole(Long playerId, Channel channel) {
        // 查询缓存
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject != null) {
            return null;
        }
        // 查询数据库
        Player player = playerMapper.getPlayerById(playerId);
        if (player == null) {
            return ProtocolFactory.createLoginPlayerRes(PlayerResultType.LOGIN_FAILED, "登录失败", null);
        }
        // 创建角色
        playerObject = new PlayerObject(player);
        // 读取道具数据
        itemManager.loadPlayerItem(player);
        // 读取玩家邮箱
        emailManager.loadPlayerEmail(player);
        // 读取玩家技能数据
        // 设置Channel与角色的关联
        channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).set(playerObject);
        playerObject.setChannel(channel);
        // 进入场景
        sceneManager.entryScene(playerObject, player.getSceneId());
        // 放入管理器缓存
        playerManager.putPlayerObject(playerObject);
        // 发出角色登录事件
        LoginEvent loginEvent = new LoginEvent(player.getId());
        EventBus.EVENT_BUS.fire(loginEvent);
        return ProtocolFactory.createLoginPlayerRes(PlayerResultType.SUCCESS, "角色登录成功", playerObject);
    }

    /**
     * 获取角色信息
     *
     * @param playerObject
     * @return com.game.protocol.PlayerProtocol.PlayerInfo
     */
    @Override
    public PlayerProtocol.PlayerInfo getPlayerInfo(PlayerObject playerObject) {
        return ProtocolFactory.createPlayerInfo(playerObject);
    }

    /**
     * 根据账户获得该账户的角色列表
     *
     * @param accountId 账户Id
     * @return com.game.protocol.PlayerProtocol.PlayerList
     */
    @Override
    public PlayerProtocol.PlayerListRes getRoleList(int accountId) {
        // 获得角色列表
        List<Player> playerList = playerMapper.getPlayerListByAccountId(accountId);
        // 转换成Protocol 返回
        return ProtocolFactory.createPlayerList(playerList);
    }

}
