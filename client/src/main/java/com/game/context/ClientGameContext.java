package com.game.context;

import com.game.module.account.entity.Account;
import com.game.module.game.model.Role;
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

    public Integer getRoleIdByRoleName(String roleName){
        for(Role role:roleList){
            if(role.getName().equals(roleName)){
                return role.getId();
            }
        }
        return null;
    }
}
