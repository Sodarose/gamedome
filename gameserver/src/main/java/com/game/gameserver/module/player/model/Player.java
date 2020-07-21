package com.game.gameserver.module.player.model;

import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.entity.CreatureType;
import com.game.gameserver.common.fsm.StateMachine;
import com.game.gameserver.common.fsm.state.player.PlayerState;
import com.game.gameserver.module.backbag.model.BackBag;
import com.game.gameserver.module.equipment.model.EquipBar;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.fighter.type.FighterModeEnum;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.skill.model.PlayerSkill;
import com.game.gameserver.module.skill.model.Skill;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 玩家域模型
 *
 * @author xuewenkang
 * @date 2020/7/10 0:16
 */
@Data
public class Player implements Creature {

    /** 玩家实体属性*/
    private final PlayerEntity playerEntity;

    /*** 战斗属性*/
    private PlayerBattle playerBattle;

    /*** 连接信息*/
    private Channel channel;

    /*** 装备栏*/
    private EquipBar equipBar;

    /*** 个人背包Id*/
    private BackBag backBag;

    /** 角色技能 */
    private PlayerSkill playerSkill;

    /*** 当前所在的组队 队伍ID*/
    private Long teamId;

    /*** 当前所在场景*/
    private Scene currScene;

    /*** 当前战斗模式*/
    private FighterModeEnum fighterModeEnum = FighterModeEnum.PEACE;

    /*** 角色是否在线*/
    private volatile boolean online = false;

    /*** 角色Buff*/
    private List<Buffer> buffers = new CopyOnWriteArrayList<>();

    /*** 技能CD表*/
    private Map<Integer, Skill> skillCdMap = new ConcurrentHashMap<>();

    /*** 道具CD表*/
    private Map<Integer, Item> itemCdMap = new ConcurrentHashMap<>();

    /*** 角色当前交易Id*/
    private Long currTrade;

    /** 角色状态 */
    private PlayerState state;

    /** 角色攻击目标 */
    private Creature target;

    /** 角色状态机 */
    private StateMachine<Player,PlayerState> stateMachine;

    public Player(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
        this.state = PlayerState.PLAYER_LIVE;
        this.stateMachine = new StateMachine<>(this,PlayerState.PLAYER_LIVE);
    }

    public void setTarget(Creature target){
        this.target = target;
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
    public long getId() {
        return playerEntity.getId();
    }

    @Override
    public String getName() {
        return playerEntity.getName();
    }

    @Override
    public int getHp() {
        return playerBattle.getMaxHp();
    }

    @Override
    public void setHp(int hp) {
        playerBattle.setMaxHp(hp);
    }

    @Override
    public int getCurrHp() {
        return playerBattle.getHp();
    }

    @Override
    public void setCurrHp(int value) {
        playerBattle.setHp(value);
    }

    @Override
    public int getMp() {
        return playerBattle.getMaxMp();
    }

    @Override
    public void setMp(int mp) {
        playerBattle.setMaxMp(mp);
    }

    @Override
    public int getCurrMp() {
        return playerBattle.getMp();
    }

    @Override
    public void setCurrMp(int value) {
        playerBattle.setMp(value);
    }

    @Override
    public int getAttack() {
        return playerBattle.getAttack();
    }

    @Override
    public void setAttack(int attack) {
        playerBattle.setAttack(attack);
    }

    @Override
    public int getDefense() {
        return playerBattle.getDefense();
    }

    @Override
    public void setDefense(int defense) {
        playerBattle.setDefense(defense);
    }

    @Override
    public List<Buffer> getBuffers() {
        return buffers;
    }

    @Override
    public CreatureType getType() {
        return CreatureType.PLAYER;
    }

    @Override
    public Map<Integer, Skill> getInCdSkill() {
        return skillCdMap;
    }

    @Override
    public Scene getScene() {
        return currScene;
    }

    @Override
    public Skill getSkill(int skillId) {
        return playerSkill.getSkillMap().get(skillId);
    }

    @Override
    public Map<Integer, Skill> getSkillMap() {
        return null;
    }

    @Override
    public boolean isDead() {
        return state ==PlayerState.PLAYER_DEAD;
    }

    @Override
    public void setDead(boolean dead) {
        if(dead){
            if(stateMachine!=null){
                stateMachine
                        .changeState(PlayerState.PLAYER_DEAD);
            }
        }
    }

    /**
     * 得到攻击目标 AI使用
     */
    @Override
    public Creature getTarget() {
        return target;
    }

    @Override
    public void update() {
        if(stateMachine!=null){
            stateMachine.update();
        }
    }

    public void changeState(PlayerState playerState){
        if(stateMachine!=null){
            stateMachine.changeState(playerState);
        }
    }

    public void addGolds(int value) {
        int golds = playerEntity.getGolds() + value;
        playerEntity.setGolds(golds);
    }

    public void decrease(int value) {
        int golds = playerEntity.getGolds() - value;
        playerEntity.setGolds(golds);
    }

    public Long getGuildId() {
        return playerEntity.getGuildId();
    }

    public void setGuildId(Long guildId) {
        playerEntity.setGuildId(guildId);
    }

    public int getLevel() {
        return playerEntity.getLevel();
    }

    public Map<Integer, Item> getItemCdMap() {
        return itemCdMap;
    }

    public void setPlayerSkill(PlayerSkill playerSkill) {
        this.playerSkill = playerSkill;
    }

    public Map<Integer, Skill> getSkillCdMap() {
        return skillCdMap;
    }

    public int getGolds(){
        return playerEntity.getGolds();
    }

    public int getCareerId(){
        return playerEntity.getCareerId();
    }

    public PlayerSkill getPlayerSkill() {
        return playerSkill;
    }

    public PlayerState getState() {
        return state;
    }
}
