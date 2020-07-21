package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.game.gameserver.module.buffer.model.Property;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/13 0:56
 */
@Data
public class BufferConfig {

    /** bufferId */
    @JSONField(name = "id")
    private Integer id;

    /** buffer名称 */
    @JSONField(name = "name")
    private String  name;

    /** buffer对hp的影响 */
    @JSONField(name = "hp")
    private int hp;

    /** buffer对mp的影响 */
    @JSONField(name = "mp")
    private int mp;

    /** buffer类型 */
    @JSONField(name = "type")
    private int type;

    /** buffer对属性的影响 */
    @JSONField(name = "property")
    private Property property;

    /** buffer效果Id */
    @JSONField(name = "effect")
    private int effect;

    /** buffer持续时间 */
    @JSONField(name = "duration")
    private long duration;

    /** buffer间隔时间 */
    @JSONField(name = "intervalTime")
    private long intervalTime;

    /** buffer执行次数 */
    @JSONField(name = "times")
    private int times;

    /** buffer结束后 是否删除buffer属性值 */
    @JSONField(name = "deduct")
    private boolean deduct;
}
