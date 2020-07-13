package com.game.gameserver.module.npc.model;

import com.game.gameserver.common.config.NpcConfig;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

/**
 * npc模型对象
 *
 * @author xuewenkang
 * @date 2020/6/9 11:41
 */
@Data
public class Npc {

    private long npcId;

    private String name;

    private int level;

    private String talk;

    public Npc(NpcConfig npcConfig){
        this.npcId = npcConfig.getId();
        this.name = npcConfig.getName();
        this.level = npcConfig.getLevel();
        this.talk = npcConfig.getTalk();
    }
}
