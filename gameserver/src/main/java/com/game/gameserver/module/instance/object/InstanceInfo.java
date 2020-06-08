package com.game.gameserver.module.instance.object;

import com.game.gameserver.common.config.InstanceConfig;
import lombok.Data;

/**
 * 副本信息 用于确认副本是否允许开启
 *
 * @author xuewenkang
 * @date 2020/6/8 19:28
 */
@Data
public class InstanceInfo {
    /** 当前副本状态 确定副本是否允许挑战副本 */
    private int state;
    /** 副本信息 */
    private InstanceConfig instanceConfig;
}
