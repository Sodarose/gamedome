package com.game.game.service;

/**
 * @author: xuewenkang
 * @date: 2020/5/13 12:29
 */
public abstract class AbstractCmdService implements BaseService {

    /**
     * description: 执行命令
     *
     * @param cmd 指令
     * @return void
     */
    public abstract void runCmd(String cmd);

    /**
     * description: 初始化游戏客户端指令
     *
     * @param
     * @return void
     */
    public abstract void initGameClient();


    @Override
    public String serviceName() {
        return "AbstractCmdService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
