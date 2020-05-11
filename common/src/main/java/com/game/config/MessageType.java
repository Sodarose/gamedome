package com.game.config;

/**
 * @author xuwenkang
 * 消息类型
 */
public class MessageType {

    /**
     * 用户：用户注册
     * */
    public final static Short USER_REGISTER_REQ = 0;

    /**
     * 用户：注册结果
     * */
    public final static Short USER_REGISTER_RES = 1;

    /**
     * 用户：登录指令
     * */
    public final static Short USER_LOGIN_REQ = 2;

    /**
     * 用户：登录结果
     * */
    public final static Short USER_LOGIN_RES = 3;

    /**
     * 移动
     * */
    public final static Short GAME_MOVE_S = 4;

    /**
     * 场景
     * */
    public final static Short GAME_SCENE_S = 5;

    /**
     * 角色
     * */
    public final static Short GAME_ROLE_S = 6;

    /**
     * NPC
     * */
    public final static Short GAME_NPC_S = 7;

    /**
     * 怪物
     * */
    public final static Short GAME_MONSTER_S = 8;

    /**
     * 地图
     * */
    public final static Short GAME_MAP_S = 9;
}
