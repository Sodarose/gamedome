package com.game.gameserver.module.player.service.impl;

import com.game.gameserver.event.EventBus;
import com.game.gameserver.module.player.dao.PlayerMapper;
import com.game.gameserver.module.player.entity.Role;
import com.game.gameserver.module.player.event.LoginEvent;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.service.PlayerService;
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

    /**
     * 登录用户角色
     *
     * @param playerId
     * @return com.game.protocol.PlayerProtocol.LoginRes
     */
    @Override
    public PlayerProtocol.LoginRoleRes loginRole(Long playerId, Channel channel) {
        // 从缓存中查询用户数据
        Player player = playerManager.getPlayer(playerId);
        if (player != null) {
            // 获取连接信息 缓存中的连接信息是否活跃 如果是 则表示被挤下来了
            if (player.getChannel().isActive()) {
                // 发送角色被挤下去通知

                // 断开连接
                player.getChannel().close();
            }
            // 设置新的连接信息
            player.setChannel(channel);
            // 发出登录事件
            LoginEvent loginEvent = new LoginEvent(player.getId());
            EventBus.EVENT_BUS.fire(loginEvent);
            // 返回登录成功
            PlayerProtocol.PlayerInfo playerInfo = ProtocolFactory.createPlayerInfo(player);
            return PlayerProtocol.LoginRoleRes.newBuilder().setCode(0).setMsg("登录角色成功")
                    .setPlayerInfo(playerInfo).build();
        }
        // 不再缓存中 则从数据库中查询数据
        player = playerMapper.selectPlayer(playerId);
        if (player == null) {
            return PlayerProtocol.LoginRoleRes.newBuilder().setCode(1000).setMsg("非法登录").build();
        }
        // 设置连接信息
        channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).set(player);
        player.setChannel(channel);
        // 放入缓存中
        playerManager.putPlayerObject(player);
        // 发出角色登录事件
        LoginEvent loginEvent = new LoginEvent(player.getId());
        EventBus.EVENT_BUS.fire(loginEvent);
        PlayerProtocol.PlayerInfo playerInfo = ProtocolFactory.createPlayerInfo(player);
        return PlayerProtocol.LoginRoleRes.newBuilder().setCode(0).setMsg("登录角色成功")
                .setPlayerInfo(playerInfo).build();
    }

    /**
     * 根据账户获得该账户的角色列表
     *
     * @param accountId 账户Id
     * @return com.game.protocol.PlayerProtocol.PlayerList
     */
    @Override
    public PlayerProtocol.QueryRoleListRes queryRoleList(int accountId) {
        // 获得角色列表
        List<Role> roles = playerMapper.selectRoleList(accountId);
        // 转换成Protocol 返回
        return ProtocolFactory.createQueryRoleListRes(roles);
    }

    /**
     * 获取角色信息
     *
     * @param player
     * @return com.game.protocol.PlayerProtocol.PlayerInfo
     */
    @Override
    public PlayerProtocol.QueryPlayerInfoRes queryPlayerInfo(Player player) {
        return ProtocolFactory.createQueryPlayerInfoRes(player);
    }
}
