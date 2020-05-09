package com.game.gameserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xuewenkang
 */
@Component
public class GameServer {
    private final Logger logger = LoggerFactory.getLogger(GameServer.class);

    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ServerBootstrap server;

    @Value("${gameserver.port}")
    private Integer port;
    @Value("${gameserver.name}")
    private String serverName;


    @PostConstruct
    public void start(){
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup(16);
        server = new ServerBootstrap();
        try {
            server
                    .channel(NioServerSocketChannel.class)
                    .group(boss, worker)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {

                        }
                    });
            ChannelFuture future = server.bind(port).addListener((ChannelFutureListener) f->{
                if(f.isSuccess()){
                    logger.info("game server is successfully to {},waiting connect....",port);
                }else{
                    logger.info("game server start failed");
                }
            });
            future.channel().closeFuture().sync();
        }catch (Exception e){
            logger.info("game server start failed");
            e.printStackTrace();
        }finally {
            logger.info("game server end");
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    @PreDestroy
    public void destroy(){
        if(boss!=null){
            boss.shutdownGracefully();
        }
        if(worker!=null){
            worker.shutdownGracefully();
        }
        logger.info("game server end");
    }
}
