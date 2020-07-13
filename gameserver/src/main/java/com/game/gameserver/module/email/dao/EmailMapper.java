package com.game.gameserver.module.email.dao;

import com.game.gameserver.module.email.entity.EmailEntity;
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
    List<EmailEntity> selectList(long receiverId);
    EmailEntity select(long emailId);
    int update(EmailEntity emailEntity);
    int insert(EmailEntity emailEntity);
    int delete(long emailId);
}
