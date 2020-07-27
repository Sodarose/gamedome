package com.game.gameserver.module.cooltime.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.entity.CreatureType;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.skill.model.Skill;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/7/16 14:30
 */
@Service
public class CoolTimeService {


    /**
     * CD 定时器线程
     */
    private final static ThreadFactory COOL_TIME_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("Buffer_Thread-%d").setUncaughtExceptionHandler((t, e) -> e.printStackTrace()).build();

    /**
     * 冷却定时器
     */
    private final static ScheduledThreadPoolExecutor COOL_TIME_THREAD = new ScheduledThreadPoolExecutor(2
            , COOL_TIME_THREAD_FACTORY);

    /**
     * 技能进入CD
     *
     * @param creature
     * @param skill
     * @return void
     */
    public void skillInCd(Creature creature, Skill skill) {
        SkillConfig skillConfig = skill.getSkillConfig();
        // 技能拥有冷却时间
        if(skillConfig.getCoolTime()>0){
            creature.getInCdSkill().put(skill.getSkillId(),skill);
            COOL_TIME_THREAD.schedule(
                    ()->{
                        creature.getInCdSkill().remove(skill.getSkillId());
                        if(creature.getType()== CreatureType.PLAYER){
                            NotificationHelper.notifyPlayer((Player)
                                    creature, MessageFormat.format("技能{0}冷却完毕",
                                    skillConfig.getName()));
                        }
                    },skillConfig.getCoolTime(),TimeUnit.SECONDS
            );
        }
    }

    /**
     * 道具进入CD
     *
     * @param player
     * @param item
     * @return void
     */
    public void itemInCd(Player player, Item item) {
        // 道具进入CD
        player.getItemCdMap().put(item.getItemConfigId(), item);
        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(item.getItemConfigId());
        // 根据CD时长 放入CD 定时器
        COOL_TIME_THREAD.schedule(() -> {
            // 时间到后 将该道具移除cd表
            player.getItemCdMap().remove(item.getItemConfigId());
            NotificationHelper.notifyPlayer(player, MessageFormat.format("道具{0}冷却完毕",
                    itemConfig.getName()));
        }, itemConfig.getCoolTime(), TimeUnit.MILLISECONDS);
    }
}
