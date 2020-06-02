package com.game.gameserver.module.timewheel.entity;

import com.game.gameserver.common.DefaultThreadFactory;
import io.netty.util.internal.PlatformDependent;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 简单 秒级 时间轮
 *
 * @author xuewenkang
 * @date 2020/5/28 14:38
 */
public class TimeWheelTimer {

    private final static Logger logger = LoggerFactory.getLogger(TimeWheelTimer.class);
    private final static AtomicBoolean WORKER_STATE = new AtomicBoolean(false);

    /**
     * 单轮tick时间
     */
    private final long tickDuration;
    /**
     * 时间轮槽数数量
     */
    private final int ticksPerWheel;
    /**
     * 时间单位
     */
    private final TimeUnit timeUnit;
    /**
     * 线程工厂
     */
    private ThreadFactory threadFactory;

    /**
     * 时间轮
     */
    private Slot[] wheels;

    private Worker worker;
    private Thread thread;

    private volatile long startTime;
    private volatile long currentTickIndex;

    public TimeWheelTimer(int tickDuration, int ticksPerWheel, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("the timeUnit is null");
        }
        if (tickDuration <= 0) {
            throw new IllegalArgumentException("tickDuration mast be greater than 0: now tickDuration " + tickDuration);
        }
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel mast be greater than 0: now ticksPerWheel " + ticksPerWheel);
        }
        // 以纳秒的方式计算
        this.tickDuration = TimeUnit.NANOSECONDS.convert(tickDuration, timeUnit);
        this.timeUnit = timeUnit;
        this.ticksPerWheel = ticksPerWheel;

        this.wheels = new Slot[ticksPerWheel];
        for (int i = 0; i < wheels.length; i++) {
            wheels[i] = new Slot();
        }
        this.threadFactory = new DefaultThreadFactory("TimeWheel Timeout");
        this.worker = new Worker();
        this.thread = new Thread(worker, "TimeWheel");
    }

    public TimeWheelTimer(int tickDuration, int ticksPerWheel, TimeUnit timeUnit, ThreadFactory threadFactory) {
        if (timeUnit == null) {
            throw new NullPointerException("the timeUnit is null");
        }
        if (threadFactory == null) {
            throw new NullPointerException("the threadFactory is null");
        }
        if (tickDuration <= 0) {
            throw new IllegalArgumentException("tickDuration mast be greater than 0: now tickDuration " + tickDuration);
        }
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel mast be greater than 0: now ticksPerWheel " + ticksPerWheel);
        }
        // 以纳秒的方式计算
        this.tickDuration = TimeUnit.NANOSECONDS.convert(tickDuration, timeUnit);
        this.timeUnit = timeUnit;
        this.ticksPerWheel = ticksPerWheel;
        this.threadFactory = threadFactory;
        wheels = new Slot[ticksPerWheel];
    }

    /**
     * 启动时间轮
     */
    public void start() {
        if (WORKER_STATE.get()) {
            logger.warn("The TimeWheel is starting");
            return;
        }
        WORKER_STATE.compareAndSet(false, true);
        thread.start();
    }

    /**
     * 停止时间轮
     */
    public void stop() {
        if (!WORKER_STATE.get()) {
            logger.warn("The TimeWheel is stopped");
            return;
        }
        WORKER_STATE.compareAndSet(true, false);
        worker.shutdown();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public boolean addTimerTask(TimerTask timerTask, long delay, TimeUnit timeUnit) {
        // 计算到期时间
        long deadLine = TimeUnit.NANOSECONDS.convert(delay, this.timeUnit);
        if (deadLine < 0) {
            return false;
        }
        // 根据到期时间 计算放入哪个槽
        int idx = calculateIndex(deadLine);
        // 生成Timeout 对象
        Timeout timeout = new Timeout(timerTask, deadLine);
        return wheels[idx].add(timeout);
    }

    public boolean removeTimerTask(Timeout timeout) {
        int idx = calculateIndex(timeout.getExecuteTime());
        return wheels[idx].remove(timeout);
    }

    private int calculateIndex(long deadLine) {
        return (int) ((deadLine  / tickDuration) + currentTickIndex) % ticksPerWheel;
    }

    /**
     * 工作线程
     */
    private class Worker implements Runnable {
        private final int CORE_SIZE = Runtime.getRuntime().availableProcessors() * 4;
        private final int MAX_POOL_SIZE = CORE_SIZE * 2;
        private final int KEEP_LIVE = 5;
        private final TimeUnit TIMEUNIT = TimeUnit.MINUTES;
        private final BlockingQueue<Runnable> WORKER_QUEUE = new ArrayBlockingQueue<Runnable>(1000);
        // tick
        private long tick;
        private ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_SIZE, MAX_POOL_SIZE,
                KEEP_LIVE, TIMEUNIT, WORKER_QUEUE, threadFactory);

        @Override
        public void run() {
            startTime = System.nanoTime();
            tick = 0;
            while (WORKER_STATE.get()) {
                // 记录当前刻度
                currentTickIndex = tick;
                long deadLine = waitForNextTick();
                if (deadLine > 0) {
                    int idx = (int) (tick % ticksPerWheel);
                    Slot slot = wheels[idx];
                   // logger.info("当前可刻度{} 任务数{}", idx, print());
                    processedTimeouts(slot, deadLine);
                }
                tick++;
            }
        }

        String print() {
            String p = "";
            for (Slot slot : wheels) {
                p += slot.timeouts.size()+" ";
            }
            return p;
        }

        /**
         * 执行槽位中的方法
         *
         * @param slot 槽位
         * @return void
         */
        private void processedTimeouts(Slot slot, long deadLine) {
            slot.lock();
            try {
                // 得到过期的列表
                List<Timeout> timeouts = slot.getDeadLineTimeouts(deadLine);
                for (Timeout timeout : timeouts) {
                    executor.submit(timeout.getTimerTask());
                }
            } finally {
                slot.unlock();
            }
        }

        /**
         * 等待至下一个刻度
         */
        private long waitForNextTick() {
            long deadline = tickDuration * (tick + 1);
            for (; ; ) {
                final long currentTime = System.nanoTime() - startTime;
                long sleepTimeMs = (deadline - currentTime + 999999) / 1000000;
                if (sleepTimeMs <= 0) {
                    if (currentTime == Long.MIN_VALUE) {
                        return -Long.MAX_VALUE;
                    } else {
                        return currentTime;
                    }
                }

                if (PlatformDependent.isWindows()) {
                    sleepTimeMs = sleepTimeMs / 10 * 10;
                    if (sleepTimeMs == 0) {
                        sleepTimeMs = 1;
                    }
                }

                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException ignored) {
                    if (!WORKER_STATE.get()) {
                        return Long.MIN_VALUE;
                    }
                }
            }
        }

        public void shutdown() {
            executor.shutdown();
        }
    }

    /**
     * 时间轮单位
     */
    private class Slot {

        private List<Timeout> timeouts = new CopyOnWriteArrayList<>();
        private final AtomicBoolean SLOT_STATE = new AtomicBoolean(true);

        // 添加任务
        public boolean add(Timeout timeout) {
            if (!SLOT_STATE.get()) {
                return false;
            }
            timeouts.add(timeout);
            return true;
        }

        // 移除任务
        public boolean remove(Timeout timeout) {
            if (!SLOT_STATE.get()) {
                return false;
            }
            timeouts.remove(timeout);
            return true;
        }

        /**
         * 用空间换时间 不一个一个的删除
         * 得到过期的人物列表
         *
         * @param deadLine 过期时间
         * @return java.util.List<com.game.gameserver.module.timewheel.entity.TimeWheelTimer.Timeout>
         */
        List<Timeout> getDeadLineTimeouts(long deadLine) {
            List<Timeout> deadTimeouts = new ArrayList<>();
            List<Timeout> noDeadTimeouts = new ArrayList<>();
            for (Timeout timeout : timeouts) {
                if (timeout.getExecuteTime() > deadLine) {
                    noDeadTimeouts.add(timeout);
                } else {
                    deadTimeouts.add(timeout);
                }
            }
            timeouts = new CopyOnWriteArrayList<>(noDeadTimeouts);
            return deadTimeouts;
        }

        void lock() {
            SLOT_STATE.compareAndSet(true, false);
        }

        void unlock() {
            SLOT_STATE.compareAndSet(false, true);
        }

    }

    private class Timeout {
        private final TimerTask timerTask;
        private final long executeTime;

        public Timeout(TimerTask timerTask, long executeTime) {
            this.timerTask = timerTask;
            this.executeTime = executeTime;
        }

        public TimerTask getTimerTask() {
            return timerTask;
        }

        private long getExecuteTime() {
            return executeTime;
        }
    }

    public static void main(String[] args) {
        TimeWheelTimer wheelTimer = new TimeWheelTimer(1, 60, TimeUnit.SECONDS);
        Random random = new Random();
        new Thread(new Runnable() {
            @Override
            public void run() {
              while (true){
                  int time = random.nextInt(10);
                 // logger.info("添加一个任务 执行时间为{}:",time);
                  wheelTimer.addTimerTask(new TimerTask(),time,TimeUnit.SECONDS);
                  try {
                      Thread.sleep(100);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
            }
        },"随机线程1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    int time = random.nextInt(10);
                   // logger.info("添加一个任务 执行时间为{}:",time);
                    wheelTimer.addTimerTask(new TimerTask(),time,TimeUnit.SECONDS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"随机线程2").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    int time = random.nextInt(10);
                   // logger.info("添加一个任务 执行时间为{}:",time);
                    wheelTimer.addTimerTask(new TimerTask(),time,TimeUnit.SECONDS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"随机线程3").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    int time = random.nextInt(10);
                  //  logger.info("添加一个任务 执行时间为{}:",time);
                    wheelTimer.addTimerTask(new TimerTask(),time,TimeUnit.SECONDS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"随机线程4").start();new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    int time = random.nextInt(10);
                   // logger.info("添加一个任务 执行时间为{}:",time);
                    wheelTimer.addTimerTask(new TimerTask(),time,TimeUnit.SECONDS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"随机线程5").start();new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    int time = random.nextInt(10);
                   // logger.info("添加一个任务 执行时间为{}:",time);
                    wheelTimer.addTimerTask(new TimerTask(),time,TimeUnit.SECONDS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"随机线程6").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    int time = random.nextInt(10);
                  //  logger.info("添加一个任务 执行时间为{}:",time);
                    wheelTimer.addTimerTask(new TimerTask(),time,TimeUnit.SECONDS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"随机线程").start();


        wheelTimer.start();
    }
}
