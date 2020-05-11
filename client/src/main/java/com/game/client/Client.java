package com.game.client;

import com.game.handler.ClientChannelInitializer;
import com.game.page.*;
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
 */
@Component
public class Client {
    private Logger logger = LoggerFactory.getLogger(Client.class);

    private String host = "127.0.0.1";
    private Integer port = 11211;
    private EventLoopGroup worker;

    @Autowired
    private GameFrame gameFrame;

    @Autowired
    private MainPage mainPage;

    @Autowired
    ClientChannelInitializer initializer;

    @Autowired
    private LoginAndRegisterPage loginAndRegisterPage;

    @PostConstruct
    public void start(){
        logger.info("loading ......");
        LoadPage loadPage = new LoadPage();
        gameFrame.setMainPanel(loadPage);
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
                            gameFrame.setMainPanel(loginAndRegisterPage);
                        }else{
                            TextPage textPage = new TextPage("connect failed ......");
                            gameFrame.setMainPanel(textPage);
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
