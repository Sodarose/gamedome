package com.game.gameserver.module.timewheel.entity;

import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/5/28 14:43
 */
public  class TimerTask implements Runnable {
    private volatile boolean cancel = false;
    @Override
    public void run() {

    }

    public boolean isCancel(){
        return cancel;
    }
}
