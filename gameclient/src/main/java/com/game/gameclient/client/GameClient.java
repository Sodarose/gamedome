package com.game.gameclient.client;

import com.game.gameclient.view.ClientView;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author xuewenkang
 * @date 2020/7/10 17:31
 */
public class GameClient {
    private Logger logger = LoggerFactory.getLogger(GameClient.class);

    private String host = "127.0.0.1";
    private Integer port = 11211;
    private EventLoopGroup worker;

    public void run(){
        logger.info("loading ......");
        worker = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        try {
            client
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientChannelInitializer());
            ClientView.MSG_PAGE.append("连接服务器......\n");
            ChannelFuture future = client
                    .connect(new InetSocketAddress(host,port))
                    .addListener((ChannelFutureListener) f->{
                        if(f.isSuccess()){
                            logger.info("game client successfully start......");
                            ClientView.MSG_PAGE.append("连接服务器成功\n");
                        }else{
                            ClientView.MSG_PAGE.append("连接服务器失败\n");
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
}
