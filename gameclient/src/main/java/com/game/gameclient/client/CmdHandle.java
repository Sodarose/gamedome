package com.game.gameclient.client;

import com.game.gameclient.cmd.Cmd;
import com.game.gameclient.context.GameClientContext;
import com.game.gameclient.view.ClientView;
import com.game.protocol.CmdProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/10 18:28
 */
public class CmdHandle {
    private final static Logger logger = LoggerFactory.getLogger(CmdHandle.class);
    public static final Map<String, Cmd> CMD_MAP = new HashMap<>();

    static {
        for (Cmd cmd : Cmd.values()) {
            CMD_MAP.put(cmd.name(), cmd);
        }
    }

    public static void submit(String order) {
        if ("CLEAN".equals(order.toUpperCase())) {
            ClientView.cleanMsgPane();
            return;
        }
        if (GameClientContext.ctx != null) {
            // 切分消息
            String[] array = order.split("\\s+");
            // 查找CMD
            Cmd cmd = CMD_MAP.get(array[0].toUpperCase());
            if (cmd == null) {
                ClientView.print2Msg("没有该命令");
                return;
            }
            // 参数
            StringBuilder param = new StringBuilder();
            for(int i=1;i<array.length;i++){
                param.append(array[i]).append(" ");
            }
            // 构建消息
            CmdProto.CmdMsg cmdMsg = CmdProto.CmdMsg.newBuilder()
                    .setModule(cmd.getModule())
                    .setCmd(cmd.getCmd())
                    .setContent(param.toString().trim())
                    .build();
            GameClientContext.ctx.channel().writeAndFlush(cmdMsg);
            logger.info("发送命令:{}",cmd.toString());
        }
    }
}
