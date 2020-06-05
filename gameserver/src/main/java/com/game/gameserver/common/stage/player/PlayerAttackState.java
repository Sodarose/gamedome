package com.game.gameserver.common.stage.player;

import com.game.gameserver.common.stage.State;
import com.game.gameserver.module.player.object.PlayerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuewenkang
 * @date 2020/6/4 15:00
 */
public class PlayerAttackState implements State<PlayerObject> {

    private static final Logger logger = LoggerFactory.getLogger(PlayerAttackState.class);
    private static final PlayerAttackState INSTANCE = new PlayerAttackState();

    public  static PlayerAttackState getInstance(){
        return INSTANCE;
    }

    private PlayerAttackState() {

    }

    /**
     * 进入状态前的动作
     *
     * @param playerObject
     * @return void
     */
    @Override
    public void onEntry(PlayerObject playerObject) {

    }

    /**
     * 执行当前状态动作
     *
     * @param playerObject
     * @return void
     */
    @Override
    public void execute(PlayerObject playerObject) {

    }

    /**
     * 退出当前状态动作
     *
     * @param playerObject
     * @return void
     */
    @Override
    public void onExit(PlayerObject playerObject) {

    }
}
