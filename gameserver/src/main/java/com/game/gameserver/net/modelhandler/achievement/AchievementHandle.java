/*
package com.game.gameserver.net.modelhandler.achievement;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.AchievementProtocol;
import com.game.message.Message;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

*/
/**
 * @author xuewenkang
 * @date 2020/7/2 15:42
 *//*

@Component
@ModuleHandler(module = ModuleKey.ACHIEVEMENT_MODULE)
public class AchievementHandle extends BaseHandler {

    @Autowired
    private AchievementService achievementService;

    */
/**
     * 处理查询所有成就请求
     *
     * @param message
     * @param channel
     * @return void
     *//*

    @CmdHandler(cmd = AchievementCmd.QUERY_ALL_ACHIEVEMENT)
    public void handleQueryAllAchievementReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        AchievementProtocol.QueryAchievementListRes res = achievementService.queryAllAchievementMsg();
        Message resMsg = MessageUtil.createMessage(ModuleKey.ACHIEVEMENT_MODULE,
                AchievementCmd.QUERY_ALL_ACHIEVEMENT, res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    */
/**
     * 处理查询用户成就请求
     *
     * @param message
     * @param channel
     * @return void
     *//*

    @CmdHandler(cmd = AchievementCmd.QUERY_PLAYER_ACHIEVEMENT)
    public void handleQueryPlayerAchievementReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        AchievementProtocol.QueryPlayerAchievementListRes res = achievementService
                .queryPlayerAchievementList(player.getId());
        Message resMsg = MessageUtil.createMessage(ModuleKey.ACHIEVEMENT_MODULE,AchievementCmd.QUERY_PLAYER_ACHIEVEMENT,
                res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    */
/**
     * 处理提交成就请求
     *
     * @param message
     * @param channel
     * @return void
     *//*

    @CmdHandler(cmd = AchievementCmd.SUBMIT_ACHIEVEMENT)
    public void handleSubmitAchievementReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            AchievementProtocol.SubmitAchievementReq req = AchievementProtocol
                    .SubmitAchievementReq.parseFrom(message.getData());
            AchievementProtocol.SubmitAchievementRes res = achievementService.submitAchievement(player.getId(),
                    req.getAchievementId());
            Message resMsg = MessageUtil.createMessage(ModuleKey.ACHIEVEMENT_MODULE,AchievementCmd.SUBMIT_ACHIEVEMENT,
                    res.toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
*/
