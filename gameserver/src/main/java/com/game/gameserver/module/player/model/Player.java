package com.game.gameserver.module.player.model;

import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.common.entity.CreatureType;
import com.game.gameserver.common.fsm.StateMachine;
import com.game.gameserver.common.fsm.state.player.PlayerState;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.event.GoldsChangeEvent;
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

import java.util.HashMap;
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

    /**
     * 玩家实体属性
     */
    private final PlayerEntity playerEntity;

    /*** 战斗属性*/
    private PlayerBattle playerBattle;

    /*** 连接信息*/
    private Channel channel;

    /*** 装备栏*/
    private EquipBar equipBar;

    /*** 个人背包Id*/
    private BackBag backBag;

    /**
     * 角色技能
     */
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

    /**
     * 临时数据存放地
     */
    private Map<String, Object> tempData = new HashMap<>();

    /**
     * 角色状态
     */
    private PlayerState state;

    /**
     * 角色攻击目标
     */
    private Creature target;

    /**
     * 角色状态机
     */
    private StateMachine<Player, PlayerState> stateMachine;

    public Player(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
        this.state = PlayerState.PLAYER_LIVE;
        this.stateMachine = new StateMachine<>(this, PlayerState.PLAYER_LIVE);
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
        return playerBattle.getHp();
    }

    @Override
    public void setHp(int hp) {
        playerBattle.setHp(hp);
    }

    @Override
    public int getCurrHp() {
        return playerBattle.getCurrHp();
    }

    @Override
    public void setCurrHp(int value) {
        playerBattle.setCurrHp(value);
    }

    @Override
    public void changeCurrHp(int value) {
        int currHp = getCurrHp() + value;
        if (currHp > getHp()) {
            setCurrHp(getHp());
            return;
        }
        if (currHp < 0) {
            setCurrHp(0);
            return;
        }
        setCurrHp(currHp);
    }


    @Override
    public int getMp() {
        return playerBattle.getMp();
    }

    @Override
    public void setMp(int mp) {
        playerBattle.setMp(mp);
    }

    @Override
    public int getCurrMp() {
        return playerBattle.getCurrMp();
    }

    @Override
    public void setCurrMp(int value) {
        playerBattle.setCurrMp(value);
    }

    @Override
    public void changeCurrMp(int value) {
        int currMp = getCurrMp() + value;
        if (currMp > getMp()) {
            setCurrHp(getMp());
            return;
        }
        if(currMp<0){
            setCurrMp(0);
            return;
        }
        setCurrMp(currMp);
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
        return state == PlayerState.PLAYER_DEAD;
    }

    @Override
    public void setDead(boolean dead) {
        if (dead) {
            if (stateMachine != null) {
                stateMachine
                        .changeState(PlayerState.PLAYER_DEAD);
            }
        }
    }

    @Override
    public void update() {
        if (stateMachine != null) {
            stateMachine.update();
        }
    }

    public void changeState(PlayerState playerState) {
        if (stateMachine != null) {
            stateMachine.changeState(playerState);
        }
    }

    /**
     * 金币变化
     *
     * @param value
     * @return void
     */
    public void goldsChange(int value) {
        int golds = playerEntity.getGolds() + value;
        playerEntity.setGolds(golds);
        // 发出金币改变事件
        EventBus.EVENT_BUS.fire(new GoldsChangeEvent(this));
    }

    public int getGolds() {
        return playerEntity.getGolds();
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

    public void setLevel(int level) {
        this.playerEntity.setLevel(level);
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

    public int getCareerId() {
        return playerEntity.getCareerId();
    }

    public PlayerSkill getPlayerSkill() {
        return playerSkill;
    }

    public PlayerState getState() {
        return state;
    }

    public int getExpr() {
        return playerEntity.getExpr();
    }

    public void setExpr(int expr) {
        playerEntity.setExpr(expr);
    }

}
