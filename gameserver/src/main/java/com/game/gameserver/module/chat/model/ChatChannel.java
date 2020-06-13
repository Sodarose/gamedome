package com.game.gameserver.module.chat.model;

import com.game.gameserver.module.chat.entity.ChatMsg;
import com.game.gameserver.util.GenIdUtil;
import org.apache.poi.hpsf.GUID;

import java.util.ArrayList;
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
    private final int id;
    /** 通道拥有者Id */
    private final int ownId;
    /** 通道另一方 */
    private final int targetId;
    /** 消息最大存储容量 */
    private int maxCount;
    /** 消息当前数量 */
    private int currCount;
    /** 当消息达到最大值 删除的数量 */
    private int delCount;
    /** 通道在没有交流时最大存活时长 */
    private long liveTime;
    /** 读写锁 */
    private final ReentrantReadWriteLock lock;
    /** 消息容器 */
    private final List<ChatMsg> rawData;


    public ChatChannel(int ownId,int targetId){
        this.id = GenIdUtil.nextId();
        this.ownId = ownId;
        this.targetId = targetId;
        this.lock = new ReentrantReadWriteLock();
        this.rawData = new ArrayList<>();
    }

    /**
     * 放入一条消息记录
     * @param chatMsg
     * @return void
     */
    public void addMsg(ChatMsg chatMsg){

    }





}
