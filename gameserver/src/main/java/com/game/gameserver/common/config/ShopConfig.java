package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 商店配置
 *
 * @author xuewenkang
 * @date 2020/7/14 3:00
 */
@Data
public class ShopConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "goods")
    private List<Integer> goods;
}
