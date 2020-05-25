package com.game.net.client;

import com.game.module.gui.GameClientPage;
import com.game.net.handler.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author xuwenkang
 * @date: 2020/5/13 11:47
 */
@Component
public class GameClient {

    private Logger logger = LoggerFactory.getLogger(GameClient.class);

    private String host = "127.0.0.1";
    private Integer port = 11211;
    private EventLoopGroup worker;

    @Autowired
    ClientChannelInitializer initializer;

    @Autowired
    private GameClientPage page;

    @PostConstruct
    public void start(){
        logger.info("loading ......");
        page.showLoadPage();
        worker = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        try {
            client
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(initializer);

            ChannelFuture future = client
                    .connect(new InetSocketAddress(host,port))
                    .addListener((ChannelFutureListener) f->{
                        if(f.isSuccess()){
                            logger.info("game client successfully start......");
                            page.showLoginPage();
                        }else{
                            page.showTextPage("connect failed .....");
                        }
                    });
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
            logger.info("game client end");logger.info("game client end");
        }
    }

    @PreDestroy
    public void destroy(){
        if(worker!=null){
            worker.shutdownGracefully();
        }
        logger.info("game client end......");
    }

}
