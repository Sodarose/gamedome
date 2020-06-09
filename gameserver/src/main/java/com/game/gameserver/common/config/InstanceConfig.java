package com.game.gameserver.common.config;

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
     * 简单副本信息
     */
    private int id;
    private String name;
    private int type;
    private int diff;
    private int sceneId;
    private String desc;

    /**
     * 副本开放时间 0 全天开发
     */
    private int openDate;
    /**
     * 存在时间
     */
    private int liveDate;
    /**
     * 通关 时间限制
     */
    private int limitDate;
    private int needGood;
    private int minNum;
    private int maxNum;
    private int minLevel;

    /**
     * 副本奖励
     */
    private int exprAward;
    private int goodAward;
    private List<Integer> equipAward;

    /**
     * 副本Boss
     */
    private int boss;
}