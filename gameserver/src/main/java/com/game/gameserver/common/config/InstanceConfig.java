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
     * 副本开放时间 0 全天开发
     */
    @JSONField(name = "openTime")
    private int openTime;
    /**
     * 通关 时间限制
     */
    @JSONField(name = "limitTime")
    private int limitTime;
    @JSONField(name = "needGood")
    private int needGood;
    @JSONField(name = "minNum")
    private int minNum;
    @JSONField(name = "maxNum")
    private int maxNum;
    @JSONField(name = "minLevel")
    private int minLevel;

    /**
     * 副本奖励
     */
    @JSONField(name = "exprAward")
    private int exprAward;
    @JSONField(name = "goldAward")
    private int goldAward;
    @JSONField(name = "equipAward")
    private List<Integer> equipAward;
    @JSONField(name = "propAward")
    private List<Integer> propAward;

    /** 怪物配置 */
    @JSONField(name = "bossConfigId")
    private Integer bossConfigId;

}