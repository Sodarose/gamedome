package com.game.gameserver.module.player.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 属性变更记录
 * @author kangkang
 * */
@Data
public class ProperChangeInfo {
    private String propertyName;
    private int diffValue;

    public ProperChangeInfo(String propertyName,int diffValue){
        this.diffValue = diffValue;
        this.propertyName = propertyName;
    }
}
