package com.game.module.chat;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/15 14:07
 */
@Data
public class ChatMsg {
    /** 消息Id */
    private int id;
    /** 发送者Id */
    private int sendId;
    /** 发送者名称 */
    private String sendName;
    /** 消息内容 */
    private String message;
    /** 频道id */
    private int channelId;
    /** 发送时间 */
    private long sendTime;
}
