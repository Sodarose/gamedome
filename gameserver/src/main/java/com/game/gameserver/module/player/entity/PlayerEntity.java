package com.game.gameserver.module.player.entity;

/*import com.game.gameserver.module.ai.state.player.PlayerState;*/
import lombok.Data;

import java.io.Serializable;

/**
 * 玩家模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:16
 */
@Data
public class PlayerEntity implements Serializable {
    /** id */
    private Long id;
    /** 姓名 */
    private String name;
    /** 等级 */
    private Integer level;
    /** 职业*/
    private Integer careerId;
    /** 场景Id*/
    private Integer sceneId;
    /** 角色经验 */
    private Integer expr;
    /** 金币数量*/
    private Integer golds;
    /** 背包容量*/
    private Integer backBagCapacity;
    /** 仓库容量*/
    private Integer warehouseCapacity;
    /** 公会Id */
    private Long guildId;
    /** 账号ID*/
    private Long userId;

    public PlayerEntity(){

    }

}
