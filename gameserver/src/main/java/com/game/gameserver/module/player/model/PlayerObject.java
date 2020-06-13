package com.game.gameserver.module.player.model;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.CareerLevelProperty;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.goods.model.EquipBar;
import com.game.gameserver.module.goods.model.PlayerBag;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.entity.PlayerBattle;
import com.game.gameserver.module.skill.model.PlayerSkill;
import com.game.gameserver.util.GenIdUtil;
import io.netty.channel.Channel;

import java.util.List;

/**
 * 玩家模型对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:16
 */
public class PlayerObject implements Unit {

    /** 生成的唯一Id */
    private final Integer id;
    /** 用户信息 */
    private final Player player;
    /** 战斗属性 */
    private PlayerBattle playerBattle;
    /** 装备栏 */
    private EquipBar equipBar;
    /** 用户背包 */
    private PlayerBag playerBag;
    /** 技能 */
    private PlayerSkill playerSkill;
    /** buff列表 */
    private List<Buffer> buffers;

    /** 当前所在的组队 队伍ID */
    private Integer teamId;
    /** 角色连接信息 */
    private Channel channel;


    public PlayerObject(Player player){
        this.id = GenIdUtil.nextId();
        this.player = player;
    }

    /**
     * 初始化
     *
     * @param
     * @return void
     */
    public void initialize(){
        // 获得职业等级数据
        CareerConfig careerConfig = StaticConfigManager.getInstance().getCareerConfigMap()
                .get(player.getCareerId());
        CareerLevelProperty property = careerConfig.getCareerLevelProperty()
                .get(player.getLevel()/10);
        playerBattle = new PlayerBattle();
        playerBattle.initialize(property);
        // 根据装备调整属性
        playerBattle.addEquipBarProperty(equipBar);
        // 根据buffer调整属性
        playerBattle.addBufferListProperty(buffers);
        playerBattle.reset();
    }

    @Override
    public void update() {

    }

    public Integer getId(){
        return id;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamId(){
        return teamId;
    }

    public void setPlayerSkill(PlayerSkill playerSkill) {
        this.playerSkill = playerSkill;
    }

    public PlayerSkill getPlayerSkill() {
        return playerSkill;
    }

    public PlayerBag getPlayerBag() {
        return playerBag;
    }

    public void setPlayerBag(PlayerBag playerBag) {
        this.playerBag = playerBag;
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

    public void setEquipBar(EquipBar equipBar) {
        this.equipBar = equipBar;
    }

    public EquipBar getEquipBar() {
        return equipBar;
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
}
