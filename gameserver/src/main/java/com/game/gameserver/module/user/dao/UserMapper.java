package com.game.gameserver.module.user.dao;

import com.game.gameserver.module.user.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/5/18 17:55
 */
@Repository
@Mapper
public interface AccountMapper {

    AccountEntity select(String loginId);

    int insert(AccountEntity accountEntity);

    int update(AccountEntity accountEntity);

    int delete(long accountId);

    int count(String loginId);
}
