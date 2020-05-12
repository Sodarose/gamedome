package com.game.gameserver.mapper;

import com.game.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 */
@Repository
public interface AccountMapper {

    /**
     * 根据LoginId查找用户
     * @param loginId 登录账号
     * @return 返回用户数据
     * */
    User findUserByLoginId(String loginId);

    /**
     * description: 新建一个用户
     *
     * @param user 用户属性
     * @return java.lang.Integer
     */
    int insertUserByUser(User user);
}
