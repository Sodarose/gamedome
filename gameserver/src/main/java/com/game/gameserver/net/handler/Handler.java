package com.game.gameserver.net.handler;

import com.game.protocol.Message;
import io.netty.channel.Channel;

/**
 *
 * @author xuewenkang
 * @date 2020/5/24 16:11
 */
public interface Handler {

    void dispatcher(Message message, Channel channel);
}
