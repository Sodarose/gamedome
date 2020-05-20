package com.game.gameserver.game.player;

import com.game.gameserver.game.account.Account;
import com.game.gameserver.game.world.WorldManager;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author xuewenkang
 * @date 2020/5/18 20:31
 */
@Service
public class PlayerService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public final static AttributeKey<Player> PLAYER_ATTRIBUTE_KEY = AttributeKey.newInstance("PLAYER_ATTRIBUTE_KEY");

    @Autowired
    private WorldManager worldManager;

    /**
     * 加载玩家角色
     * @param account 玩家账户信息
     * @param channel 玩家连接Channel
     * @return void
     */
    public void loadPlayer(Account account, Channel channel){
        logger.info("load player ......");
        // 加载角色数据
        Player player = createTestPlayer(account);
        player.setChannel(channel);

        // 进入场景
        worldManager.addPlayerToWorld(player);
    }

    /**
     * 暂时测试用
     * @param account 账户信息
     * @return 玩家数据
     */
    public Player createTestPlayer(Account account){
        Player player = new Player();
        player.setId(0);
        player.setName("康康");
        player.setAccount(account);
        player.setWorldId(0);
        return player;
    }
}
