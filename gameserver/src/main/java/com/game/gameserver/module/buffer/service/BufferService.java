package com.game.gameserver.module.buffer.service;

import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.module.buffer.model.Buffer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * buffer 服务
 *
 * @author xuewenkang
 * @date 2020/6/11 10:58
 */
@Service
public class BufferService {

    /** Buffer定时器线程 */
    private final static ThreadFactory BUFFER_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("Buffer_Thread-%d").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();

    /** Buffer线程 */
    private final static ScheduledThreadPoolExecutor BUFFER_THREAD = new ScheduledThreadPoolExecutor(1
            ,BUFFER_THREAD_FACTORY);

    /**
     * 添加一个Buffer到指定的目标
     *
     * @param creature
     * @param buffer
     * @return void
     */
    public void addBuffer(Creature creature, Buffer buffer){

    }
}
