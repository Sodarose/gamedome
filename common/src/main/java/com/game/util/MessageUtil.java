package com.game.util;

import com.game.protocol.Message;

/**
 * @author xuewenkang
 * @date 2020/5/19 14:53
 */
public class MessageUtil {
    /***
     * int 4个长度
     */
    private final static int LENGTH = 4;

    private final static int MODULE_LENGTH = 2;

    /**
     * short 2个长度
     */
    private final static int CMD_LENGTH = 2;

    public static Message createMessage(short module,short cmd, byte[] bytes) {
        int length = LENGTH + MODULE_LENGTH +CMD_LENGTH;
        if (bytes != null) {
            length += bytes.length;
        }
        return new Message(length,module,cmd, bytes);
    }
}
