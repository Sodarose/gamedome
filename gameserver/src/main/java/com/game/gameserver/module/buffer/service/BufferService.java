package com.game.gameserver.module.buffer.service;

import com.game.gameserver.common.config.BufferConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.entity.CreatureState;
import com.game.gameserver.common.entity.CreatureType;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.*;

/**
 * buffer 服务
 *
 * @author xuewenkang
 * @date 2020/6/11 10:58
 */
@Service
public class BufferService {

    /**
     * Buffer定时器线程
     */
    private final static ThreadFactory BUFFER_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("Buffer_Thread-%d").setUncaughtExceptionHandler((t, e) -> e.printStackTrace()).build();

    /**
     * Buffer线程
     */
    private final static ScheduledThreadPoolExecutor BUFFER_THREAD = new ScheduledThreadPoolExecutor(2
            , BUFFER_THREAD_FACTORY);

    /**
     * 添加一个buffer到活动物品
     *
     * @param creature
     * @param bufferId
     * @return void
     */
    public void addBuffer(Creature creature, int bufferId) {
        BufferConfig bufferConfig = StaticConfigManager.getInstance().getBufferConfigMap().get(bufferId);
        if (bufferConfig == null) {
            return;
        }
        // 创建一个Buffer
        Buffer buffer = new Buffer(bufferConfig);
        // 放入活物的buffer列表
        creature.getBuffers().add(buffer);
        // 启动buffer
        startBuffer(creature, buffer);
        if (creature.getType().equals(CreatureType.PLAYER)) {
            Player player = (Player) creature;
            NotificationHelper.notifyPlayer(player, MessageFormat.format("玩家获得buffer:{0}",
                    buffer.getBufferConfig().getName()));
        }
    }

    /**
     * 启动Buffer
     *
     * @param creature
     * @param buffer
     * @return void
     */
    private void startBuffer(Creature creature, Buffer buffer) {
        // 设定buffer 开始时间
        buffer.setStartTime(System.currentTimeMillis());
        // 判断buffer持续时间
        if (buffer.getBufferConfig().getDuration() > 0) {
            Future runFuture = BUFFER_THREAD.scheduleAtFixedRate(() -> {
                        // 执行方法
                        runBuffer(creature, buffer);
                    }, 500,
                    buffer.getBufferConfig().getIntervalTime(), TimeUnit.MILLISECONDS);
            // 设置 结束
            Future cancelFuture = BUFFER_THREAD.schedule(() -> {
                try {
                    runFuture.cancel(true);
                    creature.getBuffers().remove(buffer);
                    if (creature.getType().equals(CreatureType.PLAYER)) {
                        Player player = (Player) creature;
                        NotificationHelper.notifyPlayer(player, MessageFormat.format("玩家buffer结束:{0}",
                                buffer.getBufferConfig().getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, buffer.getBufferConfig().getDuration(), TimeUnit.MILLISECONDS);
            buffer.setRunFuture(runFuture);
            buffer.setCancelFuture(cancelFuture);
        }
    }

    /**
     * 执行buffer
     *
     * @param creature
     * @param buffer
     * @return void
     */
    private void runBuffer(Creature creature, Buffer buffer) {
        // 目标死亡 buffer 不再生效
        if(creature.isDead()){
            return;
        }
        // 判断执行次数是否超过预设值
        if (buffer.getTimes() >= buffer.getBufferConfig().getTimes()) {
            return;
        }
        buffer.addTimes(1);
        // 设置buffer 效果
        if(buffer.getBufferConfig().getEffect()!=-1){

        }
        // hp mp 效果
        creature.setCurrHp(creature.getCurrHp()+buffer.getBufferConfig().getHp());
        creature.setCurrMp(creature.getCurrMp()+buffer.getBufferConfig().getMp());
        if (creature.getType().equals(CreatureType.PLAYER)) {
            Player player = (Player) creature;
            NotificationHelper.notifyPlayer(player, MessageFormat.format("hp效果:{0}  mp效果:{1}",
                    buffer.getBufferConfig().getHp(),
                    buffer.getBufferConfig().getMp()));
        }
        if(creature.getCurrHp()<=0){
            creature.setDead(true);
            creature.setCurrHp(0);
        }
    }
}
