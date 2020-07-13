package com.game.gameserver.net.modelhandler.scene;

/**
 * @author kangkang
 */
public interface SceneCmd {
    /** 切换场景 */
    short MOVE_SCENE = 1;
    /** 同步场景数据 */
    short SYNC_SCENE = 2;
    /** 查询场景列表 */
    short QUERY_SCENE_LIST = 3;
}
