package com.game.gameserver.module.player.service;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.LogoutEvent;
import com.game.gameserver.module.player.dao.PlayerDbService;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.model.PlayerBattle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/7/10 14:25
 */
@Listener
@Service
public class PlayerPropertyService {


    @Autowired
    private PlayerDbService playerDbService;

    /**
     * 初始化战斗属性
     *
     * @param player
     * @return void
     */
    public void initPlayerBattle(Player player){
        PlayerBattle playerBattle = new PlayerBattle();
        player.setPlayerBattle(playerBattle);
    }


    public void changeHpMp(int hp,int mp){

    }


    @EventHandler
    public void handleLogoutEvent(LogoutEvent logoutEvent){
        Player player = logoutEvent.getPlayer();
        playerDbService.updateAsync(player.getPlayerEntity());
    }

}
