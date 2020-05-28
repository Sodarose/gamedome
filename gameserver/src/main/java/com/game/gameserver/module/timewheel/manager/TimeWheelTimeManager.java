package com.game.gameserver.module.timewheel.manager;

import com.game.gameserver.module.timewheel.entity.TimeWheelTimer;
import com.game.gameserver.module.timewheel.entity.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/5/28 17:11
 */
@Component
public class TimeWheelTimeManager {
    private final static Logger logger = LoggerFactory.getLogger(TimeWheelTimeManager.class);

    private TimeWheelTimer timeWheelTimer = new TimeWheelTimer(1,60, TimeUnit.SECONDS);

    /** 启动时间轮 */
    public void start(){
        logger.info("启动时间轮");
        timeWheelTimer.start();
    }

    public boolean addTimerTask(TimerTask timerTask){
        return false;
    }
}
