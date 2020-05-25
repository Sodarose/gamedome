package com.game.gameserver.module.player.facade.impl;

import com.game.gameserver.module.player.dao.PlayerMapper;
import com.game.gameserver.module.player.facade.PlayerFacade;
import com.game.gameserver.module.player.model.Role;
import com.game.gameserver.module.player.vo.PlayerVo;
import io.netty.channel.Channel;
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

    @Autowired
    private PlayerMapper playerMapper;

    /**
     * 返回账户上的所有角色
     * @param accountId 账户ID
     * @return java.util.List<com.game.gameserver.module.player.entity.PlayerEntity>
     */
    @Override
    public List<Role> getPlayListByAccountId(Integer accountId) {
        List<Role> roles = playerMapper.findPlayerListByAccountId(accountId);
        return roles;
    }

    /**
     * 根据角色Id 登录角色
     * @param roleId
     * @param channel
     * @return com.game.gameserver.module.player.vo.PlayerVo
     */
    @Override
    public PlayerVo loginRoleByRoleId(Integer roleId, Channel channel) {
        
        return null;
    }
}
