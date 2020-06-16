package com.game.gameserver.common.state.instance;

import com.game.gameserver.common.state.State;
import com.game.gameserver.module.instance.model.InstanceObject;

/**
 * 副本运行状态
 *
 * @author xuewenkang
 * @date 2020/6/16 15:42
 */
public class InstanceRunState implements State<InstanceObject> {

    private final static InstanceRunState INSTANCE = new InstanceRunState();

    private InstanceRunState(){

    }

    public static InstanceRunState getInstance(){
        return INSTANCE;
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
     * 执行状态方法
     *
     * @param instanceObject
     * @return void
     */
    @Override
    public void execute(InstanceObject instanceObject) {

    }
}
