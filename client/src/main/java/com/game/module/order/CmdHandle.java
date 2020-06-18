package com.game.module.order;

import com.game.context.ClientGameContext;
import com.game.module.ModuleKey;
import com.game.module.chat.ChatHandle;
import com.game.module.player.PlayerCmd;
import com.game.module.gui.WordPage;
import com.game.module.player.PlayerHandle;
import com.game.module.store.StoreHandle;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/19 21:43
 */
@Service
public class CmdHandle {
    private final static Logger logger = LoggerFactory.getLogger(CmdHandle.class);
    private final static Map<String, Invoke> CMD_INVOKE_MAP = new HashMap<>();

    @Autowired
    private WordPage wordPage;
    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private StoreHandle storeHandle;
    @Autowired
    private PlayerHandle playerHandle;
    @Autowired
    private ChatHandle chatHandle;


    public void submitCmd(String order) {
        // 命令片段
        String[] frags = order.split("\\s+");
        if (frags.length == 0) {
            return;
        }
        String cmd = frags[0].toUpperCase();
        Invoke invoke = CMD_INVOKE_MAP.get(cmd);
        if (invoke == null) {
            return;
        }
        invoke.invoke(frags);
    }

    public interface Invoke {
        void invoke(String[] flags);
    }

    @PostConstruct
    public void init() {
        // clean
        CMD_INVOKE_MAP.put(CmdType.CMD_CLEAN, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                wordPage.clean();
            }
        });

        // list role
        CMD_INVOKE_MAP.put(CmdType.LIST_ROLES, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                listRoles(flags);
            }
        });

        // confirm role
        CMD_INVOKE_MAP.put(CmdType.LOGIN_ROLE, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                confirmRole(flags);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.SHOW_ME, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                playerHandle.showPlayerInfo();
            }
        });

        CMD_INVOKE_MAP.put(CmdType.SHOW_STORE, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                showStore(flags);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.BUY, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                buy(flags);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.SELL, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                sell(flags);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.SEND_C_CHAT, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                int channelId = Integer.parseInt(flags[1]);
                String content = flags[2];
                chatHandle.sendChannelChat(channelId, content);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.SEND_P_CHAT, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                int playerId = Integer.parseInt(flags[1]);
                String content = flags[2];
                chatHandle.sendPrivacyChat(playerId, content);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.SEND_L_CHAT, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                String content = flags[1];
                chatHandle.sendLocalChat(content);
            }
        });
    }


    /**
     * 请求角色列表
     *
     * @param flags
     * @return void
     */
    private void listRoles(String[] flags) {
        PlayerProtocol.PlayerListReq.Builder builder = PlayerProtocol.PlayerListReq.newBuilder();
        Message message = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.LIST_PLAYERS, builder.build()
                .toByteArray());
        gameContext.getChannel().writeAndFlush(message);
        logger.info("发送请求角色列表命令");
    }

    /**
     * 选择角色并登录
     *
     * @param flags 命令
     * @return void
     */
    private void confirmRole(String[] flags) {
        if (flags.length < 2) {
            return;
        }
        String roleName = flags[1];
        playerHandle.loginRole(roleName);
    }

    private void showMe(String[] flags) {
        playerHandle.showPlayerInfo();
    }

    private void showStore(String[] flags) {
        storeHandle.requestCommodityList();
    }

    private void buy(String[] flags) {
        if (flags.length != 3) {
            return;
        }
        String goodsName = flags[1];
        int num = Integer.parseInt(flags[2]);
        storeHandle.requestByCommodity(goodsName, num);
    }

    private void sell(String[] flags) {
        if (flags.length != 3) {
            return;
        }
        String goodsName = flags[1];
        int num = Integer.parseInt(flags[2]);
        storeHandle.requestShellGoods(goodsName, num);
    }
}
