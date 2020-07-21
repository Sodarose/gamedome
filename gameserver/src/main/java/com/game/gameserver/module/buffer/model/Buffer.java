package com.game.gameserver.module.buffer.model;

import com.game.gameserver.common.config.BufferConfig;
import com.game.gameserver.common.entity.Creature;
import lombok.Data;

import java.util.concurrent.Future;

/**
 * buffer
 *
 * @author xuewenkang
 * @date 2020/6/10 10:32
 */
@Data
public class Buffer {
    /**
     * buffer配置
     */
    private BufferConfig bufferConfig;
    /**
     * 当前执行次数
     */
    private int times;
    /**
     * 开始时间
     */
    private long startTime;

    /** buffer生效的定时器 */
    private Future runFuture;

    /** 移除Buffer的定时器 */
    private Future cancelFuture;

    public Buffer(BufferConfig bufferConfig) {
        this.bufferConfig = bufferConfig;
        this.times = 0;
        this.startTime = 0L;
    }

    public void addTimes(int value) {
        this.times += value;
    }
}
