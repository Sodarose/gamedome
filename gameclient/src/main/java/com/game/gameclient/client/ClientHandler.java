package com.game.gameclient.client;

import com.game.gameclient.context.GameClientContext;
import com.game.message.Message;
import com.game.protocol.CmdProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuewenkang
 * @date 2020/7/10 17:33
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("连接已接通 Channel ID {}",ctx.channel().id());
        GameClientContext.ctx = ctx;
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CmdProto.CmdMsg cmdMsg = (CmdProto.CmdMsg) msg;
        Message message = Message.buildMsg(cmdMsg);
        MessageHandle.handle(message);
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
