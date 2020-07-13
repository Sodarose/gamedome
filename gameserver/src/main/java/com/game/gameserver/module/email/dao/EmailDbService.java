package com.game.gameserver.module.email.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.email.entity.EmailEntity;
import com.game.gameserver.module.email.manager.EmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/12 17:28
 */
@Repository
public class EmailDbService extends BaseDbService {
    @Autowired
    private EmailMapper emailMapper;

    public List<EmailEntity> selectEmailList(long receiverId){
        return emailMapper.selectList(receiverId);
    }

    public EmailEntity select(long emailId){
        return emailMapper.select(emailId);
    }

    public int insert(EmailEntity emailEntity){
        return emailMapper.insert(emailEntity);
    }

    public int update(EmailEntity emailEntity){
        return emailMapper.update(emailEntity);
    }

    public int delete(long emailId){
        return emailMapper.delete(emailId);
    }

    public void insertAsync(EmailEntity emailEntity){
        submit(()->{
            int i = insert(emailEntity);
        });
    }

    public void updateAsync(EmailEntity emailEntity){
        submit(()->{
            int i = update(emailEntity);
        });
    }

    public void deleteAsync(long emailId){
        submit(()->{
            int i = delete(emailId);
        });
    }
}
