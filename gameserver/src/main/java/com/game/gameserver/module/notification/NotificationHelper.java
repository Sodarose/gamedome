package com.game.gameserver.module.notification;

import com.game.gameserver.module.backbag.helper.BackBagHelper;
import com.game.gameserver.module.equipment.helper.EquipHelper;
import com.game.gameserver.module.guild.domain.GuildDomain;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.helper.PlayerHelper;
import com.game.gameserver.module.scene.helper.SceneHelper;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.team.model.Team;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.backbag.BackBagCmd;
import com.game.gameserver.net.modelhandler.equipment.EquipCmd;
import com.game.gameserver.net.modelhandler.player.PlayerCmd;
import com.game.gameserver.net.modelhandler.scene.SceneCmd;
import com.game.message.Message;
import com.game.protocol.CmdProto;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通知类 发送通知消息
 *
 * @author xuewenkang
 * @date 2020/7/9 2:22
 */
public class NotificationHelper {
    private static final Logger logger = LoggerFactory.getLogger(NotificationHelper.class);

    public static void notifyChannel(Channel channel, String content) {
        Message message = new Message(content);
        CmdProto.CmdMsg  cmdMsg = Message.buildCmdProtoCmdMsg(message);
        channel.writeAndFlush(cmdMsg);
    }

    public static void notifyChannel(Channel channel, int module, int cmd, String content) {
        Message message = new Message();
    }

    public static void notifyPlayer(Player playerDomain, String content) {
        Message message = new Message(content);
        CmdProto.CmdMsg  cmdMsg = Message.buildCmdProtoCmdMsg(message);
        playerDomain.getChannel().writeAndFlush(cmdMsg);
    }

    public static void notifyPlayer(Player playerDomain, int module, int cmd, String content) {

    }

    public static void notifyGuild(GuildDomain guildDomain, String content) {

    }

    public static void notifyScene(Scene scene, String content) {
        Message message = new Message(content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        scene.getPlayerMap().values().forEach(playerDomain -> {
            playerDomain.getChannel().writeAndFlush(cmdMsg);
        });
    }

    /**
     * 同步场景数据
     *
     * @param scene
     * @return void
     */
    public static void syncScene(Scene scene){
        String content = SceneHelper.buildScene(scene);
        Message message = new Message(ModuleKey.SCENE_MODULE, SceneCmd.SYNC,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        scene.getPlayerMap().values().forEach(
                playerDomain -> {
                    playerDomain.getChannel().writeAndFlush(cmdMsg);
                }
        );
    }

    /**
     * 同步角色数据
     *
     * @param
     * @return void
     */
    public static void syncPlayer(Player playerDomain){
        String content = PlayerHelper.buildPlayerDomain(playerDomain);
        Message message = new Message(ModuleKey.PLAYER_MODULE, PlayerCmd.SHOW_PLAYER,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        playerDomain.getChannel().writeAndFlush(cmdMsg);
    }

    /**
     * 同步装备数据
     *
     * @param
     * @return void
     */
    public static void syncPlayerEquipBar(Player playerDomain){
        String content = EquipHelper.buildEquipBar(playerDomain.getEquipBar());
        Message message = new Message(ModuleKey.EQUIP_MODULE, EquipCmd.SYNC,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        playerDomain.getChannel().writeAndFlush(cmdMsg);
    }

    /**
     * 同步背包数据
     *
     * @param
     * @return void
     */
    public static void syncPlayerBackBag(Player playerDomain){
        String content = BackBagHelper.buildPlayerBackBag(playerDomain.getBackBag());
        Message message = new Message(ModuleKey.BACK_BAG_MODULE, BackBagCmd.SYNC,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        playerDomain.getChannel().writeAndFlush(cmdMsg);
    }

    public static void notifyTeam(Team team,String content){
        Message message = new Message(content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        team.getMemberMap().values().forEach(playerDomain -> {
            playerDomain.getChannel().writeAndFlush(cmdMsg);
        });
    }
}
