package com.game.gameserver.module.notification;

import com.game.gameserver.module.backbag.helper.BackBagHelper;
import com.game.gameserver.module.equipment.helper.EquipHelper;
import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.module.instance.helper.InstanceHelper;
import com.game.gameserver.module.instance.model.Instance;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.helper.PlayerHelper;
import com.game.gameserver.module.scene.helper.SceneHelper;
import com.game.gameserver.module.scene.model.GameScene;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.team.model.Team;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.backbag.BackBagCmd;
import com.game.gameserver.net.modelhandler.equipment.EquipCmd;
import com.game.gameserver.net.modelhandler.instance.InstanceCmd;
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

    public static void notifyPlayer(Player player, String content) {
        Message message = new Message(content);
        CmdProto.CmdMsg  cmdMsg = Message.buildCmdProtoCmdMsg(message);
        player.getChannel().writeAndFlush(cmdMsg);
    }

    public static void notifyPlayer(Player player, int module, int cmd, String content) {

    }


    public static void notifyScene(Scene scene, String content) {
        Message message = new Message(content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        scene.getPlayerMap().values().forEach(player -> {
            player.getChannel().writeAndFlush(cmdMsg);
        });
    }

    /**
     * 同步场景数据
     *
     * @param scene
     * @return void
     */
    public static void syncScene(GameScene scene){
        String content = SceneHelper.buildScene(scene);
        Message message = new Message(ModuleKey.SCENE_MODULE, SceneCmd.SYNC,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        scene.getPlayerMap().values().forEach(
                player -> {
                    player.getChannel().writeAndFlush(cmdMsg);
                }
        );
    }

    public static void syncInstance(Instance instance){
        String content = InstanceHelper.buildInstanceSceneMsg(instance);
        Message message = new Message(ModuleKey.INSTANCE_MODULE, InstanceCmd.SYNC,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        instance.getCheckPoint().getPlayerMap().values().forEach(
                player -> {
                    player.getChannel().writeAndFlush(cmdMsg);
                }
        );
    }

    /**
     * 同步角色数据
     *
     * @param
     * @return void
     */
    public static void syncPlayer(Player player){
        String content = PlayerHelper.buildplayer(player);
        Message message = new Message(ModuleKey.PLAYER_MODULE, PlayerCmd.SHOW_PLAYER,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        player.getChannel().writeAndFlush(cmdMsg);
    }

    /**
     * 同步装备数据
     *
     * @param
     * @return void
     */
    public static void syncEquipBar(Player player){
        String content = EquipHelper.buildEquipBar(player.getEquipBar());
        Message message = new Message(ModuleKey.EQUIP_MODULE, EquipCmd.SYNC,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        player.getChannel().writeAndFlush(cmdMsg);
    }

    /**
     * 同步背包数据
     *
     * @param
     * @return void
     */
    public static void syncBackBag(Player player){
        String content = BackBagHelper.buildPlayerBackBag(player.getBackBag());
        Message message = new Message(ModuleKey.BACK_BAG_MODULE, BackBagCmd.SYNC,content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        player.getChannel().writeAndFlush(cmdMsg);
    }

    public static void notifyTeam(Team team,String content){
        Message message = new Message(content);
        CmdProto.CmdMsg cmdMsg = Message.buildCmdProtoCmdMsg(message);
        team.getMemberMap().values().forEach(player -> {
            player.getChannel().writeAndFlush(cmdMsg);
        });
    }

    public static void notifyGuild(Guild guild, String content) {
        guild.getMemberMap().forEach((key,value)->{
            Player player = PlayerManager.instance.getPlayer(value.getPlayerId());
            if(player!=null){
                notifyPlayer(player,content);
            }
        });
    }

}
