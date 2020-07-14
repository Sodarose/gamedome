package com.game.gameserver.module.email.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.email.entity.EmailEntity;
import com.game.gameserver.module.email.helper.EmailHelper;
import com.game.gameserver.module.email.manager.EmailManager;
import com.game.gameserver.module.email.model.Email;
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

    public int insert(Email email){
        EmailEntity emailEntity = EmailHelper.createEmailEntity(email);
        return emailMapper.insert(emailEntity);
    }

    public int update(Email email){
        EmailEntity emailEntity = EmailHelper.createEmailEntity(email);
        return emailMapper.update(emailEntity);
    }

    public int delete(long emailId){
        return emailMapper.delete(emailId);
    }

    public void insertAsync(Email email){
        submit(()->{
            int i = insert(email);
        });
    }

    public void updateAsync(Email email){
        submit(()->{
            int i = update(email);
        });
    }

    public void deleteAsync(long emailId){
        submit(()->{
            int i = delete(emailId);
        });
    }
}
