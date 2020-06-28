package com.game.gameserver.module.ai.fsm;

import com.alibaba.druid.support.spring.stat.annotation.Stat;

/**
 * 状态机
 *
 * @author xuewenkang
 * @date 2020/6/22 20:39
 */
public class StateMachine<E, S extends State<E>> {
    /**
     * 状态拥有者
     */
    private E owner;
    /**
     * 当前状态
     */
    private S currState;
    /**
     * 全局状态
     */
    private S globalState;
    /**
     * 上一个状态
     */
    private S previousState;

    public StateMachine() {
        this(null, null, null);
    }

    public StateMachine(E owner) {
        this(owner, null, null);
    }

    public StateMachine(E owner, S initialState) {
        this(owner, initialState, null);
    }

    public StateMachine(E owner, S initialState, S globalState) {
        this.owner = owner;
        this.setInitialState(initialState);
        this.globalState = globalState;
    }

    public E getOwner() {
        return owner;
    }

    public void setInitialState(S initialState) {
        this.currState = initialState;
        this.previousState = null;
    }

    public S getCurrState() {
        return currState;
    }

    public S getPreviousState() {
        return previousState;
    }

    public S getGlobalState() {
        return globalState;
    }

    public void changeGlobalState(S globalState) {
        if(this.globalState!=null){
            this.globalState.exit(owner);
        }
        this.globalState = globalState;
        this.globalState.enter(owner);
    }

    public void update() {
        if (globalState != null) {
            globalState.update(owner);
        }

        if (currState != null) {
            currState.update(owner);
        }
    }

    public void changeState(S newState) {
        previousState = currState;
        if (currState != null) {
            currState.exit(owner);
        }
        currState = newState;
        if(currState!=null){
            currState.enter(owner);
        }
    }

    public boolean revertToPreviousState(){
        if(previousState==null){
            return false;
        }
        changeState(previousState);
        return true;
    }

    public boolean isInState(S state){
        return currState.equals(state);
    }


}
