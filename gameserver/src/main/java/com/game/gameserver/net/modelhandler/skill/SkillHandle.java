package com.game.gameserver.net.modelhandler.skill;

import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.skill.service.SkillService;
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
 * @date 2020/6/4 20:34
 */
@Component
@ModuleHandler(module = ModuleKey.SKILL_MODULE)
public class SkillHandle extends BaseHandler {
    @Autowired
    private SkillService skillService;

    @CmdHandler(cmd = SkillCmd.SHOW_CAREER_SKILL)
    public void showCareerSkill(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        skillService.showCareerSkill(player);
    }

    @CmdHandler(cmd = SkillCmd.SHOW_SKILL)
    public void showSkill(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        skillService.showSkill(player);
    }

    @CmdHandler(cmd = SkillCmd.LEARN_SKILL)
    public void learnSkill(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int skillId = Integer.parseInt(message.getContent());
        skillService.learnSkill(player,skillId);
    }

    @CmdHandler(cmd = SkillCmd.FORGET_SKILL)
    public void forgetSkill(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int skillId = Integer.parseInt(message.getContent());
        skillService.forgetSkill(player,skillId);
    }

}
