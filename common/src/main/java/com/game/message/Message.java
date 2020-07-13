package com.game.msg;

import com.game.protocol.CmdProto;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/10 17:20
 */
@Data
public class Message {
    /** 模块号 */
    int module;
    /** 命令号 */
    int cmd;
    /** 命令内容 */
    String content;

    public static Message buildMsg(CmdProto.CmdMsg cmdMsg){
        Message message = new Message();
        message.setModule(cmdMsg.getModule());
        message.setCmd(cmdMsg.getCmd());
        message.setContent(cmdMsg.getContent());
        return message;
    }

    public static CmdProto.CmdMsg buildCmdProtoCmdMsg(Message message){
        return CmdProto.CmdMsg
                .newBuilder()
                .setModule(message.getModule())
                .setCmd(message.getCmd())
                .setContent(message.getContent())
                .build();
    }
}
