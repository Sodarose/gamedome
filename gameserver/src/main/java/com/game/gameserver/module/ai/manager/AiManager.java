package com.game.gameserver.module.ai.manager;

import com.game.gameserver.thread.DefaultThreadFactory;
/*import com.game.gameserver.module.pet.manager.PetManager;*/
import com.game.gameserver.module.player.manager.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * AI管理器
 *
 * @author xuewenkang
 * @date 2020/6/22 21:21
 */
@Component
public class AiManager {
    private final static Logger logger = LoggerFactory.getLogger(AiManager.class);
    @Autowired
    private PlayerManager playerManager;
 /*   @Autowired
    private PetManager petManager;*/

    private ScheduledExecutorService scheduledExecutorService;
    private Worker  worker;

    public void startInstanceWorker() {
        if (scheduledExecutorService != null) {
            return;
        }
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new
                DefaultThreadFactory("AI Worker"));
        worker = new Worker();
        scheduledExecutorService.scheduleAtFixedRate(worker, 0, 500, TimeUnit.MILLISECONDS);
    }

    private class Worker implements Runnable{
        @Override
        public void run() {
            try {

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
