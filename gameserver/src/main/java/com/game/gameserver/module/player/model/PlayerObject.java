package com.game.gameserver.module.player.model;

import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.common.entity.UnitType;
import com.game.gameserver.module.ai.fsm.StateMachine;
import com.game.gameserver.module.ai.state.player.PlayerState;
import com.game.gameserver.module.buffer.entity.Buffer;
import com.game.gameserver.module.fighter.type.FighterModeEnum;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.entity.PlayerBattle;
import io.netty.channel.Channel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:16
 */
public class PlayerObject  implements  Serializable,Unit {

    /** 角色数据 */
    private final Player player;
    /** 角色战斗数据 */
    private PlayerBattle playerBattle;
    /** 角色连接信息 */
    private Channel channel;
    /** buff列表 */
    private List<Buffer> buffers;
    /** 当前所在的组队 队伍ID */
    private Long teamId;
    /** 当前玩家所在的副本*/
    private Long instanceId;
    /** 副本次数数据 key 副本Id value 当前次数 */
    private final Map<Integer,Integer> instanceNumMap = new ConcurrentHashMap<>();
    /** 聊天频道  key 频道类型  value 频道Id*/
    private final Map<Integer,Long> playerChannelMap = new ConcurrentHashMap<>();
    /** 状态机 */
    private StateMachine<PlayerObject, PlayerState> stateMachine;
    /** 临时数据*/
    private Map<String,Object> tempData = new ConcurrentHashMap<>();
    /** 战斗模式*/
    private FighterModeEnum fighterModeEnum = FighterModeEnum.ALL;
    /** 攻击的单位 */
    private volatile Unit attackTarget;
    /** 玩家召唤的宝宝 */
    private List<Long> petList = new ArrayList<>();

    public PlayerObject(Player player){
        this.player = player;
        this.playerBattle = new PlayerBattle();
        this.stateMachine = new StateMachine<>(this,PlayerState.NORMAL);
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTeamId(){
        return teamId;
    }


    public void setPlayerBattle(PlayerBattle playerBattle) {
        this.playerBattle = playerBattle;
    }

    public PlayerBattle getPlayerBattle() {
        return playerBattle;
    }

    public Player getPlayer() {
        return player;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setBuffers(List<Buffer> buffers) {
        this.buffers = buffers;
    }

    public List<Buffer> getBuffers(){
        return buffers;
    }

    public Map<Integer, Long> getPlayerChannelMap() {
        return playerChannelMap;
    }

    public void addExpr(int expr){
        expr = this.player.getExpr() +expr;
        this.player.setExpr(expr);
    }

    public int getExpr(){
        return this.getPlayer().getExpr();
    }

    public void addGolds(int value){
        int golds = player.getGolds()+value;
        player.setGolds(golds);
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public boolean isDead(){
        return getCurrState().equals(PlayerState.DEAD);
    }


    public void changeGlobalState(PlayerState playerState){
        if(stateMachine!=null){
            stateMachine.changeGlobalState(playerState);
        }
    }

    public void changeState(PlayerState playerState){
        if(stateMachine!=null){
            stateMachine.changeState(playerState);
        }
    }

    /**
     * 更新
     */
    @Override
    public void update() {
        if(stateMachine!=null){
            stateMachine.update();
        }
    }

    /**
     * 单位类型
     */
    @Override
    public int getUnitType() {
        return UnitType.PLAYER;
    }

    /**
     * 单位Id
     */
    @Override
    public long getUnitId() {
        return player.getId();
    }

    public void revert(){
        playerBattle.setCurrHp(playerBattle.getHp());
        playerBattle.setCurrMp(playerBattle.getMp());
    }

    public Map<String, Object> getTempData() {
        return tempData;
    }

    public FighterModeEnum getFighterModeEnum(){
        return fighterModeEnum;
    }

    public void setFighterModeEnum(FighterModeEnum modeEnum){
        this.fighterModeEnum = modeEnum;
    }


    public PlayerState getGlobalState(){
        if(stateMachine==null){
            return null;
        }
        return stateMachine.getGlobalState();
    }

    public PlayerState getCurrState(){
        if(stateMachine==null){
            return null;
        }
        return stateMachine.getCurrState();
    }

    public void setAttackTarget(Unit attackTarget) {
        this.attackTarget = attackTarget;
    }

    public Unit getAttackTarget() {
        return attackTarget;
    }
}
