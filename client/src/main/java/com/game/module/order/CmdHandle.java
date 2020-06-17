package com.game.module.order.handle;

import com.game.context.ClientGameContext;
import com.game.module.ModuleKey;
import com.game.module.player.PlayerCmd;
import com.game.module.gui.WordPage;
import com.game.module.order.CmdType;
import com.game.module.player.entity.PlayerObject;
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
        CMD_INVOKE_MAP.put(CmdType.CONFIRM_ROLE, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                confirmRole(flags);
            }
        });

        // self message
        CMD_INVOKE_MAP.put(CmdType.SELF_MESSAGE, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                selfMessage(flags);
            }
        });

        // open bag
        CMD_INVOKE_MAP.put(CmdType.OPEN_BAG, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                openBag(flags);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.USER_ITEM, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                useItem(flags);
            }
        });

        CMD_INVOKE_MAP.put(CmdType.OPEN_EQUIP, new Invoke() {
            @Override
            public void invoke(String[] flags) {
                openEquipBar(flags);
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
        String name = flags[1];
        Integer roleId = gameContext.getRoleIdByRoleName(name);
        if (roleId == null) {
            wordPage.print("请输入正确的角色名");
            return;
        }
/*        PlayerProtocol.LoginPlayer.Builder builder = PlayerProtocol.LoginPlayer.newBuilder();
        builder.setPlayerId(roleId);
        Message message = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.LOGIN_PLAYER,builder.build()
                .toByteArray());
        gameContext.getChannel().writeAndFlush(message);
        logger.info("登录角色请求 id {} , name {}",roleId,name);*/
    }

    /**
     * 产看自己的角色信息
     */
    private void selfMessage(String[] flags) {
        PlayerObject playerObject = gameContext.getPlayerObject();
        if (playerObject == null) {
            wordPage.print("您还没有登录角色");
            return;
        }
        wordPage.print(playerObject);
        logger.info("查询登录的角色信息");
    }

    private void openBag(String[] flags) {
        PlayerObject playerObject = gameContext.getPlayerObject();
        if (playerObject == null) {
            wordPage.print("您还没有登录角色");
            return;
        }
        /*wordPage.print(playerObject.getBag());*/
    }

    private void openEquipBar(String[] flags) {
        PlayerObject playerObject = gameContext.getPlayerObject();
        if (playerObject == null) {
            wordPage.print("您还没有登录角色");
            return;
        }
        /* wordPage.print(playerObject.getEquipBar());*/
    }

    private void useItem(String[] flags) {


    }

    private void useEquip(Integer equipId) {

    }

    private void useItem(Integer itemId) {

    }

    public void requestSceneInfo() {

    }

    public void requestPlayerInfo() {

    }

    public void requestEquip() {

    }

    public void requestBag() {

    }

    public void requestSkill() {

    }

    private void showStore(String[] flags) {
        storeHandle.requestCommodityList();
    }

    private void buy(String[] flags) {
        if(flags.length!=3){
            return;
        }
        String goodsName = flags[1];
        int num = Integer.parseInt(flags[2]);
        storeHandle.requestByCommodity(goodsName,num);
    }

    private void sell(String[] flags) {
        if(flags.length!=3){
            return;
        }
        String goodsName = flags[1];
        int num = Integer.parseInt(flags[2]);
        storeHandle.requestShellGoods(goodsName,num);
    }
}
