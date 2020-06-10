package com.game.gameserver.module.chat.model;

import com.game.gameserver.module.chat.entity.ChatMsg;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 私聊通道
 *
 * @author xuewenkang
 * @date 2020/6/10 18:34
 */
public class ChatChannel implements Channel {
    /** 私聊通道唯一ID */
    private int id;
    /** 通道拥有者Id */
    private int ownId;
    /** 通道另一方 */
    private int targetId;
    /** 消息最大存储容量 */
    private int maxCount;
    /** 消息当前数量 */
    private int currCount;
    /** 当消息达到最大值 删除的数量 */
    private int delCount;
    /** 通道在没有交流时最大存活时长 */
    private long liveTime;
    /** 读写锁 */
    private ReentrantReadWriteLock lock;
    /** 消息容器 */
    private List<ChatMsg> rawData;
}
