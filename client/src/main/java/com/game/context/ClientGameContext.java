package com.game.context;

import com.game.module.account.entity.Account;
import com.game.module.player.entity.Player;
import com.game.module.player.model.Role;
import com.game.module.scene.entity.Scene;
import io.netty.channel.Channel;
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
    private List<Role> roleList;
    /** 角色信息 */
    private Player player;
    /** 角色目前存在的场景 */
    private Scene scene;

    public Integer getRoleIdByRoleName(String roleName){
        for(Role role:roleList){
            if(role.getName().equals(roleName)){
                return role.getId();
            }
        }
        return null;
    }
}
