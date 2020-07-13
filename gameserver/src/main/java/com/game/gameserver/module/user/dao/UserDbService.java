package com.game.gameserver.module.user.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.user.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/11 1:14
 */
@Repository
public class AccountDbService extends BaseDbService {

    @Autowired
    private AccountMapper accountMapper;

    public AccountEntity select(String loginId){
        return accountMapper.select(loginId);
    }

    public void updateAsync(AccountEntity accountEntity){
        submit(()->accountMapper.update(accountEntity));
    }

    public void insertAsync(AccountEntity accountEntity){
        submit(()->accountMapper.insert(accountEntity));
    }

    public int insert(AccountEntity accountEntity){
        return accountMapper.insert(accountEntity);
    }

    public void deleteAsync(long accountId){
        submit(()->accountMapper.delete(accountId));
    }

    public int count(String loginId){
        return accountMapper.count(loginId);
    }


}
