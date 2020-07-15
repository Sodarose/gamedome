package com.game.gameserver.net.modelhandler.task;

/**
 * @author xuewenkang
 * @date 2020/6/29 15:00
 */
public interface TaskCmd {
    int SHOW_ALL_TASK = 1001;
    int SHOW_RECEIVE_ABLE_TASK = 1002;
    int SHOW_RECEIVE_TASK = 1003;
    int ACCEPT_TASK = 1004;
    int CANCEL_TASK = 1005;
    int SUBMIT_TASK = 1006;
}
