package com.game.gameserver.handler;

import com.game.gameserver.context.ServerContext;
import com.game.protocol.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

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
            logger.info("messageDispatcher {}",messageDispatcher);
            messageDispatcher.dispatch(message);
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
        super.channelInactive(ctx);
        //ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx,cause);
        //ctx.channel().close();
        cause.printStackTrace();
    }
}
