package com.game.net.handler;

import com.game.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 解码器
 * @author xuewenkang
 */
public class MessageDecode extends LengthFieldBasedFrameDecoder {

    public MessageDecode(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }


    /**
     * messageLength | module | cmd      | data
     * Integer       | Short  | Short   | byte[]
     * */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf farm = (ByteBuf) super.decode(ctx,in);
        if(farm==null){
            return null;
        }
        int length = farm.readInt();
        short module = farm.readShort();
        short cmd = farm.readShort();
        byte []bytes = ByteBufUtil.getBytes(farm);
        return new Message(length,module,cmd,bytes);
    }
}
