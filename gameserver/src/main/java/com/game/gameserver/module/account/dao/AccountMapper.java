package com.game.gameserver.module.account.dao;

import com.game.gameserver.module.account.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/5/18 17:55
 */
@Repository
@Mapper
public interface AccountMapper {
    /**
     * 根据登录账号查找用户
     * @param loginId 登录账号
     * @return com.game.gameserver.game.account.Account
     */
    Account findUserByLoginId(String loginId);


    /**
     * 创建账户
     * @param account 创建账户
     * @return java.lang.Integer
     */
    Integer createUserByUser(Account account);
}
