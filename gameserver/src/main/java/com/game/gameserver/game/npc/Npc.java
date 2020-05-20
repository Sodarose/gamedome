package com.game.gameserver.game.npc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuewenkang
 * @date 2020/5/19 15:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Npc {

    /**
     * ID 该ID不同于数据库ID
     */
    private Integer id;

    /**
     * 角色姓名
     */
    private String name;

    /**
     * 当前角色所在的场景ID
     */
    private Integer worldId;
}
