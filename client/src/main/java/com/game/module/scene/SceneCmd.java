package com.game.module.scene;

/**
 * @author xuewenkang
 * @date 2020/6/1 15:00
 */
public interface SceneCmd {
    /** 移动 */
    short MOVE_SCENE = 1;
    /** 同步场景数据 */
    short SYNC_SCENE = 2;
    /** 获取场景列表 */
    short SCENE_LIST_REQ = 3;
}
