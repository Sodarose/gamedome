package com.game.gameserver.module.friend.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.friend.entity.PlayerFriendEntity;
import com.game.gameserver.module.friend.model.PlayerFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/8 12:04
 */
@Repository
public class FriendDbService extends BaseDbService {
    @Autowired
    private FriendMapper friendMapper;

    public PlayerFriendEntity select(long playerId){
        return friendMapper.select(playerId);
    }

    public int update(PlayerFriendEntity playerFriendEntity){
        return friendMapper.update(playerFriendEntity);
    }

    public int insert(PlayerFriendEntity playerFriendEntity){
        return friendMapper.insert(playerFriendEntity);
    }

    public int delete(long playerId){
        return friendMapper.delete(playerId);
    }

    public void updateAsync(PlayerFriendEntity playerFriendEntity){
        submit(()->{
            update(playerFriendEntity);
        });
    }

    public void insertAsync(PlayerFriendEntity playerFriendEntity){
        submit(()->{
            insert(playerFriendEntity);
        });
    }

    public void deleteAsync(long playerId){
        submit(()->{
            delete(playerId);
        });
    }
}
