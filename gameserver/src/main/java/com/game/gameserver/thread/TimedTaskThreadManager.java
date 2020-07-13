package com.game.gameserver.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * 定时线程管理器
 *
 * @author xuewenkang
 * @date 2020/7/7 3:26
 */
public class TimedTaskThreadManager {
    /** 定时任务线程池 */
    private final static ThreadFactory TIMED_TASK_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("timedTask-pool-%d").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();
    /** 定时任务线程池 */
    private final static ScheduledThreadPoolExecutor  TIMED_TASK_THREAD_POOL = new ScheduledThreadPoolExecutor(Runtime.getRuntime()
            .availableProcessors()
            ,TIMED_TASK_THREAD_FACTORY);

}
