package com.game.gameserver.net.modelhandler.achievement;

import com.game.gameserver.module.achievement.service.AchievementService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/7/22 17:20
 */
@Component
@ModuleHandler(module = ModuleKey.ACHIEVEMENT_MODULE)
public class AchievementHandle extends BaseHandler {
    @Autowired
    private AchievementService achievementService;

    @CmdHandler(cmd = AchievementCmd.SHOW_ACHIEVEMENT)
    public void showAchievement(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        achievementService.showAchievement(player);
    }

    @CmdHandler(cmd = AchievementCmd.SUBMIT_ACHIEVEMENT)
    public void submitAchievement(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int achievementId = Integer.parseInt(message.getContent());
        achievementService.submitAchievement(player,achievementId);
    }
}
