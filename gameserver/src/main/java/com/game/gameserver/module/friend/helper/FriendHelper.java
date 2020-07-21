package com.game.gameserver.module.friend.helper;

import com.game.gameserver.module.friend.model.Friend;
import com.game.gameserver.module.friend.model.UserFriend;

import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/13 17:33
 */
public class FriendHelper {

    public static String buildFriendListMsg(UserFriend userFriend){
        StringBuilder sb = new StringBuilder().append("\n");
        sb.append("申请列表:").append("\n");
        sb.append("[");
        userFriend.getApplicant().forEach(applyId->{
            sb.append(applyId).append(",");
        });
        sb.append("]").append("\n");
        sb.append("好友列表:").append("\n");
        userFriend.getFriendMap().forEach((key,value)->{
            sb.append(buildFriendMsg(value)).append("\n");
            sb.append("\n");
        });
        return sb.toString();
    }

    public static String buildFriendMsg(Friend friend){
        StringBuilder sb = new StringBuilder("好友信息:");
        sb.append("id:").append(friend.getId()).append("\n");
        sb.append("friendId:").append(friend.getFriendId()).append("\n");
        sb.append("friendType:").append(friend.getFriendType()).append("\n");
        sb.append("online:").append(friend.isOnline()).append("\n");
        return sb.toString();
    }
}
