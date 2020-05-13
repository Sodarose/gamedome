package com.game.net.handler;

import com.game.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xuewenkang
 * 编码器
 */
public class MessageEncode extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getLength());
        out.writeShort(msg.getCmd());
        if(msg.getData()!=null){
            out.writeBytes(msg.getData());
        }
    }
}