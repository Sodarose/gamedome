package com.game.module.order.handle;

import com.game.context.ClientGameContext;
import com.game.module.ModuleKey;
import com.game.module.bag.BagCmd;
import com.game.module.bag.entity.Bag;
import com.game.module.bag.entity.Cell;
import com.game.module.player.PlayerCmd;
import com.game.module.gui.WordPage;
import com.game.module.order.CmdType;
import com.game.protocol.BagProtocol;
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


    public void submitCmd(String order){
        // 命令片段
        String[] frags = order.split("\\s+");
        if(frags.length == 0){
            return;
        }
        String cmd = frags[0].toUpperCase();
        Invoke invoke = CMD_INVOKE_MAP.get(cmd);
        if(invoke==null){
            return;
        }
        invoke.invoke(frags);
    }

    public interface Invoke{
        void invoke(String[] flags);
    }

    @PostConstruct
    public void init(){
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
    }

    private void listRoles(String[] flags){
        Message message = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.LIST_ROLES,null);
        gameContext.getChannel().writeAndFlush(message);
        logger.info("发送请求角色列表命令");
    }

    /**
     * 选择角色并登录
     * @param flags 命令
     * @return void
     */
    private void confirmRole(String[] flags){
        if(flags.length<2){
            return;
        }
        String name = flags[1];
        Integer roleId = gameContext.getRoleIdByRoleName(name);
        if(roleId==null){
            wordPage.print("请输入正确的角色名");
            return;
        }
        PlayerProtocol.LoginRole.Builder builder = PlayerProtocol.LoginRole.newBuilder();
        builder.setId(roleId);
        Message message = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.LOGIN_ROLE,builder.build()
                .toByteArray());
        gameContext.getChannel().writeAndFlush(message);
        logger.info("登录角色请求 id {} , name {}",roleId,name);
    }

    /** 产看自己的角色信息 */
    private void selfMessage(String[] flags){
        Message message  = MessageUtil.createMessage(ModuleKey.PLAYER_MODULE, PlayerCmd.PLAYER_INFO,null);
        gameContext.getChannel().writeAndFlush(message);
        logger.info("查询登录的角色信息");
    }

    private void openBag(String[] flags){
        BagProtocol.OpenBag.Builder builder = BagProtocol.OpenBag.newBuilder();
        builder.setBagId(1);
        Message message = MessageUtil.createMessage(ModuleKey.BAG_MODEL, BagCmd.OPEN_BAG,builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    private void useItem(String[] flags){
        String itemName = flags[1];
        Bag bag = gameContext.getPlayer().getBag();
        Cell cell = bag.getCellByItemName(itemName);
        if(cell==null){
            wordPage.print("背包没有该道具");
            return;
        }
        if(cell.getItemType().equals(1)){
            useEquip(cell.getId());
        }else{
            useItem(cell.getId());
        }

    }

    private void useEquip(Integer equipId){
        
    }

    private void useItem(Integer itemId){

    }
}
