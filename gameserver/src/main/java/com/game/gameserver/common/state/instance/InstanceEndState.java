package com.game.gameserver.common.state.instance;

import com.game.gameserver.common.state.State;

/**
 * @author xuewenkang
 * @date 2020/6/16 15:48
 */
public class InstanceEndState implements State<InstanceEndState> {

    private final static InstanceEndState INSTANCE = new InstanceEndState();

    public static InstanceEndState getInstance(){
        return INSTANCE;
    }

    private InstanceEndState(){

    }

    /**
     * 进入状态
     *
     * @param instanceEndState
     * @return void
     */
    @Override
    public void onEntry(InstanceEndState instanceEndState) {

    }

    /**
     * 退出当前状态
     *
     * @param instanceEndState
     * @return void
     */
    @Override
    public void onExit(InstanceEndState instanceEndState) {

    }

    /**
     * 更新状态信息
     *
     * @param instanceEndState
     * @return void
     */
    @Override
    public void execute(InstanceEndState instanceEndState) {

    }
}
