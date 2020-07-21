package com.game.gameserver.common.fsm;

/**
 * @author xuewenkang
 * @date 2020/6/22 20:33
 */
public interface State<E> {
    /**
     * 进入当前状态
     *
     * @param e
     * @return void
     */
    void enter(E e);

    /**
     * 实体更新方法
     *
     * @param e
     * @return void
     */
    void update(E e);

    /**
     * 退出当前状态
     *
     * @param e
     * @return void
     */
    void exit(E e);
}
