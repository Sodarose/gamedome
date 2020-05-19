package com.game.gameserver.game.update;

import java.util.ArrayList;
import java.util.List;

/**
 * 更新器
 * @author xuewenkang
 * @date 2020/5/19 11:21
 */
public interface Update {
    
    /**
     * 需要执行方法的更新列表
     */
    List<Update> syncUpdateList = new ArrayList<>();
    
    /**
     * 更新方法
     * @param
     * @return void
     */
    void update();
}
