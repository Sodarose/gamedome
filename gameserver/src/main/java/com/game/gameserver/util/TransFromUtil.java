package com.game.gameserver.util;

import com.game.gameserver.context.ServerContext;
import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.module.player.model.Role;
import com.game.protocol.PlayerProtocol;
import org.springframework.context.ApplicationContext;

/**
 * 转换工具
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class TransFromUtil {
    public static PlayerProtocol.RoleInfo roleTransFromPlayerProtocolRoleInfo(Role role){
        ApplicationContext applicationContext = ServerContext.getApplication();
        DictionaryManager dictionaryManager = applicationContext.getBean(DictionaryManager.class);
        PlayerProtocol.RoleInfo.Builder builder = PlayerProtocol.RoleInfo.newBuilder();
        builder.setId(role.getId());
        builder.setName(role.getName());
        builder.setLevel(role.getLevel());
        builder.setCareer(dictionaryManager.getRoleCareerName(role.getId()));
        return builder.build();
    }
}
