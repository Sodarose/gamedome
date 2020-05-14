package com.game.gameserver.net.handler;

import com.game.gameserver.game.context.ServerContext;
import com.game.gameserver.game.service.AbstractGameService;
import com.game.gameserver.game.service.impl.GameServiceImpl;
import com.game.gameserver.task.MessageDispatcher;
import com.game.protocol.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuewenkang
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("server accept message {}",msg);
        Message message = (Message) msg;
       try {
            MessageDispatcher messageDispatcher = ServerContext.getApplication()
                    .getBean(MessageDispatcher.class);
            messageDispatcher.dispatch(message,ctx.channel());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("有连接接通 Channel Id {}",ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel {} inactive ",ctx.channel().id());
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("channel {} exception",ctx.channel().id());
        GameServiceImpl gameService = ServerContext.getApplication().getBean(GameServiceImpl.class);
        gameService.handleExit(null,ctx.channel());
        ctx.channel().close();
    }
}
