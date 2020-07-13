package com.game.gameserver.module.union.type;

import com.game.gameserver.module.union.entity.Union;

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
        // 会装权限
        List<Integer> presidentPerm = new ArrayList<>();
        // 任命
        presidentPerm.add(UnionPermission.APPOINT);
        // 公告
        presidentPerm.add(UnionPermission.ANNOUNCEMENT);
        // 处理申请信息
        presidentPerm.add(UnionPermission.PROCESS_APPLY);
        // 踢人
        presidentPerm.add(UnionPermission.KICK);
        // 公会升级
        presidentPerm.add(UnionPermission.LEVEL_UP);
        // 公会解散
        presidentPerm.add(UnionPermission.DISSOLVE);
        // 使用公会仓库
        presidentPerm.add(UnionPermission.USE_WAREHOUSE);
        POSITION_PERMISSION_MAP.put(UnionPosition.PRESIDENT,presidentPerm);

        /** 副会长权限 */
        List<Integer> vicePresident = new ArrayList<>();
        // 任命
        vicePresident.add(UnionPermission.APPOINT);
        // 公告
        vicePresident.add(UnionPermission.ANNOUNCEMENT);
        // 处理申请信息
        vicePresident.add(UnionPermission.PROCESS_APPLY);
        // 踢人
        vicePresident.add(UnionPermission.KICK);
        // 公会升级
        vicePresident.add(UnionPermission.LEVEL_UP);
        // 使用公会仓库
        vicePresident.add(UnionPermission.USE_WAREHOUSE);
        POSITION_PERMISSION_MAP.put(UnionPosition.VICE_PRESIDENT,vicePresident);

        /** 优秀会员权限 */

    }
}
