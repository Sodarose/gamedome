package com.game.gameserver.net.server;

import com.game.gameserver.context.ServerContext;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.module.player.event.LostEvent;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.handler.MessageDispatcher;
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
        super.exceptionCaught(ctx,cause);
        logger.error("channel {} exception",ctx.channel().id());
        PlayerObject playerObject = ctx.channel().attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerObject!=null){
            LostEvent lostEvent = new LostEvent(playerObject.getPlayer().getId());
            EventBus.EVENT_BUS.fire(lostEvent);
        }
        ctx.channel().close();
    }

}
