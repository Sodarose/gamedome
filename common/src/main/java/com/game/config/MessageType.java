package com.game.config;

/**
 * @author xuwenkang
 * 消息类型
 */
public class MessageType {

    /**
     * 用户：用户注册
     * */
    public final static short USER_REGISTER_REQ = 0;

    /**
     * 用户：注册结果
     * */
    public final static short USER_REGISTER_RES = 1;

    /**
     * 用户：登录指令
     * */
    public final static short USER_LOGIN_REQ = 2;

    /**
     * 用户：登录结果
     * */
    public final static short USER_LOGIN_RES = 3;

    /**
     * 移动指令
     * */
    public final static short GAME_MOVE_S = 4;

    /**
     * 场景
     * */
    public final static short GAME_SCENE_S = 5;

    /**
     * 角色
     * */
    public final static short GAME_ROLE_S = 6;

    /**
     * NPC
     * */
    public final static short GAME_NPC_S = 7;

    /**
     * 怪物
     * */
    public final static short GAME_MONSTER_S = 8;

    /**
     * 地图
     * */
    public final static short GAME_MAP_S = 9;

    /**
     * 切换场景
     * */
    public final static short GAME_CUT_S = 10;

    /**
     * 查看
     * */
    public final static short GAME_CHECK_S = 11;

    /**
     * AIO
     * */
    public final static short GAME_AIO_S = 12;
}
