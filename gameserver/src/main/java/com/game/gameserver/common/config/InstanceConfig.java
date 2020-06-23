package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 副本静态配置
 *
 * @author xuewenkang
 * @date 2020/6/8 16:47
 */
@Data
public class InstanceConfig {
    /**
     * 副本信息
     */
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "diff")
    private int diff;
    @JSONField(name = "desc")
    private String desc;

    /**
     * 副本开放时间
     */
    @JSONField(name = "openTime")
    private String openTime;
    /**
     * 通关 限制
     */
    /** 时间限制 单位秒*/
    @JSONField(name = "limitTime")
    private int limitTime;
    /** 是否需要强制组队 */
    @JSONField(name = "needTeam")
    private boolean needTeam;
    /** 最小人数限制 */
    @JSONField(name = "minNum")
    private int minNum;
    /** 最大人数限制 */
    @JSONField(name = "maxNum")
    private int maxNum;
    /** 等级限制 */
    @JSONField(name = "minLevel")
    private int minLevel;

    /**
     * 副本奖励配置
     */
    /** 经验奖励 */
    @JSONField(name = "exprAward")
    private int exprAward;
    /** 金币奖励 */
    @JSONField(name = "goldAward")
    private int goldAward;
    /** 装备奖励 */
    @JSONField(name = "equipAward")
    private List<Integer> equipAward;
    /** 道具奖励 */
    @JSONField(name = "propAward")
    private List<Integer> propAward;

    /** 怪物配置 */
    @JSONField(name = "instanceMonsterConfigId")
    private Integer instanceMonsterConfigId;

}