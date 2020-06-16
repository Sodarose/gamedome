package com.game.gameserver.common.state;

import com.game.gameserver.common.entity.Unit;

/**
 * 状态
 *
 * @author xuewenkang
 * @date 2020/6/8 17:36
 */
public interface State<T> {
    /**
     * 进入状态
     *
     * @param t
     * @return void
     */
    void onEntry(T t);

    /**
     * 退出当前状态
     *
     * @param t
     * @return void
     */
    void onExit(T t);

    /**
     * 更新状态信息
     *
     * @param t
     * @return void
     */
    void execute(T t);
}
