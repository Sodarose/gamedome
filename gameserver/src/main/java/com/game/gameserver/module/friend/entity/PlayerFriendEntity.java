package com.game.gameserver.module.friend.entity;

import com.game.gameserver.module.friend.model.Friend;
import com.game.gameserver.module.guild.model.Applicant;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author xuewenkang
 * @date 2020/7/23 14:27
 */
@Data
public class PlayerFriendEntity{
    /** 好友 */
    private final Long playerId;
    /** 好友表 */
    private Map<Long,Friend> friendMap = new ConcurrentHashMap<>();
    /** 好友申请表 */
    private Map<String, Applicant> applicantMap = new ConcurrentHashMap<>();
}
