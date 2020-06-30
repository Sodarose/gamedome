package com.game.module.task;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.WordPage;
import com.game.protocol.Message;
import com.game.protocol.TaskProtocol;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/30 10:56
 */
@Component
@ModuleHandler(module = ModuleKey.TASK_MODULE)
public class TaskHandle extends BaseHandler {
    /**
     * 所有任务列表
     */
    private Map<String, TaskProtocol.TaskConfigInfo> allTaskInfos = new HashMap<>();
    /**
     * 可接受任务列表
     */
    private Map<String, TaskProtocol.TaskConfigInfo> receiveAbleTaskInfos = new HashMap<>();
    /**
     * 角色接取的任务列表
     */
    private Map<String, TaskProtocol.TaskInfo> receiveInfos = new HashMap<>();

    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private WordPage wordPage;

    public void queryAllTaskReq() {
        Message message = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.QUERY_ALL_TASK, null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = TaskCmd.QUERY_ALL_TASK)
    public void receiveQueryAllTaskRes(Message message) {
        try {
            allTaskInfos.clear();
            TaskProtocol.QueryAllTaskRes res = TaskProtocol.QueryAllTaskRes.parseFrom(message.getData());
            for (TaskProtocol.TaskConfigInfo info : res.getInfosList()) {
                allTaskInfos.put(info.getName(), info);
            }
            wordPage.clean();
            wordPage.print("全部任务:");
            if (res.getInfosList().isEmpty()) {
                wordPage.print("任务列表为空");
            } else {
                wordPage.printTaskConfigInfos(res.getInfosList());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void queryReceiveAbleTaskReq() {
        Message message = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.QUERY_RECEIVE_ABLE_TASK, null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = TaskCmd.QUERY_RECEIVE_ABLE_TASK)
    public void receiveQueryReceiveAbleTaskRes(Message message) {
        try {
            receiveAbleTaskInfos.clear();
            TaskProtocol.QueryReceiveAbleTaskRes res = TaskProtocol.QueryReceiveAbleTaskRes.parseFrom(message.getData());
            for (TaskProtocol.TaskConfigInfo taskConfigInfo : res.getInfosList()) {
                receiveAbleTaskInfos.put(taskConfigInfo.getName(), taskConfigInfo);
            }
            wordPage.clean();
            wordPage.print("可接受任务:");
            if (res.getInfosList().isEmpty()) {
                wordPage.print("任务列表未空");
            } else {
                wordPage.printTaskConfigInfos(res.getInfosList());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void queryReceiveTaskReq() {
        Message message = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.QUERY_RECEIVE_TASK, null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = TaskCmd.QUERY_RECEIVE_TASK)
    public void receiveQueryReceiveTaskRes(Message message) {
        try {
            TaskProtocol.QueryReceiveTaskRes res = TaskProtocol.QueryReceiveTaskRes.parseFrom(message.getData());
            for (TaskProtocol.TaskInfo taskInfo : res.getTaskInfosList()) {
                receiveInfos.put(taskInfo.getName(), taskInfo);
            }
            wordPage.clean();
            wordPage.print("已接受任务列表:");
            if (res.getTaskInfosList().isEmpty()) {
                wordPage.print("列表为空");
            } else {
                wordPage.printReceiveTaskList(res.getTaskInfosList());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void acceptTaskReq(String taskName) {
        // 从可接受任务列表中找该任务
        TaskProtocol.TaskConfigInfo taskConfigInfo = receiveAbleTaskInfos.get(taskName);
        if (taskConfigInfo == null) {
            wordPage.print("错误的任务名");
            return;
        }
        TaskProtocol.AcceptTaskReq req = TaskProtocol.AcceptTaskReq.newBuilder().setTaskId(taskConfigInfo.getId())
                .build();
        Message message = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.ACCEPT_TASK, req.toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = TaskCmd.ACCEPT_TASK)
    public void receiveAcceptTaskRes(Message message) {
        try {
            TaskProtocol.AcceptTaskTes res = TaskProtocol.AcceptTaskTes.parseFrom(message.getData());
            wordPage.print(res.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }

    public void cancelTaskReq(String taskName) {
        // 从可接受任务列表中找该任务
        TaskProtocol.TaskInfo taskInfo = receiveInfos.get(taskName);
        if (taskInfo == null) {
            wordPage.print("错误的任务名");
            return;
        }
        TaskProtocol.CancelTaskReq req = TaskProtocol.CancelTaskReq.newBuilder().build();
    }

    @CmdHandler(cmd = TaskCmd.CANCEL_TASK)
    public void receiveCancelTaskRes(Message message) {
        try {
            TaskProtocol.CancelTaskRes res = TaskProtocol.CancelTaskRes.parseFrom(message.getData());
            wordPage.print(res.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void submitTaskReq(String taskName) {
        TaskProtocol.TaskInfo taskInfo = receiveInfos.get(taskName);
        if (taskInfo == null) {
            wordPage.print("没有该任务，请刷新");
            return;
        }
        TaskProtocol.SubmitTaskReq req = TaskProtocol.SubmitTaskReq
                .newBuilder().setTaskId(taskInfo.getTaskId()).build();
        Message message = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.SUBMIT_TASK, req.toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = TaskCmd.SUBMIT_TASK)
    public void receiveSubmitTaskRes(Message message) {
        try {
            TaskProtocol.SubmitTaskRes res = TaskProtocol.SubmitTaskRes.parseFrom(message.getData());
            wordPage.print(res.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

}
