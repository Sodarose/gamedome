package com.game.gameserver.module.instance.manager;

import com.game.gameserver.module.instance.object.InstanceInfo;
import com.game.gameserver.module.instance.object.InstanceObject;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 副本管理器
 *
 * @author xuewenkang
 * @date 2020/6/8 18:29
 */
@Component
public class InstanceManager {
    /** 已经创建的副本对象 */
    private Map<Integer, InstanceObject> instanceObjectMap;
    /** 副本信息 */
    private Map<Integer, InstanceInfo> instanceInfoMap;
}
