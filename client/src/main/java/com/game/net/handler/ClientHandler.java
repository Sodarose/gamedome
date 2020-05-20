package com.game.net.handler;

import com.game.game.context.ClientSpringContext;
import com.game.protocol.Message;
import com.game.task.MessageDispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 */
@Component
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Autowired
    ClientSpringContext clientSpringContext;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("连接已接通 Channel ID {}",ctx.channel().id());
        clientSpringContext.setChannel(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("client accept message {}",msg);
        Message message = (Message) msg;
        messageDispatcher.dispatch(message,ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
