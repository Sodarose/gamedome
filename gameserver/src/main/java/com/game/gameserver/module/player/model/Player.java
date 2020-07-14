package com.game.gameserver.module.player.model;

import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.module.backbag.model.BackBag;
import com.game.gameserver.module.equipment.model.EquipBar;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.fighter.type.FighterModeEnum;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.scene.model.Scene;
import io.netty.channel.Channel;

import java.util.List;

/**
 * 玩家域模型
 *
 * @author xuewenkang
 * @date 2020/7/10 0:16
 */
public class Player implements Creature {
    /** 玩家实体属性 */
    private final PlayerEntity playerEntity;
    /** 战斗属性 */
    private PlayerBattle playerBattle;
    /** 连接信息 */
    private Channel channel;
    /** 装备栏 */
    private EquipBar equipBar;
    /** 个人背包Id*/
    private BackBag backBag;
    /** 当前所在的组队 队伍ID*/
    private Long teamId;
    /** 当前所在场景 */
    private Scene currScene;
    /** 当前战斗模式*/
    private FighterModeEnum fighterModeEnum = FighterModeEnum.PEACE;
    /** 角色是否在线 */
    private volatile boolean online = false;
    /** 角色状态*/

    /** 角色Buff*/
    private List<Buffer> buffers;
    /** 角色当前交易 */
    private Long currTrade;

    public Player(PlayerEntity playerEntity){
        this.playerEntity = playerEntity;
    }

    public Long getCurrTrade() {
        return currTrade;
    }

    public void setCurrTrade(Long currTrade) {
        this.currTrade = currTrade;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public void setCurrScene(Scene currScene) {
        this.currScene = currScene;
    }

    public Scene getCurrScene() {
        return currScene;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setFighterModeEnum(FighterModeEnum fighterModeEnum) {
        this.fighterModeEnum = fighterModeEnum;
    }

    public FighterModeEnum getFighterModeEnum() {
        return fighterModeEnum;
    }

    public void setBackBag(BackBag backBag) {
        this.backBag = backBag;
    }

    public BackBag getBackBag() {
        return backBag;
    }

    public void setEquipBar(EquipBar equipBar) {
        this.equipBar = equipBar;
    }

    public EquipBar getEquipBar() {
        return equipBar;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerBattle(PlayerBattle playerBattle) {
        this.playerBattle = playerBattle;
    }

    public PlayerBattle getPlayerBattle() {
        return playerBattle;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString(){
        return "";
    }


    @Override
    public long getId() {
        return playerEntity.getId();
    }

    @Override
    public String getName() {
        return playerEntity.getName();
    }

    public void addGolds(int value){
        int golds = playerEntity.getGolds()+value;
        playerEntity.setGolds(golds);
    }

    public void decrease(int value){
        int golds = playerEntity.getGolds()-value;
        playerEntity.setGolds(golds);
    }

    public Long getGuildId(){
        return playerEntity.getGuildId();
    }

    public void setGuildId(Long guildId){
        playerEntity.setGuildId(guildId);
    }
}
