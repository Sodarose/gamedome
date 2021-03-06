package com.game.gameserver.net.handler;

import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.user.module.User;
import com.game.gameserver.module.user.service.UserService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.thread.BusinessThreadPool;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/24 17:31
 */
public abstract class BaseHandler implements Handler {
    private final static Logger logger = LoggerFactory.getLogger(Handler.class);

    protected final Map<Integer, CmdExecutor> CMD_EXECUTOR = new HashMap(1);

    @Autowired
    MessageDispatcher messageDispatcher;

    public BaseHandler() {

    }

    /**
     * 初始化
     *
     * @param
     * @return void
     */
    protected void initialize() {
        Method[] methods = getClass().getMethods();
        if (methods.length == 0) {
            return;
        }
        for (Method method : methods) {
            CmdHandler cmdHandler = method.getAnnotation(CmdHandler.class);
            if (cmdHandler == null) {
                continue;
            }
            Integer cmd = cmdHandler.cmd();
            CmdExecutor cmdExecutor = new CmdExecutor(cmd, method, this);
            putCmdExecutor(cmd, cmdExecutor);
        }
    }

    ;

    @PostConstruct
    public void init() {
        messageDispatcher.registerModuleHandle(this);
        this.initialize();
    }

    public void putCmdExecutor(Integer cmd, CmdExecutor cmdExecutor) {
        if (CMD_EXECUTOR.containsKey(cmd)) {
            return;
        }
        CMD_EXECUTOR.put(cmd, cmdExecutor);
    }

    /**
     * 将消息按照CMD命令 分发给模块中指定的处理器处理
     *
     * @param message
     * @param channel
     * @return void
     */
    @Override
    public void dispatcher(Message message, Channel channel) {
        int cmd = message.getCmd();
        CmdExecutor cmdExecutor = CMD_EXECUTOR.get(cmd);
        if (cmdExecutor == null) {
            return;
        }

        // 获取用户账户
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        // 用户没有登录账户
        if (player == null) {
            BusinessThreadPool.BUSINESS_THREAD_POOL.getAccountThread().execute(() -> {
                logger.info("处理任务: module {} cmd {}", message.getModule(), message.getCmd());
                cmdExecutor.execute(message, channel);
            });
            return;
        }

        // 用户已经登录 分配业务线程
        BusinessThreadPool.BUSINESS_THREAD_POOL.getBusinessThread(player.getId()).execute(() -> {
            logger.info("处理任务: module {} cmd {}", message.getModule(), message.getCmd());
            cmdExecutor.execute(message, channel);
        });
    }
}
