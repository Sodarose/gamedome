package com.game.gameserver.module.chat.model;

import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.chat.entity.ChatMsg;

import java.util.List;

/**
 * 全服聊天通道
 *
 * @author xuewenkang
 * @date 2020/6/10 18:30
 */
public class WorldChannel implements Channel {
    /** 通道Id */
    private int id;
    /** 消息记录 */
    private List<ChatMsg> rawData;
}
