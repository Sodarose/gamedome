package com.game.gameserver.module.chat.service.impl;

import com.game.gameserver.module.chat.entity.ChatChannel;
import com.game.gameserver.module.chat.manager.ChatManager;
import com.game.gameserver.module.chat.service.ChatService;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.model.SceneObject;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.chat.ChatCmd;
import com.game.protocol.ChatProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * 聊天服务
 *
 * @author xuewenkang
 * @date 2020/6/10 20:08
 */
@Service
public class ChatServiceImpl implements ChatService {


    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private ChatManager chatManager;
    @Autowired
    private SceneManager sceneManager;
    /**
     * 频道消息
     *
     * @param playerObject
     * @param channelMsg
     * @return void
     */
    @Override
    public void sendChannelMsg(PlayerObject playerObject, ChatProtocol.ChannelMsg channelMsg) {
        int channelType = channelMsg.getChannelType();
        // 获取频道类型
        Long channelId = playerObject.getPlayerChannelMap().get(channelType);
        // 获取聊天频道
        ChatChannel chatChannel = chatManager.getChatChannel(channelId);
        Lock readLock = chatChannel.getReadLock();
        readLock.lock();
        try{
            // 获取频道内成员
            List<Long> players = chatChannel.getPlayers();
            for(Long id:players){
                if(id.equals(playerObject.getPlayer().getId())){
                    continue;
                }
                PlayerObject target = playerManager.getPlayerObject(id);
                if(target==null){
                    continue;
                }
                // 构建消息
                Message message = MessageUtil.createMessage(ModuleKey.CHAT_MODULE,
                        ChatCmd.RECEIVE_CHANNEL_MSG,channelMsg.toByteArray());
                // 发送消息
                target.getChannel().writeAndFlush(message);
            }
        }finally {
            readLock.unlock();
        }
    }

    /**
     * 私聊消息
     *
     * @param playerObject
     * @param privacyMsg
     * @return void
     */
    @Override
    public void sendPrivacyMsg(PlayerObject playerObject, ChatProtocol.PrivacyMsg privacyMsg) {
        Long receiverId = privacyMsg.getReceiverId();
        // 私聊对象
        PlayerObject target = playerManager.getPlayerObject(receiverId);
        if(target==null){
            return;
        }
        // 构建消息
        Message message = MessageUtil.createMessage(ModuleKey.CHAT_MODULE,ChatCmd.RECEIVE_PRIVACY_MSG,
                privacyMsg.toByteArray());
        // 发送消息
        target.getChannel().writeAndFlush(message);
    }

    /**
     * 普通消息 (以角色周围玩家为对象，发送消息)
     * 暂时以场景为范围
     * @param playerObject
     * @param commonMsg
     * @return void
     */
    @Override
    public void sendCommonMsg(PlayerObject playerObject, ChatProtocol.CommonMsg commonMsg) {
        Long sceneId = playerObject.getSceneId();
        if(sceneId==null){
            return;
        }
        SceneObject sceneObject = sceneManager.getSceneObject(sceneId);
        if(sceneObject==null){
            return;
        }
        for(Map.Entry<Long,PlayerObject> entry:sceneObject.getPlayerObjectMap().entrySet()){
            // 不需要发给自己
            if(entry.getKey().equals(playerObject.getPlayer().getId())){
                continue;
            }
            Message message = MessageUtil.createMessage(ModuleKey.CHAT_MODULE,
                    ChatCmd.RECEIVE_COMMON_MSG,commonMsg.toByteArray());
            entry.getValue().getChannel().writeAndFlush(message);
        }
    }
}
