package com.game.gameserver.module.player.model;

import com.game.gameserver.module.buffer.entity.Buffer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家序列化数据
 *
 * @author xuewenkang
 * @date 2020/6/17 14:58
 */
@Data
public class PlayerData implements Serializable {
    /** buff列表 */
    private List<Buffer> buffers;
    /** 当前所在的组队 队伍ID */
    private Integer teamId;
    /** 副本次数数据 */
    private final Map<Integer,Integer> instanceNumMap = new ConcurrentHashMap<>();
    /** 聊天频道  key 频道类型  value 频道Id*/
    private final Map<Integer,Long> playerChannelMap = new ConcurrentHashMap<>();
}
