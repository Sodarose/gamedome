package com.game.gameserver.module.buffer.service;

import com.game.gameserver.module.buffer.model.Buffer;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/11 10:57
 */
public interface BufferService {

    /**
     * 加载角色buffer
     *
     * @param playerId
     * @return java.util.List<com.game.gameserver.module.buffer.model.Buffer>
     */
    List<Buffer> loadPlayerBuffer(int playerId);
}
