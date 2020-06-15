package com.game.gameserver.module.player.service.impl;

import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.buffer.service.BufferService;
import com.game.gameserver.module.goods.model.EquipBag;
import com.game.gameserver.module.goods.model.PlayerBag;
import com.game.gameserver.module.goods.service.EquipService;
import com.game.gameserver.module.goods.service.PropService;
import com.game.gameserver.module.player.dao.PlayerMapper;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.model.PlayerResultType;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.scene.service.SceneService;
import com.game.gameserver.module.skill.model.PlayerSkill;
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
/*    @Autowired
    private EquipService equipService;
    @Autowired
    private PropService propService;*/
    @Autowired
    private SceneService sceneService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private BufferService bufferService;

    /**
     * 登录用户角色
     *
     * @param playerId
     * @return com.game.protocol.PlayerProtocol.LoginRes
     */
    @Override
    public PlayerProtocol.LoginPlayerRes loginPlayer(int playerId, Channel channel) {
        Player player = playerMapper.getPlayerById(playerId);
        if (player == null) {
            return ProtocolFactory.createLoginPlayerRes(PlayerResultType.LOGIN_FAILED,"登录失败");
        }
        // 创建角色
        PlayerObject playerObject = new PlayerObject(player);
     /*   // 加载用户装备
        EquipBag equipBag = equipService.loadEquipBar(playerId);
        playerObject.setEquipBag(equipBag);
        // 加载用户背包
        PlayerBag propsBag = propService.loadPropsBag(playerId);
        playerObject.setPlayerBag(propsBag);*/
        // 加载当前角色技能
        PlayerSkill playerSkill = skillService.loadPlayerSkill(playerId);
        playerObject.setPlayerSkill(playerSkill);
        // 加载当前角色buff
        List<Buffer> buffers = bufferService.loadPlayerBuffer(playerId);
        playerObject.setBuffers(buffers);
        // 初始化
        playerObject.initialize();
        playerObject.setChannel(channel);
        // 保存该角色数据
        playerManager.putPlayerObject(playerObject);
        channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).compareAndSet(null,
                playerObject);
        // 返回登录结果
        return ProtocolFactory.createLoginPlayerRes(PlayerResultType.SUCCESS,"角色登录成功");
    }

    /**
     * 根据账户获得该账户的角色列表
     *
     * @param accountId 账户Id
     * @return com.game.protocol.PlayerProtocol.PlayerList
     */
    @Override
    public PlayerProtocol.PlayerListRes getPlayerList(int accountId) {
        // 获得角色列表
        List<Player> playerList = playerMapper.getPlayerListByAccountId(accountId);
        // 转换成Protocol 返回
        return ProtocolFactory.createPlayerList(playerList);
    }

    /**
     * 根据Id 获取角色
     *
     * @param playerId
     * @return com.game.gameserver.module.player.model.PlayerObject
     */
    @Override
    public PlayerObject getPlayerObject(int playerId) {
        return playerManager.getPlayerObject(playerId);
    }

    /**
     * 获取当前角色信息
     *
     * @param playerId
     * @return com.game.protocol.PlayerProtocol.PlayerInfoReq
     */
    @Override
    public PlayerProtocol.PlayerInfoReq getPlayerInfo(int playerId) {
        return null;
    }
}
