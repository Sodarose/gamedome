package com.game.gameserver.net.modelhandler.achievement;

/**
 * @author xuewenkang
 * @date 2020/7/2 15:42
 */
public interface AchievementCmd {
    /** 查询所有的成就 */
    short QUERY_ALL_ACHIEVEMENT = 1001;
    /** 查询角色的成绩*/
    short QUERY_PLAYER_ACHIEVEMENT = 1002;
    /** 提交成就*/
    short SUBMIT_ACHIEVEMENT = 1003;
}
