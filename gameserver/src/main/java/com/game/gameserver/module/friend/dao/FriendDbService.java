package com.game.gameserver.module.friend.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.friend.entity.FriendEntity;
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

    public List<FriendEntity> selectFriendEntityList(long playerId){
        return friendMapper.selectFriendEntityList(playerId);
    }

    public FriendEntity select(long id){
        return friendMapper.select(id);
    }

    public int update(FriendEntity friendEntity){
        return friendMapper.update(friendEntity);
    }

    public int insert(FriendEntity friendEntity){
        return friendMapper.insert(friendEntity);
    }

    public int delete(long id){
        return friendMapper.delete(id);
    }

    public void updateAsync(FriendEntity friendEntity){
        submit(()->{
            update(friendEntity);
        });
    }

    public void insertAsync(FriendEntity friendEntity){
        submit(()->{
            insert(friendEntity);
        });
    }

    public void deleteAsync(long id){
        submit(()->{
            delete(id);
        });
    }
}
