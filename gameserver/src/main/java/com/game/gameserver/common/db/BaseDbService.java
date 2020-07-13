package com.game.gameserver.common.db;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * 基础db服务 使用线程+工作队列的方式更新数据库数据
 *
 * @author xuewenkang
 * @date 2020/7/8 10:20
 */
public abstract class BaseDbService {
    /** DB Factory */
    private final static ThreadFactory DB_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("DB-Thread").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();
    /** DB 线程 */
    protected final static ExecutorService DB_THREAD_POOL = new ThreadPoolExecutor(
            1,
            1,
            30, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(),
            DB_THREAD_FACTORY
    );

    protected void submit(Runnable runnable){
        DB_THREAD_POOL.execute(runnable);
    }

}
