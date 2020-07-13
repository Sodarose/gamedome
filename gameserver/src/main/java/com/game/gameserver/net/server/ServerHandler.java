package com.game.gameserver.net.server;

import com.game.gameserver.context.ServerContext;
import com.game.gameserver.net.handler.MessageDispatcher;
import com.game.message.Message;
import com.game.protocol.CmdProto;
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
        CmdProto.CmdMsg cmdMsg =  (CmdProto.CmdMsg) msg;
        Message message1 = Message.buildMsg(cmdMsg);
        try {
            MessageDispatcher messageDispatcher = ServerContext.getApplication()
                    .getBean(MessageDispatcher.class);
            messageDispatcher.dispatch(message1,ctx.channel());
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
        super.exceptionCaught(ctx,cause);
        logger.error("channel {} exception",ctx.channel().id());
        ctx.channel().close();
    }

}
