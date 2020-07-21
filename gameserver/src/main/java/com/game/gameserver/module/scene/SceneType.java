package com.game.gameserver.module.scene;

/**
 * @author xuewenkang
 * @date 2020/7/18 22:05
 */
public enum SceneType {
    WORLD(1,"世界地图"),
    INSTANCE(2,"副本");

    private int type;
    private String desc;

    SceneType(int type,String desc){
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
