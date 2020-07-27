package com.game.gameserver.net.modelhandler.friend;

import com.game.gameserver.module.friend.model.Friend;
import com.game.gameserver.module.friend.service.FriendService;
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
 * @date 2020/7/13 17:31
 */
@Component
@ModuleHandler(module = ModuleKey.FRIEND_MODULE)
public class FriendHandle extends BaseHandler {
    @Autowired
    private FriendService friendService;

    @CmdHandler(cmd = FriendCmd.SHOW_FRIEND)
    public void showFriend(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        friendService.showFriends(player);
    }

    @CmdHandler(cmd = FriendCmd.APPLY_FOR_FRIEND)
    public void applyForFriend(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        long playerId = Long.parseLong(message.getContent());
        friendService.applyForFriend(player,playerId);
    }

    @CmdHandler(cmd = FriendCmd.PROCESS_FRIEND_APPLY)
    public void processFriendApply(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        String[] param = message.getContent().split("\\s+");
        String playerName = param[0];
        int agree = Integer.parseInt(param[1]);
        friendService.processFriendApply(player,playerName,agree);
    }

    @CmdHandler(cmd = FriendCmd.REMOVE_FRIEND)
    public void removeFriend(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        long playerId = Long.parseLong(message.getContent());
        friendService.removeFriend(player,playerId);
    }

    @CmdHandler(cmd = FriendCmd.CHANGE_FRIEND_TYPE)
    public void changeFriendType(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        String[] param = message.getContent().split("\\s+");
        long playerId = Long.parseLong(param[0]);
        int type = Integer.parseInt(param[1]);
        friendService.changeFriendType(player,playerId,type);
    }
}
