package com.game.gameclient.client;

import com.game.gameclient.type.BackBagCmd;
import com.game.gameclient.type.EquipCmd;
import com.game.gameclient.type.ModuleKey;
import com.game.gameclient.type.SceneCmd;
import com.game.gameclient.view.ClientView;
import com.game.message.Message;

/**
 * 接受服务端消息处理
 *
 * @author xuewenkang
 * @date 2020/7/11 0:37
 */
public class MessageHandle {
    public static void handle(Message message){
        // 场景数据
        if(message.getModule()== ModuleKey.SCENE_MODULE&&message.getCmd() == SceneCmd.SYNC){
            ClientView.print2ScenePane(message.getContent());
            return;
        }

        // 人物数据
        if(message.getModule()== ModuleKey.PLAYER_MODULE){
            ClientView.print2PlayerPane(message.getContent());
            return;
        }

        // 装备数据
        if(message.getModule()==ModuleKey.EQUIP_MODULE&&message.getCmd() == EquipCmd.SYNC){
            ClientView.print2EquipPane(message.getContent());
            return;
        }

        // 背包数据
        if(message.getModule()==ModuleKey.BACK_BAG_MODULE&&message.getCmd()== BackBagCmd.SYNC){
            ClientView.print2BackBagPane(message.getContent());
            return;
        }

        // 普通信息
        ClientView.print2Msg(message.getContent());
    }
}
