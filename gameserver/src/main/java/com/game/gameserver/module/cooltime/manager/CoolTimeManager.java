package com.game.gameserver.module.cooltime.manager;

import com.game.gameserver.module.cooltime.entity.CoolTime;
import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xuewenkang
 * @date 2020/6/3 13:59
 */
@Component
public class CoolTimeManager {
    private final static Logger logger = LoggerFactory.getLogger(CoolTimeManager.class);

    private long tickDuration;
    private TimeUnit timeUnit;
    private long tick;
    private long startTime;
    private Thread tickThread;
    private Worker worker;
    private final AtomicBoolean WORKER_STATE = new AtomicBoolean(false);
    private final ArrayBlockingQueue<CoolTime> blockingQueue = new ArrayBlockingQueue<>(1000);
    private LinkedList<CoolTime> coolTimeList = new LinkedList<>();


    public void init(long tickDuration, TimeUnit timeUnit) {
        this.tickDuration = TimeUnit.NANOSECONDS.convert(tickDuration, timeUnit);
        this.timeUnit = timeUnit;
        this.worker = new Worker();
        this.tickThread = new Thread(worker, "CD tick thread");
    }

    public void start() {
        if (WORKER_STATE.get()) {
            logger.info("CD tick is started");
            return;
        }
        WORKER_STATE.compareAndSet(false, true);
        tickThread.start();
    }

    public void stop() {
        if (!WORKER_STATE.get()) {
            logger.info("CD tick is stop");
            return;
        }
        WORKER_STATE.compareAndSet(true, false);
        tickThread.interrupt();
    }

    public void addCoolTime(CoolTime coolTime) {
        blockingQueue.add(coolTime);
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            startTime = System.nanoTime();
            tick = 0;
            while (WORKER_STATE.get()) {
                long deadline = waitForNextTick();
                if (deadline > 0) {
                    update();
                }
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

    }

    private void update() {
/*        Object[] coolTimeObject = blockingQueue.toArray();
        blockingQueue.clear();
        for (Object c : coolTimeObject) {
            coolTimeList.add((CoolTime) c);
        }
        Iterator<CoolTime> it = coolTimeList.iterator();
        while (it.hasNext()) {
            CoolTime coolTime = it.next();
            coolTime.update();
            if (coolTime.isExpire()) {
                it.remove();
            }
        }*/
    }

}