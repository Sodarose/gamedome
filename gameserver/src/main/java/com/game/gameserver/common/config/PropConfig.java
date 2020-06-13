package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 道具静态数据表
 *
 * @author xuewenkang
 * @date 2020/6/10 15:05
 */
@Data
public class PropConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "level")
    private int level;
    @JSONField(name = "overlay")
    private boolean overlay;
    @JSONField(name = "maxOverlay")
    private int maxOverlay;
    @JSONField(name = "price")
    private int price;
    @JSONField(name = "formula")
    private String formula;
    @JSONField(name = "coolTime")
    private int coolTime;
    @JSONField(name = "limitCount")
    private int limitCount;
    @JSONField(name = "desc")
    private String desc;
}
