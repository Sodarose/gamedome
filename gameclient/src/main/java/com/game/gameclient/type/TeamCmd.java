package com.game.gameclient.type;

/**
 * @author xuewenkang
 * @date 2020/6/17 18:26
 */
public interface TeamCmd {
    int CREATE_TEAM = 1001;
    int SHOW_TEAM = 1002;
    int SHOW_TEAM_LIST = 1003;
    int APPLY_FOR_TEAM = 1004;
    int INVITE_TEAM = 1005;
    int PROCESS_TEAM_APPLY = 1006;
    int PROCESS_TEAM_INVITE = 1007;
    int EXIT_TEAM = 1008;
    int DISSOLVE_TEAM = 1009;
}
