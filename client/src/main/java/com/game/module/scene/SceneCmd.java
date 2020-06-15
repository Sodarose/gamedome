package com.game.module.scene;

/**
 * @author xuewenkang
 * @date 2020/6/1 15:00
 */
public interface SceneCmd {
    /** 请求场景信息 */
    short SCENE_INFO_REQ = 0;
    /** 切换场景 */
    short CHANGE_SCENE = 1;
    /** 进入场景 */
    short ENTRY_SCENE = 2;
    /** 退出场景 */
    short EXIT_SCENE = 3;
    /** 同步场景数据 */
    short SYNC_SCENE = 4;
    /** 获取场景列表 */
    short SCENE_LIST_REQ = 5;
}
