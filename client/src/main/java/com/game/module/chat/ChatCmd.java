package com.game.module.chat;

/**
 * @author xuewenkang
 * @date 2020/6/17 9:59
 */
public interface ChatCmd {
    short SEND_CHANNEL_MSG = 1001;
    short SEND_PRIVACY_MSG = 1002;
    short SEND_COMMON_MSG = 1003;
    short RECEIVE_CHANNEL_MSG = 1004;
    short RECEIVE_PRIVACY_MSG = 1005;
    short RECEIVE_COMMON_MSG = 1006;
}
