package com.game.handler;

import com.game.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author xuewenkang
 * 解码器
 */
public class MessageDecode extends LengthFieldBasedFrameDecoder {

    public MessageDecode(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }


    /**
     * messageLength | cmd      | data
     * Integer       | Short    | byte[]
     * */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf farm = (ByteBuf) super.decode(ctx,in);
        if(farm==null){
            return null;
        }
        int length = farm.readInt();
        short cmd = farm.readShort();
        byte []bytes = ByteBufUtil.getBytes(farm);
        return new Message(length,cmd,bytes);
    }
}
