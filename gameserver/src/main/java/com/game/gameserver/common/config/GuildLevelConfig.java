package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 公会等级属性配置
 *
 * @author xuewenkang
 * @date 2020/7/3 11:45
 */
@Data
public class UnionLevelConfig {
    /** 公会等级 */
    @JSONField(name = "level")
    private int level;
    /** 升级需要经验 */
    @JSONField(name = "needExpr")
    private int needExpr;
    /** 会员人数 */
    @JSONField(name = "capacity")
    private int capacity;
    /** 副会长人数 */
    @JSONField(name = "vicePresident")
    private int vicePresident;

    /** 当前公会等级的属性加成 */
    /** hp加成*/
    @JSONField(name = "hp")
    private int hp;
    /** mp加成*/
    @JSONField(name = "mp")
    private int mp;
    /** 攻击力加成*/
    @JSONField(name = "attack")
    private int attack;
    /** 防御力加成*/
    @JSONField(name = "defense")
    private int defense;
}
