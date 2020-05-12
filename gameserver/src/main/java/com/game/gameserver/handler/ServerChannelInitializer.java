package com.game.gameserver.handler;

import com.game.handler.MessageDecode;
import com.game.handler.MessageEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author xuewenkang
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new MessageDecode(1024*1024,0,4,-4,0));
        ch.pipeline().addLast(new MessageEncode());
        ch.pipeline().addLast(new ServerHandler());
    }
}
