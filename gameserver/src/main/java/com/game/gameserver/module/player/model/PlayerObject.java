package com.game.gameserver.module.player.model;

import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.goods.model.EquipBag;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.entity.PlayerBattle;
import com.game.gameserver.module.goods.model.PropsBag;
import com.game.gameserver.module.skill.model.SkillBag;
import com.game.gameserver.util.GenIdUtil;
import io.netty.channel.Channel;

/**
 * 玩家模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:16
 */
public class PlayerObject implements Unit {

    /** 生成的唯一Id */
    private int id;
    /** 用户信息 */
    private Player player;
    /** 战斗属性 */
    private PlayerBattle playerBattle;
    /** 装备栏 */
    private EquipBag equipBag;
    /** 背包 */
    private PropsBag propsBag;
    /** 技能栏 */
    private SkillBag skillBag;

    /** 当前所在的组队 队伍ID */
    private Integer teamId;

    /** 角色连接信息 */
    private Channel channel;

    public PlayerObject(){
        this.id = GenIdUtil.nextId();
    }

    @Override
    public void update() {

    }

    public int getId(){
        return id;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamId(){
        return teamId;
    }

    public void setSkillBag(SkillBag skillBag) {
        this.skillBag = skillBag;
    }

    public SkillBag getSkillBag() {
        return skillBag;
    }

    public PropsBag getPropsBag() {
        return propsBag;
    }

    public void setPropsBag(PropsBag propsBag) {
        this.propsBag = propsBag;
    }

    public void setPlayerBattle(PlayerBattle playerBattle) {
        this.playerBattle = playerBattle;
    }

    public PlayerBattle getPlayerBattle() {
        return playerBattle;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setEquipBag(EquipBag equipBag) {
        this.equipBag = equipBag;
    }

    public EquipBag getEquipBag() {
        return equipBag;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}
