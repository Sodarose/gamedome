package com.game.module.order;

import com.game.context.ClientGameContext;
import com.game.module.ModuleKey;
import com.game.module.chat.ChatHandle;
import com.game.module.emai.EmailHandle;
import com.game.module.fighter.FighterHandle;
import com.game.module.instance.InstanceHandle;
import com.game.module.item.ItemHandle;
import com.game.module.player.PlayerCmd;
import com.game.module.gui.WordPage;
import com.game.module.player.PlayerHandle;
import com.game.module.scene.SceneHandle;
import com.game.module.store.StoreHandle;
import com.game.module.team.TeamHandle;
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
    @Autowired
    private InstanceHandle instanceHandle;
    @Autowired
    private TeamHandle teamHandle;
    @Autowired
    private ItemHandle itemHandle;
    @Autowired
    private EmailHandle emailHandle;
    @Autowired
    private SceneHandle sceneHandle;
    @Autowired
    private FighterHandle fighterHandle;


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

        CMD_INVOKE_MAP.put(CmdType.SHOW_INSTANCE_LIST, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                instanceHandle.showInstanceList();
            }
        });

        CMD_INVOKE_MAP.put(CmdType.ENTRY_INSTANCE, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                instanceHandle.entryInstance(flags[1], false);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.ENTRY_INSTANCE_BY_TEAM, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                instanceHandle.entryInstance(flags[1], true);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.EXIT_INSTANCE, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                instanceHandle.exitInstance();
            }
        });

        CMD_INVOKE_MAP.put(CmdType.CREATE_TEAM, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                int num = Integer.parseInt(flags[1]);
                teamHandle.createTeam(num, flags[2]);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.TEAM_LIST, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                teamHandle.getTeamList();
            }
        });

        CMD_INVOKE_MAP.put(CmdType.SHOW_TEAM, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                teamHandle.showTeam();
            }
        });

        CMD_INVOKE_MAP.put(CmdType.ENTRY_TEAM, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                teamHandle.entryTeam(flags[1]);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.EXIT_TEAM, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                teamHandle.exitTeam();
            }
        });

        // 打开背包
        CMD_INVOKE_MAP.put(CmdType.SHOW_BAG, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                itemHandle.playerBagReq();
            }
        });

        // 打开邮箱
        CMD_INVOKE_MAP.put(CmdType.EMAIL_LIST, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                emailHandle.showEmail();
            }
        });

        /**  */
        CMD_INVOKE_MAP.put(CmdType.SEND_EMAIL, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                String title = flags[1];
                String content = flags[2];
                long receiverId = Long.parseLong(flags[3]);
                int golds = Integer.parseInt(flags[4]);
                int bagIndex = Integer.parseInt(flags[5]);
                emailHandle.sendEmail(title, content, receiverId, golds, bagIndex);
            }
        });

        /** 场景AIO*/
        CMD_INVOKE_MAP.put(CmdType.SCENE_AIO, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                sceneHandle.sceneAio();
            }
        });

        /** 副本AIO */
        CMD_INVOKE_MAP.put(CmdType.INSTANCE_AIO, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                instanceHandle.instanceaio();
            }
        });

        CMD_INVOKE_MAP.put(CmdType.ATTACK, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                long unitId = Long.parseLong(flags[1]);
                int unitType = Integer.parseInt(flags[2]);
                fighterHandle.attack(unitId,unitType);
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
        storeHandle.requestBuyCommodity(goodsName, num);
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
