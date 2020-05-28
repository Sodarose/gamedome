package com.game.module;

import com.game.protocol.Message;

/**
 * @author xuewenkang
 * @date 2020/5/27 17:31
 */
public interface Handler {
    void dispatcher(Message message);
}
