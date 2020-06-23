package com.game.gameserver.module.npc.model;

import com.game.gameserver.common.config.NpcConfig;
import com.game.gameserver.util.GameUUID;

/**
 * npc模型对象
 *
 * @author xuewenkang
 * @date 2020/6/9 11:41
 */
public class NpcObject {

    /** id */
    private final long id;
    /** npc数据静态配置 */
    private final NpcConfig npcConfig;

    public NpcObject(NpcConfig npcConfig){
        this.id = GameUUID.getInstance().generate();
        this.npcConfig = npcConfig;
    }

    /** 初始化 */
    public void initialize(){

    }


    public long getId() {
        return id;
    }

    public NpcConfig getNpcConfig(){
        return npcConfig;
    }
}
