package com.game.gameserver.module.guild.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位权限映射
 *
 * @author xuewenkang
 * @date 2020/7/3 11:10
 */
public class PositionPermissionMapping {
    /** 职位 权限映射 */
    public final static Map<Integer, List<Integer>> POSITION_PERMISSION_MAP = new HashMap<>(4);
    static{
        // 会长权限
        List<Integer> presidentPerm = new ArrayList<>();
        // 任命
        presidentPerm.add(GuildPermission.APPOINT);
        // 公告
        presidentPerm.add(GuildPermission.ANNOUNCEMENT);
        // 处理申请信息
        presidentPerm.add(GuildPermission.PROCESS_APPLY);
        // 踢人
        presidentPerm.add(GuildPermission.KICK);
        // 公会升级
        presidentPerm.add(GuildPermission.LEVEL_UP);
        // 公会解散
        presidentPerm.add(GuildPermission.DISSOLVE);
        // 使用公会仓库
        presidentPerm.add(GuildPermission.USE_WAREHOUSE);
        POSITION_PERMISSION_MAP.put(GuildPosition.PRESIDENT,presidentPerm);

        /** 副会长权限 */
        List<Integer> vicePresident = new ArrayList<>();
        // 任命
        vicePresident.add(GuildPermission.APPOINT);
        // 公告
        vicePresident.add(GuildPermission.ANNOUNCEMENT);
        // 处理申请信息
        vicePresident.add(GuildPermission.PROCESS_APPLY);
        // 踢人
        vicePresident.add(GuildPermission.KICK);
        // 公会升级
        vicePresident.add(GuildPermission.LEVEL_UP);
        // 使用公会仓库
        vicePresident.add(GuildPermission.USE_WAREHOUSE);
        POSITION_PERMISSION_MAP.put(GuildPosition.VICE_PRESIDENT,vicePresident);

        /** 优秀会员权限 */
        List<Integer> excellentMember = new ArrayList<>();
        excellentMember.add(GuildPermission.USE_WAREHOUSE);
        POSITION_PERMISSION_MAP.put(GuildPosition.EXCELLENT_MEMBER,excellentMember);

        /** 普通会员权限 */
        List<Integer> member = new ArrayList<>();
        POSITION_PERMISSION_MAP.put(GuildPosition.MEMBER,member);
    }
}
