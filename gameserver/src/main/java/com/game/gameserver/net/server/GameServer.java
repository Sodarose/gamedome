package com.game.gameserver.net.server;

import com.game.gameserver.context.Platform;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 游戏服务器
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

    @Autowired
    private Platform platform;

    @PostConstruct
    public void start(){
        logger.info("game server start......");
        // 临时做 后面会改动
        platform.startUp();
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup(16);
        server = new ServerBootstrap();
        try {
            server
                    .channel(NioServerSocketChannel.class)
                    .group(boss, worker)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler())
                    .childHandler(new ServerChannelInitializer());
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
