package com.game.gameserver.net.modelhandler.chat;

/**
 * @author xuewenkang
 * @date 2020/6/15 17:44
 */
public interface ChatCmd {
    /** 私聊 */
    int PRIVATE_CHAT = 1001;
    /** 本地聊天 */
    int LOCAL_CHAT = 1002;
    /** 频道聊天 */
    int CHANNEL_CHAT = 1003;
}
