package com.game.gameserver.module.player.entity;

import com.game.gameserver.dictionary.entity.CareerData;
import com.game.gameserver.dictionary.entity.CareerLevelPropertyData;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/2 21:34
 */
@Data
public class CareerEntity {
    private CareerData careerData;
    private CareerLevelPropertyData propertyData;
}
