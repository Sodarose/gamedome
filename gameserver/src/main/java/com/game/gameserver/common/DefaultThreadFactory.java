package com.game.gameserver.common;

import java.util.concurrent.ThreadFactory;

/**
 * @author xuewenkang
 * @date 2020/5/28 17:21
 */
public class DefaultThreadFactory implements ThreadFactory {
    private String threadName;
    private ThreadGroup threadGroup;

    public DefaultThreadFactory(String threadName){
        this.threadName = threadName;
    }

    public DefaultThreadFactory(String threadName,String threadGroupName){
        this.threadName = threadName;
        this.threadGroup = new ThreadGroup(threadGroupName);
    }

    public ThreadGroup getThreadGroup(){
        return threadGroup;
    }

    @Override
    public Thread newThread(Runnable r) {
        return threadGroup==null?new Thread(r,threadName):new Thread(threadGroup,r,threadName);
    }
}
