package com.game.gameserver.module.friend.helper;

import com.game.gameserver.module.friend.model.Friend;

import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/13 17:33
 */
public class FriendHelper {

    public static String buildFriendListMsg(Map<Long,Friend> friendMap){
        StringBuilder sb = new StringBuilder();
        if(friendMap.size()==0){
            sb.append("空空如也").append("\n");
            return sb.toString();
        }
        friendMap.forEach((key,value)->{
            sb.append(buildFriendMsg(value)).append("\n");
            sb.append("\n");
        });
        return null;
    }

    public static String buildFriendMsg(Friend friend){
        StringBuilder sb = new StringBuilder("好友信息:");
        sb.append("id:").append(friend.getId()).append("\n");
        sb.append("name:").append(friend.getFriendName()).append("\n");
        sb.append("friendId:").append(friend.getFriendId()).append("\n");
        sb.append("friendType:").append(friend.getFriendType()).append("\n");
        sb.append("online:").append(friend.isOnline()).append("\n");
        return sb.toString();
    }
}
