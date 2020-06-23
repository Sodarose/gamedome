package com.game.module.instance;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.ScenePage;
import com.game.module.gui.WordPage;
import com.game.protocol.InstanceProtocol;
import com.game.protocol.Message;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/22 11:19
 */
@Component
@ModuleHandler(module = ModuleKey.INSTANCE_MODULE)
public class InstanceHandle extends BaseHandler {

    private Map<String, InstanceProtocol.InstanceConfigInfo> infoMap = new HashMap<>();

    private InstanceProtocol.InstanceInfo currInstanceInfo = null;

    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private ScenePage scenePage;
    @Autowired
    private WordPage wordPage;

    /**
     * 查看副本列表
     *
     * @param
     * @return void
     */
    public void showInstanceList() {
        Message reqMsg = MessageUtil.createMessage(ModuleKey.INSTANCE_MODULE, InstanceCmd.INSTANCE_LIST, null);
        gameContext.getChannel().writeAndFlush(reqMsg);
    }

    public void entryInstance(String instanceName,boolean team){
        InstanceProtocol.InstanceConfigInfo info = infoMap.get(instanceName);
        if(info==null){
            wordPage.print("错误的副本名称");
            return;
        }
        InstanceProtocol.EntryInstanceReq.Builder builder = InstanceProtocol.EntryInstanceReq.newBuilder();
        builder.setInstanceId(info.getInstanceConfigId());
        builder.setTeam(team);
        Message message = MessageUtil.createMessage(ModuleKey.INSTANCE_MODULE
                ,InstanceCmd.ENTRY_INSTANCE,builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    public void exitInstance(){
        Message message = MessageUtil.createMessage(ModuleKey.INSTANCE_MODULE,InstanceCmd.EXIT_INSTANCE,null);
        gameContext.getChannel().writeAndFlush(message);
    }


    @CmdHandler(cmd = InstanceCmd.INSTANCE_LIST)
    public void receiveInstanceInfoList(Message message) {
        try {
            InstanceProtocol.InstanceInfoListRes res = InstanceProtocol
                    .InstanceInfoListRes.parseFrom(message.getData());
            for (InstanceProtocol.InstanceConfigInfo instanceInfo : res.getInstanceInfoListList()) {
                infoMap.put(instanceInfo.getInstanceName(),instanceInfo);
            }
            wordPage.clean();
            wordPage.printInstanceInfo(res.getInstanceInfoListList());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = InstanceCmd.ENTRY_INSTANCE)
    public void receiveEntryInstanceMsg(Message message){
        try {
            InstanceProtocol.EntryInstanceRes res = InstanceProtocol.EntryInstanceRes
                    .parseFrom(message.getData());
            if(res.getCode()!=0){
                wordPage.print(res.getMsg());
                return;
            }
            this.currInstanceInfo = res.getInstanceInfo();
            scenePage.clean();
            scenePage.print(currInstanceInfo);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler( cmd = InstanceCmd.SYNC_INSTANCE_INFO)
    public void receiveSyncInstanceInfoMsg(Message message){
        try {
            InstanceProtocol.InstanceInfo info = InstanceProtocol.InstanceInfo.parseFrom(message.getData());
            this.currInstanceInfo = info;
            scenePage.clean();
            scenePage.print(info);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
