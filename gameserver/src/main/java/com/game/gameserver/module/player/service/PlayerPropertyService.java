package com.game.gameserver.module.player.service;

import com.game.gameserver.module.player.domain.PlayerDomain;
import com.game.gameserver.module.player.domain.PlayerBattle;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/7/10 14:25
 */
@Service
public class PlayerPropertyService {

    /**
     * 初始化战斗属性
     *
     * @param playerDomain
     * @return void
     */
    public void initPlayerBattle(PlayerDomain playerDomain){
        PlayerBattle playerBattle = new PlayerBattle();
        playerDomain.setPlayerBattle(playerBattle);
    }
}
