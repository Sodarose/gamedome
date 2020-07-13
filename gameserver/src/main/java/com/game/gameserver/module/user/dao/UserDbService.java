package com.game.gameserver.module.user.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/11 1:14
 */
@Repository
public class UserDbService extends BaseDbService {

    @Autowired
    private UserMapper userMapper;

    public UserEntity select(String loginId){
        return userMapper.select(loginId);
    }

    public void updateAsync(UserEntity userEntity){
        submit(()-> userMapper.update(userEntity));
    }

    public void insertAsync(UserEntity userEntity){
        submit(()-> userMapper.insert(userEntity));
    }

    public int insert(UserEntity userEntity){
        return userMapper.insert(userEntity);
    }

    public void deleteAsync(long accountId){
        submit(()-> userMapper.delete(accountId));
    }

    public int count(String loginId){
        return userMapper.count(loginId);
    }


}
