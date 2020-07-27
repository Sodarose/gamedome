package com.game.gameserver.module.player.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.LevelConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.fsm.state.player.PlayerState;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.EquipChangeEvent;
import com.game.gameserver.event.event.LevelUpEvent;
import com.game.gameserver.event.event.LogoutEvent;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.dao.PlayerDbService;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.model.PlayerBattle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * 玩家属性服务
 *
 * @author xuewenkang
 * @date 2020/7/10 14:25
 */
@Listener
@Service
public class PlayerDataService {


    @Autowired
    private PlayerDbService playerDbService;

    public static PlayerDataService instance;

    public PlayerDataService() {
        instance = this;
    }

    /**
     * 初始化战斗属性
     *
     * @param player
     * @return void
     */
    public void initPlayerBattle(Player player) {
        PlayerBattle playerBattle = new PlayerBattle();
        player.setPlayerBattle(playerBattle);
        initProperty(player);
    }


    /**
     * 增加经验
     *
     * @param player
     * @param value
     * @return void
     */
    public void addExpr(Player player, int value) {
        // 等级配置
        LevelConfig levelConfig = StaticConfigManager.getInstance().getLevelConfigMap().get(player.getLevel() + 1);
        // 已经达到最大等级了
        if (levelConfig == null) {
            return;
        }
        // 当前等级
        int level = player.getLevel();
        int exprAmount = player.getExpr() + value;
        // 经验足够 则升级
        while (exprAmount > levelConfig.getNeedExpr()) {
            // 升级
            player.setLevel(player.getLevel() + 1);
            // 减去经验
            exprAmount -= levelConfig.getNeedExpr();
            // 通知
            NotificationHelper.notifyScene(player.getScene(), MessageFormat.format("玩家 {0} 升至 {1}", player.getName(),
                    player.getLevel()));
            // 获取新的等级
            levelConfig = StaticConfigManager.getInstance().getLevelConfigMap().get(player.getLevel() + 1);
            // 已经满级了
            if (levelConfig == null) {
                exprAmount = 0;
                break;
            }
        }
        // 发出升级事件
        if (player.getLevel() > level) {
            initProperty(player);
            EventBus.EVENT_BUS.fire(new LevelUpEvent(player));
        }
        // 剩余经验
        player.setExpr(exprAmount);
        NotificationHelper.syncPlayer(player);
    }


    /**
     * 初始化属性
     *
     * @param player
     * @return void
     */
    public void initProperty(Player player) {
        // 清除buff
        player.getBuffers().clear();
        // 清除状态
        player.changeState(PlayerState.PLAYER_LIVE);
        // 计算属性
        calculatePlayerProperty(player);
    }

    /**
     * @param player
     * @return void
     */
    public void calculatePlayerProperty(Player player) {
        int level = player.getLevel();
        LevelConfig levelConfig = StaticConfigManager.getInstance().getLevelConfigMap().get(level);
        if (levelConfig == null) {
            return;
        }
        // 基本等价属性属性
        int hp = levelConfig.getProperty().getHp();
        int mp = levelConfig.getProperty().getMp();
        int attack = levelConfig.getProperty().getAttack();
        int defense = levelConfig.getProperty().getDefense();

        // 加载装备属性
        for (Map.Entry<Integer, Item> entry : player.getEquipBar().getEquipMap().entrySet()) {
            // 耐久不足
            if (entry.getValue().getDurability() == 0) {
                continue;
            }
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(entry.getValue().getItemConfigId());
            hp += itemConfig.getProperty().getHp();
            mp += itemConfig.getProperty().getMp();
            attack += itemConfig.getProperty().getAttack();
            defense += itemConfig.getProperty().getDefense();
        }

        //buffer加成 暂时不弄
        /*List<Buffer> buffers = player.getBuffers();
        for (Buffer buffer : buffers) {
            hp += buffer.getBufferConfig().getProperty().getHp();
            mp += buffer.getBufferConfig().getProperty().getMp();
            attack = buffer.getBufferConfig().getProperty().getAttack();
            defense = buffer.getBufferConfig().getProperty().getDefense();
        }*/

        // 设置属性
        player.getPlayerBattle().setHp(hp);
        player.getPlayerBattle().setMp(mp);
        player.getPlayerBattle().setDefense(defense);
        player.getPlayerBattle().setAttack(attack);

        if (PlayerState.PLAYER_LIVE == player.getState()) {
            player.setCurrHp(hp);
            player.setCurrMp(mp);
        }
        // 属性同步
        NotificationHelper.syncPlayer(player);
    }


    @EventHandler
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        Player player = logoutEvent.getPlayer();
        playerDbService.updateAsync(player.getPlayerEntity());
    }

    @EventHandler
    public void handleEquipChangeEvent(EquipChangeEvent equipChangeEvent) {
        Player player = equipChangeEvent.getPlayer();
        if (player != null) {
            calculatePlayerProperty(player);
        }
    }
}
