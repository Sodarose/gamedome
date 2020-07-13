package com.game.gameserver.module.user.dao;

import com.game.gameserver.module.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/5/18 17:55
 */
@Repository
@Mapper
public interface UserMapper {

    UserEntity select(String loginId);

    int insert(UserEntity userEntity);

    int update(UserEntity userEntity);

    int delete(long accountId);

    int count(String loginId);
}
