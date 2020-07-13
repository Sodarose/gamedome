package com.game.gameserver.module.guild.type;

/**
 * 更改成枚举
 *
 * @author xuewenkang
 * @date 2020/7/3 10:52
 */
public interface GuildPosition {
    /** 普通会员 */
    int MEMBER = 0;
    /** 优秀会员 */
    int EXCELLENT_MEMBER = 1;
    /** 副会长 */
    int VICE_PRESIDENT = 2;
    /** 会长 */
    int PRESIDENT = 3;
}
