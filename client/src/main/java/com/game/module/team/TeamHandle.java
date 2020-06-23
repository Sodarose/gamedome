package com.game.module.team;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.WordPage;
import com.game.protocol.Message;
import com.game.protocol.TeamProtocol;
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
 * @date 2020/6/22 16:10
 */
@Component
@ModuleHandler(module = ModuleKey.TEAM_MODULE)
public class TeamHandle extends BaseHandler {
    private Map<String, TeamProtocol.TeamInfo> teamInfoMap = new HashMap<>();

    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private WordPage wordPage;

    public void createTeam(int num, String teamName) {
        TeamProtocol.CreateTeamReq.Builder builder = TeamProtocol.CreateTeamReq.newBuilder();
        builder.setNum(num);
        builder.setTeamName(teamName);
        Message req = MessageUtil.createMessage(ModuleKey.TEAM_MODULE, TeamCmd.CREATE_TEAM,
                builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(req);
    }

    @CmdHandler(cmd = TeamCmd.CREATE_TEAM)
    public void receiveCreateTeamResMsg(Message message) {
        try {
            TeamProtocol.CreateTeamRes res = TeamProtocol.CreateTeamRes.parseFrom(message.getData());
            wordPage.print(res.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取团队列表
     */
    public void getTeamList() {
        Message message = MessageUtil.createMessage(ModuleKey.TEAM_MODULE, TeamCmd.TEAM_LIST, null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = TeamCmd.TEAM_LIST)
    public void receiveTeamList(Message message) {
        try {
            teamInfoMap.clear();
            TeamProtocol.TeamListRes res = TeamProtocol.TeamListRes.parseFrom(message.getData());
            for (TeamProtocol.TeamInfo teamInfo : res.getTeamInfoList()) {
                teamInfoMap.put(teamInfo.getTeamName(),teamInfo);
            }
            wordPage.clean();
            wordPage.printTeamList(res.getTeamInfoList());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /** 查看自己的队伍 */
    public void showTeam(){
        Message message = MessageUtil.createMessage(ModuleKey.TEAM_MODULE,TeamCmd.SHOW_TEAM,null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = TeamCmd.SHOW_TEAM)
    public void receiveShowTeam(Message message){
        try {
            TeamProtocol.CheckTeamRes res = TeamProtocol.CheckTeamRes.parseFrom(message.getData());
            if(res.getCode()!=0){
                wordPage.print(res.getMsg());
                return;
            }
            wordPage.clean();
            wordPage.printTeamInfo(res.getTeamInfo());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    public void entryTeam(String teamName){
        TeamProtocol.TeamInfo teamInfo = teamInfoMap.get(teamName);
        if(teamInfo==null){
            wordPage.print("不存在该队伍");
            return;
        }
        TeamProtocol.EntryTeamReq.Builder builder = TeamProtocol.EntryTeamReq.newBuilder();
        builder.setTeamId(teamInfo.getId());
        Message req = MessageUtil.createMessage(ModuleKey.TEAM_MODULE,TeamCmd.ENTRY_TEAM,builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(req);
    }

    @CmdHandler(cmd = TeamCmd.ENTRY_TEAM)
    public void handleEntryTeam(Message message){
        try {
            TeamProtocol.EntryTeamRes teamRes = TeamProtocol.EntryTeamRes.parseFrom(message.getData());
            wordPage.print(teamRes.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void exitTeam(){
        Message message = MessageUtil.createMessage(ModuleKey.TEAM_MODULE,TeamCmd.EXIT_TEAM,null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = TeamCmd.EXIT_TEAM)
    public void handleExitTeam(Message message){
        try {
            TeamProtocol.ExitTeamRes exitTeamRes = TeamProtocol.ExitTeamRes.parseFrom(message.getData());
            wordPage.print(exitTeamRes.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}