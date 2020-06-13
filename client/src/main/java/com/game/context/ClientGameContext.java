package com.game.context;

import com.game.module.account.entity.Account;
import com.game.module.player.entity.PlayerObject;
import com.game.module.player.entity.SimplePlayerInfo;
import com.game.module.player.model.Player;
import io.netty.channel.Channel;
import javafx.scene.Scene;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/25 12:01
 */
@Data
@Component
public class ClientGameContext {
    /** 客户端游戏账户 */
    private Account account;
    /** 客户端连接通道 */
    private Channel channel;
    /** 用户角色列表 */
    private List<SimplePlayerInfo> playerList;
    /** 角色信息 */
    private PlayerObject playerObject;
    /** 角色目前存在的场景 */
    private Scene scene;

    public Integer getRoleIdByRoleName(String roleName){
        for(SimplePlayerInfo info : playerList){
            if(info.getName().equals(roleName)){
                return info.getId();
            }
        }
        return null;
    }
}
