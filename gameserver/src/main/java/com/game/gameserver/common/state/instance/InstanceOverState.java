package com.game.gameserver.common.state.instance;

import com.game.gameserver.common.state.State;
import com.game.gameserver.module.instance.model.InstanceObject;

/**
 * 副本通关状态
 *
 * @author xuewenkang
 * @date 2020/6/16 15:45
 */
public class InstanceOverState implements State<InstanceObject> {

    private final static InstanceOverState INSTANCE = new InstanceOverState();

    public static InstanceOverState getInstance(){
        return INSTANCE;
    }

    private InstanceOverState(){

    }

    /**
     * 进入状态
     *
     * @param instanceObject
     * @return void
     */
    @Override
    public void onEntry(InstanceObject instanceObject) {

    }

    /**
     * 退出当前状态
     *
     * @param instanceObject
     * @return void
     */
    @Override
    public void onExit(InstanceObject instanceObject) {

    }

    /**
     * 更新状态信息
     *
     * @param instanceObject
     * @return void
     */
    @Override
    public void execute(InstanceObject instanceObject) {

    }
}
