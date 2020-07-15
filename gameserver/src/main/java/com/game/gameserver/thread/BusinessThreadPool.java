package com.game.gameserver.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 业务线程池
 *
 * @author xuewenkang
 * @date 2020/7/9 3:28
 */
public class BusinessThreadPool {
    /** 线程工厂 */
    private final static ThreadFactory BUSINESS_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("business-pool-%d").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();
    /** 业务线程池 */
    public final static ThreadPoolExecutor BUSINESS_THREAD_POOL = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors()*2,
            30, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100),
            BUSINESS_THREAD_FACTORY
            );
}
