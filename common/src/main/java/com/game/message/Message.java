package com.game.message;

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

    public Message(){

    }

    public Message(String content){
        this.module = 0;
        this.cmd = 0;
        this.content = content.trim();
    }

    public Message(int module,String content){
        this.module = module;
        this.cmd = 0;
        this.content = content.trim();
    }

    public Message(int module,int cmd,String content){
        this.module = module;
        this.cmd = cmd;
        this.content = content.trim();
    }

    public static Message buildMsg(CmdProto.CmdMsg cmdMsg){
        return new Message(cmdMsg.getModule(),cmdMsg.getCmd(),cmdMsg.getContent());
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
