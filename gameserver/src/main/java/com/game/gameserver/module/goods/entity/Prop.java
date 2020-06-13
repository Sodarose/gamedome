package com.game.gameserver.module.goods.entity;

import com.game.gameserver.common.config.PropConfig;
import com.game.gameserver.util.GenIdUtil;

/**
 * 道具
 *
 * @author xuewenkang
 * @date 2020/6/10 10:28
 */
public class Prop extends Goods {
    /** 道具静态属性 */
    private PropConfig propConfig;

    public Prop(){
        this.id = GenIdUtil.nextId();
    }

    public PropConfig getPropConfig() {
        return propConfig;
    }

    public void setPropConfig(PropConfig propConfig) {
        this.propConfig = propConfig;
    }
}
