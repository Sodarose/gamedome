package com.game.gameserver.module.friend.helper;

import com.game.gameserver.module.friend.model.Friend;
import com.game.gameserver.module.friend.model.PlayerFriend;
import com.game.gameserver.module.friend.type.FriendType;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.service.PlayerService;

/**
 * @author xuewenkang
 * @date 2020/7/13 17:33
 */
public class FriendHelper {

    public static String buildFriendListMsg(PlayerFriend playerFriend) {
        StringBuilder sb = new StringBuilder().append("\n");
        sb.append("申请列表:").append("\n");
        sb.append("[");
        playerFriend.getApplicantMap().forEach((key, value) -> {
            sb.append(key).append(",");
        });
        sb.append("]").append("\n");
        sb.append("好友列表:").append("\n");
        playerFriend.getFriendMap().forEach((key, value) -> {
            sb.append(buildFriendMsg(value)).append("\n");
            sb.append("\n");
        });
        return sb.toString();
    }

    public static String buildFriendMsg(Friend friend) {
        StringBuilder sb = new StringBuilder("好友信息:");
        sb.append("好友:").append(friend.getName()).append("\n");
        sb.append("是否在线:").append(PlayerManager.instance.getPlayer(friend.getFriendId()) != null).append("\n");
        sb.append("亲密度:").append(buildFriendTypeMsg(friend.getFriendType()))
                .append("\n");
        return sb.toString();
    }

    private static String buildFriendTypeMsg(int friendType) {
        if (friendType == FriendType.COMMON) {
            return "普通";
        }
        if (friendType == FriendType.INTIMATE) {
            return "亲密";
        }
        if (friendType == FriendType.BLACK_LIST) {
            return "黑名单";
        }
        return "无";
    }
}
