package com.game.gameserver.thread;

import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * unit tick 线程
 *
 * @author xuewenkang
 * @date 2020/6/4 15:35
 */
public class UnitTickThread implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(UnitTickThread.class);

    private final long tickDuration;
    private final TimeUnit timeUnit;
    private long tick;
    private long startTime;
    private Thread tickThread;
    private final AtomicBoolean WORKER_STATE = new AtomicBoolean(false);

    public UnitTickThread(long tickDuration, TimeUnit timeUnit) {
        this.tickDuration = TimeUnit.NANOSECONDS.convert(tickDuration, timeUnit);
        this.timeUnit = timeUnit;
        tickThread = new Thread(this, "Unit tick thread");
    }

    public void start() {
        if (WORKER_STATE.get()) {
            logger.warn("Unit tick thread is started");
            return;
        }
        WORKER_STATE.compareAndSet(false, true);
        tickThread.start();
    }

    public void stop() {
        if (!WORKER_STATE.get()) {
            logger.warn("Unit tick thread is stopped");
            return;
        }
    }

    @Override
    public void run() {
        startTime = System.nanoTime();
        tick = 0;
        while (WORKER_STATE.get()) {
            waitForNextTick();
            update();
            tick++;
        }
    }

    private long waitForNextTick() {
        // 下一次tick的时间
        long deadline = tickDuration * (tick + 1);
        for (; ; ) {
            final long currentTime = System.nanoTime() - startTime;
            // 需要等待的时间
            long sleepTimeMs = (deadline - currentTime + 999999) / 1000000;
            if (sleepTimeMs <= 0) {
                if (currentTime == Long.MIN_VALUE) {
                    return -Long.MAX_VALUE;
                } else {
                    // 返回当前时间
                    return currentTime;
                }
            }

            // 如果是windows 系统则需要换算
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

    private void update() {
    /*    synchronized (BaseUnit.UNIT_MAP) {
            BaseUnit.UNIT_MAP.forEach((integer, unit) -> {
                try {
                    unit.update();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }*/
    }
}
