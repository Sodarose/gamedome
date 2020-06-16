package com.game.gameserver.module.player.model;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.CareerLevelProperty;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.goods.model.EquipBag;
import com.game.gameserver.module.goods.model.PlayerBag;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.entity.PlayerBattle;
import com.game.gameserver.module.skill.model.PlayerSkill;
import com.game.gameserver.util.GameUUID;
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
public class PlayerObject  implements Unit, Serializable {

    /** 角色数据 */
    private final Player player;
    /** 角色战斗数据 */
    private PlayerBattle playerBattle;

    /** buff列表 */
    private List<Buffer> buffers;
    /** 当前所在的组队 队伍ID */
    private Integer teamId;
    /** 角色连接信息 */
    private Channel channel;

    /** 副本次数数据 */
    private final Map<Integer,Integer> instanceNumMap = new ConcurrentHashMap<>();
    /** 聊天频道  key 频道类型  value 频道Id*/
    private final Map<Integer,Long> playerChannelMap = new ConcurrentHashMap<>();

    public PlayerObject(Player player){
        this.player = player;
    }


    @Override
    public void update() {

    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamId(){
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

}
