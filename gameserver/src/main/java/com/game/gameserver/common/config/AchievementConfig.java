package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.game.gameserver.util.TaskUtil;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 成就资源类
 *
 * @author xuewenkang
 * @date 2020/7/2 11:58
 */
@Data
public class AchievementConfig {
    /** 成就Id */
    @JSONField(name = "id")
    int id;

    /** 成就Id */
    @JSONField(name = "name")
    String name;

    /** 成就Id */
    @JSONField(name = "limitLevel")
    int limitLevel;

    /** 成就Id */
    @JSONField(name = "taskRequire")
    String taskRequire;

    /** 成就Id */
    @JSONField(name = "taskRequireStr")
    String taskRequireStr;

    /** 成就Id */
    @JSONField(name = "expr")
    int expr;

    /** 成就Id */
    @JSONField(name = "golds")
    int golds;

    /** 成就Id */
    @JSONField(name = "props")
    String props;

    /** 成就Id */
    @JSONField(name = "equips")
    String equips;

    /** 道具奖励*/
    @JSONField(serialize = false)
    private Map<Integer, Integer> propAwards;

    /** 装备奖励*/
    @JSONField(serialize = false)
    private List<Integer> equipAwards;

    public Map<Integer, Integer> getPropAwards() {
        if (propAwards != null) {
            return propAwards;
        }
        propAwards = TaskUtil.parserPropAwards(props);
        return propAwards;
    }

    public List<Integer> getEquipAwards() {
        if (equipAwards != null) {
            return equipAwards;
        }
        equipAwards = TaskUtil.parserEquipAwards(equips);
        return equipAwards;
    }
}
