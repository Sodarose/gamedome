package com.game.gameserver.module.chat.entity;

import lombok.Data;

/**
 * 聊天数据实体
 *
 * @author xuewenkang
 * @date 2020/6/10 19:46
 */
@Data
public class ChatMsg {
    /** 消息Id */
    private int id;
    /** 发送者Id */
    private int sendId;
    /** 接受者Id */
    private int receiverId;
    /** 消息内容 */
    private String message;
    /** 频道id */
    private int channelId;
    /** 发送时间 */
    private long sendTime;
}
