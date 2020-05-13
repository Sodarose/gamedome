package com.game.context;

import com.game.pojo.Role;
import com.game.entity.Scene;
import com.game.protocol.Protocol;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * 游戏上下文，用于存储游戏数据
 */
@Component
@Data
public class GameContext {

    /**
     * 当前场景
     * */
    private Protocol.Scene scene;

    /**
     * 当前游戏角色
     * */
    private Protocol.Role role;

}
