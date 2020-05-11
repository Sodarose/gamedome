package com.game.context;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author xuwenkang
 */

@Component
public class ClientContext {
    private Channel channel;



    public void setChannel(Channel channel){
        this.channel = channel;
    }

    public Channel getChannel(){
        return channel;
    }
}
