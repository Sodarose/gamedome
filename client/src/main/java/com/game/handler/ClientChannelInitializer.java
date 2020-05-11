package com.game.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuwenkang
 */
@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ClientHandler clientHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(clientHandler);
    }
}
