package com.game.module.fighter;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.WordPage;
import com.game.protocol.FighterProtocol;
import com.game.protocol.Message;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.color.CMMException;

/**
 * @author xuewenkang
 * @date 2020/6/5 6:50
 */
@Component
@ModuleHandler(module = ModuleKey.FIGHTER_MODEL)
public class FighterHandle extends BaseHandler {

    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private WordPage wordPage;

    public void useSkill(long unitId,int unitType,int skillId){
        FighterProtocol.UseSkillReq.Builder builder = FighterProtocol.UseSkillReq.newBuilder();
        builder.setUnitId(unitId);
        builder.setUnitType(unitType);
        builder.setSkillId(skillId);
        Message message = MessageUtil.createMessage(ModuleKey.FIGHTER_MODEL,FighterCmd.USE_SKILL,builder.build()
                .toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    public void attack(long unitId,int unitType){
        FighterProtocol.AttackReq.Builder builder = FighterProtocol.AttackReq.newBuilder();
        builder.setUnitId(unitId);
        builder.setUnitType(unitType);
        Message message = MessageUtil.createMessage(ModuleKey.FIGHTER_MODEL,FighterCmd.ATTACK,
                builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = FighterCmd.ATTACK)
    public void receiveAttackRes(Message message){
        try {
            FighterProtocol.AttackRes attackRes = FighterProtocol.AttackRes.parseFrom(message.getData());
            if(attackRes.getCode()!=0){
                wordPage.print(attackRes.getMsg());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void changeFighterModel(int model){
        FighterProtocol.ChangeModelReq.Builder builder = FighterProtocol.ChangeModelReq.newBuilder();
        builder.setFighterModuleId(model);
        Message message = MessageUtil.createMessage(ModuleKey.FIGHTER_MODEL,FighterCmd.CHANGE_MODEL,
                builder.build().toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = FighterCmd.CHANGE_MODEL)
    public void receiveChangeFighterModelRes(Message message){
        try {
            FighterProtocol.ChangeModelRes res = FighterProtocol.ChangeModelRes.parseFrom(message.getData());
            wordPage.print(res.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = FighterCmd.USE_SKILL)
    public void receiveUserSkillRes(Message message){
        try {
            FighterProtocol.UseSkillRes res = FighterProtocol.UseSkillRes.parseFrom(message.getData());
            wordPage.print(res.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
