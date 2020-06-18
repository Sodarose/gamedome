package com.game.gameserver.module.email.dao;

import com.game.gameserver.module.email.entity.Email;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/15 20:26
 */
@Repository
@Mapper
public interface EmailMapper {
    List<Email> getEmailList(Long playerId);
}
