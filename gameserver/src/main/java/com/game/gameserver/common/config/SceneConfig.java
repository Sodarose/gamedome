package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 场景静态配置
 *
 * @author xuewenkang
 * @date 2020/6/8 16:46
 */
@Data
public class SceneConfig {
    /** id */
    @JSONField(name = "id")
    private Integer id;

    /** 名称 */
    @JSONField(name = "name")
    private String name;

    /** 介绍*/
    @JSONField(name = "desc")
    private String desc;

    /** 相邻场景*/
    @JSONField(name = "neighbors")
    private List<Integer> neighbors;

    /** 怪物列表 */
    @JSONField(name = "monsterIds")
    private List<Integer> monsterIds;

    /** npc列表 */
    @JSONField(name = "npcIds")
    private List<Integer> npcIds;
}
