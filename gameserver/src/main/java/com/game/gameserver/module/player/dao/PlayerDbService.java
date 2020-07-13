package com.game.gameserver.module.player.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.player.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户存档服务
 *
 * @author xuewenkang
 * @date 2020/7/2 18:03
 */
@Repository
public class PlayerDbService extends BaseDbService {

    @Autowired
    private PlayerMapper playerMapper;

    public List<Role> selectRoleList(long userId) {
        return playerMapper.selectRoleList(userId);
    }

    public List<Long> selectRoleIds(long userId){
        return playerMapper.selectRoleIds(userId);
    }

    public PlayerEntity select(long playerId) {
        return playerMapper.select(playerId);
    }

    public int count(String name){
        return playerMapper.count(name);
    }

    public int insert(PlayerEntity player) {
        return playerMapper.insert(player);
    }

    public int update(PlayerEntity player) {
        return playerMapper.update(player);
    }

    public int delete(long playerId) {
        return playerMapper.delete(playerId);
    }

    public void insertAsync(PlayerEntity player) {
        submit(() -> playerMapper.insert(player));
    }

    public void updateAsync(PlayerEntity player) {
        submit(() -> playerMapper.update(player));
    }

    public void deleteAsync(long playerId) {
        submit(() -> playerMapper.delete(playerId));
    }
}
