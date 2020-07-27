package com.game.gameserver.thread;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.UserLogoutEvent;
import com.game.gameserver.module.user.module.User;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 业务线程池
 *
 * @author xuewenkang
 * @date 2020/7/9 3:28
 */
@Listener
@Component
public class BusinessThreadPool {


    public static BusinessThreadPool BUSINESS_THREAD_POOL;

    public BusinessThreadPool() {
        BUSINESS_THREAD_POOL = this;
    }

    /*** 业务线程组 */
    private final ThreadPoolExecutor[] BUSINESS_THREAD_GROUP = new
            ThreadPoolExecutor[Runtime.getRuntime().availableProcessors() * 2];
    /*** 用户玩家对应的线程 key：角色Id value:线程标识 */
    private final Map<Long, Integer> USER_THREAD_MAP = new ConcurrentHashMap<>();
    /*** 计数器 用于均匀分配标识线程*/
    private final AtomicInteger COUNTER = new AtomicInteger(0);

    /*** 玩家未登录角色 账户线程 */
    private final static ThreadFactory ACCOUNT_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("Account-%d")
            .setUncaughtExceptionHandler((t, e) -> e.printStackTrace()).build();
    private final ThreadPoolExecutor ACCOUNT_THREAD = new ThreadPoolExecutor(
            1,
            1,
            30,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(),
            ACCOUNT_THREAD_FACTORY);

    /**
     * 初始化
     */
    public void initialize() {
        // 创建业务线程池
        for (int i = 0; i < BUSINESS_THREAD_GROUP.length; i++) {
            // 业务线程工厂
            ThreadFactory businessThreadFactory = new ThreadFactoryBuilder()
                    // 线程标识
                    .setNameFormat("Business-" + i )
                    .setUncaughtExceptionHandler((t, e) -> e.printStackTrace()).build();
            ThreadPoolExecutor businessThread = new ThreadPoolExecutor(
                    1,
                    1,
                    30,
                    TimeUnit.MINUTES,
                    new LinkedBlockingQueue<>(),
                    businessThreadFactory);
            BUSINESS_THREAD_GROUP[i] = businessThread;
        }
    }

    /**
     * 根据账户Id 分配业务线程
     */
    public ThreadPoolExecutor getBusinessThread(long userId) {
        // 如果该该id已经绑定了线程 则直接返回该线程
        if (USER_THREAD_MAP.get(userId) != null) {
            return BUSINESS_THREAD_GROUP[USER_THREAD_MAP.get(userId)];
        }
        // 获得线程标识
        int i = COUNTER.addAndGet(1) % BUSINESS_THREAD_GROUP.length;
        // 绑定线程标识
        USER_THREAD_MAP.put(userId, i);
        return BUSINESS_THREAD_GROUP[i];
    }

    public ThreadPoolExecutor getAccountThread() {
        return ACCOUNT_THREAD;
    }


    /**
     * 处理账户退出事件
     *
     * @param userLogoutEvent
     * @return void
     */
    @EventHandler
    public void handUserLogoutEvent(UserLogoutEvent userLogoutEvent) {
        // 移除该账户的Id
        User user = userLogoutEvent.getUser();
        USER_THREAD_MAP.remove(user.getId());
    }
}
