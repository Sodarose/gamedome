package com.game.gameserver.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    /** 标识线程池 key:线程标识   value:线程*/
    private final Map<Integer,ThreadPoolExecutor> THREAD_POOL_MAP = new HashMap<>();
    /** 用户玩家对应的线程 key：玩家Id value:线程标识 */
    private final Map<Long,Integer> USER_THREAD_MAP = new ConcurrentHashMap<>();
    /** 计数器 用于均匀分配标识线程 */
    private final AtomicInteger COUNTER = new AtomicInteger(0);

    public void initialize(){
        
    }
}
