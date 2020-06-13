package com.game.util;


import com.game.module.player.entity.SimplePlayerInfo;
import com.game.protocol.PlayerProtocol;
import org.springframework.beans.BeanUtils;

/**
 * 转换工具
 *
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class TransFromUtil {

    public static SimplePlayerInfo transFromSimplePlayerInfo(PlayerProtocol.SimplePlayerInfo protocol){
        SimplePlayerInfo simplePlayerInfo = new SimplePlayerInfo();
        BeanUtils.copyProperties(protocol,simplePlayerInfo);
        return simplePlayerInfo;
    }


}
