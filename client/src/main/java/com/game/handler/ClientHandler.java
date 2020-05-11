package com.game.handler;

import com.game.client.Client;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 */
@Component
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("连接已接通 Channel ID {}",ctx.channel().id());
        super.channelActive(ctx);
    }
}
