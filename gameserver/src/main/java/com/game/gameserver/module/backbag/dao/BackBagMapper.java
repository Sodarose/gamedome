package com.game.gameserver.module.backbag.dao;

import com.game.gameserver.module.backbag.entity.BackBagEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/12 16:29
 */
@Component
@Repository
public interface BackBagMapper {
    int insert(BackBagEntity backBagEntity);

    int update(BackBagEntity backBagEntity);

    BackBagEntity select(long playerId);

    int delete(long playerId);
}
