package com.game.gameclient.type;

/**
 * @author kangkang
 */
public interface SceneCmd {
    /** 展示当前场景 */
    int SHOW_SCENE = 1001;
    /** 移动场景 */
    int MOVE_SCENE = 1002;
    /** AIO 打印实体*/
    int AIO = 1003;
    /** 同步客户端场景数据*/
    int SYNC = 1004;
    /** 场景列表*/
    int SCENE_LIST = 1005;
    /** 查看一个场景*/
    int CHECK_SCENE = 1006;
    /** 与NPC对话*/
    int TALK_NPC = 1007;
}
