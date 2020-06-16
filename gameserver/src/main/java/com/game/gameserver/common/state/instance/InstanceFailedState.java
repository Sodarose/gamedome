package com.game.gameserver.common.state.instance;

import com.game.gameserver.common.state.State;
import com.game.gameserver.module.instance.model.InstanceObject;

/**
 * 副本通关失败状态
 *
 * @author xuewenkang
 * @date 2020/6/16 15:46
 */
public class InstanceFailedState implements State<InstanceObject> {

    private final static InstanceFailedState INSTANCE  = new InstanceFailedState();

    public static InstanceFailedState getInstance(){
        return INSTANCE;
    }

    private InstanceFailedState(){

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
