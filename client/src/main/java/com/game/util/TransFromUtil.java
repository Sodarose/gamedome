package com.game.util;


import com.game.module.player.model.BriefPlayerInfo;
import com.game.protocol.PlayerProtocol;
import org.springframework.beans.BeanUtils;

/**
 * 转换工具
 *
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class TransFromUtil {
    public static BriefPlayerInfo transFromBriefPlayerIn(PlayerProtocol.BriefPlayerInfo protocol){
        BriefPlayerInfo briefPlayerInfo = new BriefPlayerInfo();
        BeanUtils.copyProperties(protocol,briefPlayerInfo);
        return briefPlayerInfo;
    }
}
