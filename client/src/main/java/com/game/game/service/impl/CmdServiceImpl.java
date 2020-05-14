package com.game.game.service.impl;

import com.game.config.MessageType;
import com.game.game.context.ClientContext;
import com.game.game.context.GameContext;
import com.game.game.page.PageManager;
import com.game.game.page.WordPage;
import com.game.game.service.AbstractCmdService;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 解析控制台指令 并发送响应指令到服务器
 * @author: xuewenkang
 * @date: 2020/5/13 12:29
 */
@Service
public class CmdServiceImpl extends AbstractCmdService {
    private final static String CMD_MOVE = "MOVE";
    private final static String CMD_AOI = "AOI";
    private final static String CMD_CUT = "CUT";
    private final static String CMD_CHECK = "CHECK";
    private final static String CMD_SELF = "SELF";
    private final static String CMD_CLEAN="CLEAN";
    private final static String CMD_EXIT = "EXIT";

    private final static int MIN_ORDER_LENGTH = 2;
    private final static int CMD_CHECK_LENGTH = 3;

    private final static String TYPE_ROLE = "ROLE";
    private final static String TYPE_NPC = "NPC";
    private final static String TYPE_MONSTER = "MONSTER";

    @Autowired
    private ClientContext clientContext;

    @Autowired
    private PageManager pageManager;

    @Autowired
    private GameContext gameContext;

    @Autowired
    private WordPage wordPage;

    @Override
    public void initGameClient() {
        clientContext.getChannel().writeAndFlush(MessageUtil.createMessage(MessageType.GAME_SCENE_S,null));
        clientContext.getChannel().writeAndFlush(MessageUtil.createMessage(MessageType.GAME_ROLE_S,null));
        pageManager.showMainPage();
    }


    @Override
    public void runCmd(String cmd) {
        String[] orders = cmd.split("\\s+");
        String order = orders[0].toUpperCase();
        clean();
        switch (order){
            case CMD_MOVE:
                sendCmdMove(orders);
                break;
            case CMD_CUT:
                sendCmdCut(orders);
                break;
            case CMD_CHECK:
                sendCmdCheck(orders);
                break;
            case CMD_AOI:
                sendCmdAoi(orders);
                break;
            case CMD_SELF:
                sendCmdSelf(orders);
                break;
            case CMD_CLEAN:
                clean();
                break;
            case CMD_EXIT:
                exit();
                break;
            default:{
                wordPage.print("【"+cmd+"】无效指令");
            }
        }
    }

    /**
     * 发送当前角色移动命令
     * @param orders
     * @return void
     */
    private void sendCmdMove(String[] orders){
        boolean arrive = false;
        if(orders.length<MIN_ORDER_LENGTH){
            return;
        }

        List<Protocol.Map> ways = gameContext.getScene().getMap().getWaysList();
        int mapId = -1;
        for(Protocol.Map way:ways){
            if(orders[1].equals(way.getName())){
                arrive = true;
                mapId = way.getId();
                break;
            }
        }

        if(!arrive||mapId==-1){
            wordPage.print("路径【"+orders[1]+"】不可达");
            return;
        }

        Protocol.MoveScene moveScene = Protocol.MoveScene.newBuilder()
                .setSceneId(mapId).setMapName(orders[1]).build();
        clientContext.getChannel().writeAndFlush(MessageUtil.createMessage(MessageType.GAME_MOVE_S,moveScene.toByteArray()));
    }

    /**
     * 查看当前所有实体
     * @param orders cmd 命令
     * @return void
     */
    private void sendCmdAoi(String[] orders){
        Protocol.Scene scene = gameContext.getWinScene();
        wordPage.print("AIO命令 打印该场景所有实体属性----------------------------");
        for(Map.Entry<Integer, Protocol.Role> entry:scene.getRolesMap().entrySet()){
            wordPage.print(entry.getValue());

        }
        for(Map.Entry<Integer, Protocol.Npc> entry:scene.getNpcMap().entrySet()){
            wordPage.print(entry.getValue());
        }
        for(Map.Entry<Integer, Protocol.Monster> entry:scene.getMonsterMap().entrySet()){
            wordPage.print(entry.getValue());
        }
        if(scene.getRolesMap().get(gameContext.getRole().getId())==null){
            wordPage.print("当前玩家角色不在该场景中");
        }else{
            wordPage.print("当前玩家在该场景中");
        }
        wordPage.print("AIO命令 打印该场景所有实体属性----------------------------");
    }

    /**
     * 查看当前场景指定实体
     * @param orders cmd 命令
     * @return void
     */
    private void sendCmdCheck(String[] orders){
        if(orders.length<CMD_CHECK_LENGTH){
            return;
        }
        Protocol.Scene scene = gameContext.getWinScene();
        if(TYPE_ROLE.equals(orders[1])){
            Protocol.Role role = null;
            for(Map.Entry<Integer,Protocol.Role> entry:scene.getRolesMap().entrySet()){
                if(entry.getValue().getName().equals(orders[2])){
                    role = entry.getValue();
                    break;
                }
            }
            if(role==null){
                wordPage.print("当前场景无【"+orders[2]+"】角色");
                return;
            }
            wordPage.print("查看角色：");
            wordPage.print(role);
        }else if(TYPE_NPC.equals(orders[1])){
            Protocol.Npc npc = null;
            for(Map.Entry<Integer,Protocol.Npc> entry:scene.getNpcMap().entrySet()){
                if(entry.getValue().getName().equals(orders[2])){
                    npc = entry.getValue();
                    break;
                }
            }
            if(npc==null){
                wordPage.print("当前场景无【"+orders[2]+"】npc");
                return;
            }
            wordPage.print("查看NPC：");
            wordPage.print(npc);
        }else if(TYPE_MONSTER.equals(orders[1])){
            Protocol.Monster monster= null;
            for(Map.Entry<Integer,Protocol.Monster> entry:scene.getMonsterMap().entrySet()){
                if(entry.getValue().getName().equals(orders[2])){
                    monster = entry.getValue();
                    break;
                }
            }
            if(monster==null){
                wordPage.print("当前场景无【"+orders[2]+"】怪物");
                return;
            }
            wordPage.print("查看怪物：");
            wordPage.print(monster);
        }else{
            wordPage.print("命令错误");
        }
    }

    /**
     * 发送查看地图命令 可以查看任意地图场景
     * @param orders cmd 命令
     * @return void
     */
    private void sendCmdCut(String[] orders){
        if(orders.length<MIN_ORDER_LENGTH){
            return;
        }
        Protocol.Map map = Protocol.Map.newBuilder().setName(orders[1]).build();
        Protocol.Scene scene = Protocol.Scene.newBuilder().setMap(map).build();
        clientContext.getChannel().writeAndFlush(MessageUtil.createMessage(MessageType.GAME_CUT_S
                ,scene.toByteArray()));
    }

    /**
     * 发送更新自身信息命令
     * @param orders cmd 命令
     * @return void
     */
    private void sendCmdSelf(String[] orders){
        gameContext.setWinScene(gameContext.getScene());
        clientContext.getChannel().writeAndFlush(MessageUtil.createMessage(MessageType.GAME_SCENE_S,null));
        clientContext.getChannel().writeAndFlush(MessageUtil.createMessage(MessageType.GAME_ROLE_S,null));
    }

    /**
     * 清楚界面数据
     * @param
     * @return void
     */
    private void clean(){
        wordPage.clean();
    }

    /**
     * 退出游戏
     * @param
     * @return void
     */
    private void exit(){
        Message message = MessageUtil.createMessage(MessageType.GAME_EXIT_S,null);
        clientContext.getChannel().writeAndFlush(message);
        wordPage.print("退出游戏");
        pageManager.showLoginAndRegisterPage();
    }

    /**
     * 刷新用户所在场景信息
     */
    @Override
    public void refreshUserScene(){
        clientContext.getChannel().writeAndFlush(MessageUtil.createMessage(MessageType.GAME_SCENE_S,null));
    }
}
