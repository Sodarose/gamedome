package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.game.gameserver.module.task.model.Award;
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
     * 副本基本信息
     */
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;
    /** */
    @JSONField(name = "desc")
    private String desc;
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
    /** 经验奖励 */
    @JSONField(name = "exprAward")
    private int exprAward;
    /** 金币奖励 */
    @JSONField(name = "goldAward")
    private int goldAward;
    /** 装备奖励 */
    @JSONField(name = "itemAward")
    private List<Award> itemAward;
}