package com.game.game.context;

import com.game.protocol.Protocol;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 游戏上下文，用于存储游戏数据
 * @author xuewenkang
 */
@Component
@Data
public class GameContext {

    /**
     * 角色所在场景
     * */
    private Protocol.Scene scene;

    /**
     * 游戏角色
     * */
    private Protocol.Role role;

    /**
     * 当前窗口展示的场景
     * */
    private Protocol.Scene winScene;


}
