package com.game.gameserver.net.modelhandler.friend;

/**
 * @author xuewenkang
 * @date 2020/7/13 17:30
 */
public interface FriendCmd {
    int SHOW_FRIEND = 1001;
    int APPLY_FOR_FRIEND = 1002;
    int PROCESS_FRIEND_APPLY = 1003;
    int REMOVE_FRIEND = 1004;
    int CHANGE_FRIEND_TYPE = 1005;
}
