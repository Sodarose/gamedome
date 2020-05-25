package com.game.gameserver.module.player.vo;

import com.game.gameserver.module.bag.entity.BagEntity;

import java.util.List;

/**
 * 客户端展示的数据
 * @author xuewenkang
 * @date 2020/5/25 15:05
 */
public class PlayerVo {
    private Integer id;
    private String  name;
    /** 等级 */
    private Integer level;
    /** 职业 */
    private String  career;
    /** 属性 */
    private String  property;
    /** 装备道具 */
    private List<String> equipBar;
}
