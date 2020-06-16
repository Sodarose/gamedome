package com.game.gameserver.common.state;

import com.game.gameserver.common.entity.Unit;

/**
 * 状态机
 *
 * @author xuewenkang
 * @date 2020/6/4 9:41
 */
public class StateMachine<T> {
    /**
     * 实体
     */
    private T t;
    /**
     * 当前状态
     **/
    private State<T> currState;
    /**
     * 之前的状态
     **/
    private State<T> preState;

    public StateMachine(T t) {
        this.t = t;
    }

    public StateMachine(T t, State<T> currState) {
        this.t = t;
        this.currState = currState;
        this.currState.onEntry(t);
    }

    public void initState(State<T> state) {
        this.currState = state;
        this.currState.onEntry(t);
    }

    /**
     * 切换状态
     *
     * @param state
     * @return void
     */
    public void changeState(State<T> state) {
        this.currState.onExit(t);
        this.preState = this.currState;
        this.currState = state;
        this.currState.onEntry(t);
    }

    /**
     * 更新状态
     *
     * @param
     * @return void
     */
    public void update() {
        if (this.currState != null) {
            this.currState.execute(t);
        }
    }

    /**
     * 重置状态
     *
     * @param
     * @return void
     */
    public void rest() {

    }
}