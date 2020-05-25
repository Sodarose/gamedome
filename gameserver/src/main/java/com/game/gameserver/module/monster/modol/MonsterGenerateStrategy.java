package com.game.gameserver.module.monster.modol;

import lombok.Data;

/**
 * 怪物生成策略
 * @author xuewenkang
 * @date 2020/5/22 15:44
 */
@Data
public class MonsterGenerateStrategy {
    private Integer monsterId;
    private Double odds;
    private Integer initCount;
}
