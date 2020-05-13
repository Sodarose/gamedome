package com.game.ulit;

import com.game.protocol.Message;

/**
 * @author: xuewenkang
 * @date: 2020/5/13 9:22
 */
public class MessageUtil {

    /***
     * int 4个长度
     */
    private final static int LENGTH = 4;

    /**
     * short 2个长度
     * */
    private final static int CMD_LENGTH = 2;

    public static Message createMessage(short cmd, byte[] bytes){
        int length = LENGTH+CMD_LENGTH;
        if(bytes!=null){
            length += bytes.length;
        }
        return new Message(length,cmd,bytes);
    }
}
