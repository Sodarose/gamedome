package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.task.model.Task;
import lombok.Data;

/**
 * 任务完成事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:47
 */
@Data
public class TaskCompletedEvent implements Event {
    private Player player;
    private Task task;

    public TaskCompletedEvent(Player player, Task task) {
        this.player = player;
        this.task = task;
    }
}
