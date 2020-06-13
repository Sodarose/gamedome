package com.game.gameserver.module.buffer.service.impl;

import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.buffer.service.BufferService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * buffer 服务
 *
 * @author xuewenkang
 * @date 2020/6/11 10:58
 */
@Service
public class BufferServiceImpl implements BufferService {
    /**
     * 加载角色buffer
     *
     * @param playerId
     * @return java.util.List<com.game.gameserver.module.buffer.model.Buffer>
     */
    @Override
    public List<Buffer> loadPlayerBuffer(int playerId) {
        return null;
    }
}
