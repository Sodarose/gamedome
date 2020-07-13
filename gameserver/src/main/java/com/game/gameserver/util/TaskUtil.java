package com.game.gameserver.util;

import com.game.gameserver.common.config.*;
import com.game.gameserver.event.EventType;
import com.game.gameserver.module.task.entity.TaskProgress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务要求解析器
 *
 * @author xuewenkang
 * @date 2020/7/1 14:32
 */
public class TaskUtil {

    /**
     * 解析任务要求
     *
     * @param taskRequire
     * @return java.util.List<com.game.gameserver.module.task.entity.TaskProgress>
     */
    public static List<TaskProgress> parserTaskRequire(String taskRequire) {
        List<TaskProgress> taskProgresses = new ArrayList<>();
        String[] requireStrs = taskRequire.split("\\|");
        for (String requireStr : requireStrs) {
            String[] require = requireStr.split("_");
            // 行为类型
            int type = Integer.parseInt(require[0]);
            // 任务目标
            int target = Integer.parseInt(require[1]);
            // 要求数量
            int amount = Integer.parseInt(require[2]);
            // 创建任务要求/进度
            TaskProgress taskProgress = new TaskProgress(type, target, amount);
            // 加入
            taskProgresses.add(taskProgress);
        }
        return taskProgresses;
    }



    /**
     * 解析进度为字符串
     *
     * @param taskProgress
     * @return java.lang.String
     */
    public static String parserTaskRequireByStr(TaskProgress taskProgress) {
        int type = taskProgress.getType();
        StringBuilder builder = new StringBuilder();
        switch (type) {
            // 杀敌任务
            /*case EventType.EVENT_TYPE_MONSTER_DEAD:
                // 获取目标
                MonsterConfig monsterConfig = StaticConfigManager
                        .getInstance().getMonsterConfigMap().get(taskProgress.getTarget());
                if (monsterConfig == null) {
                    return "";
                }
                // 获取需要击杀数
                int amount = taskProgress.getAmount();
                // 获取当前已经击杀数
                int num = taskProgress.getNum();
                builder.append("击杀怪物").append(amount).append("只").append(monsterConfig.getName())
                        .append("\t").append("当前进度").append(num).append("/").append(amount);
                return builder.toString();
            // 升级任务
            case EventType.EVENT_TYPE_LEVEL_UP:
                return "";
            default:
                return "";*/
        }
        return "";
    }


    /**
     * 将道具解析为字符串
     *
     * @param propsMap
     * @return java.lang.String
     */
    public static String parserPropsAward2Str(Map<Integer, Integer> propsMap){
        StringBuilder result = new StringBuilder();
      /*  for (Map.Entry<Integer, Integer> entry : propsMap.entrySet()) {
            // 获取道具资源
            PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(entry.getKey());
            if (propConfig == null) {
                continue;
            }
            result.append(propConfig.getName()).append(entry.getValue()).append("\n");
        }*/
        return result.toString();
    }

    /**
     * 将装备解析为字符串
     *
     * @param equipList
     * @return java.lang.String
     */
    public static String parserEquipAward2Str(List<Integer> equipList){
        StringBuilder result = new StringBuilder();
        for (Integer equipId : equipList) {
         /*   // 获取装备资源
            EquipConfig equipConfig = StaticConfigManager.getInstance().getEquipConfigMap().get(equipId);
            if (equipConfig == null) {
                continue;
            }
            result.append(equipConfig.getName()).append("\n");*/
        }
        return result.toString();
    }

    /** 解析道具奖励 */
    public static Map<Integer, Integer> parserPropAwards(String props) {
        String[] propAwardStrs = props.split("\\|");
        Map<Integer, Integer> propAwards = new HashMap<>(propAwardStrs.length);
        for (String propAwardStr : propAwardStrs) {
            String[] propAward = propAwardStr.split("_");
            if (propAward.length != 2) {
                continue;
            }
            int propConfigId = Integer.parseInt(propAward[0]);
            int num = Integer.parseInt(propAward[1]);
            propAwards.put(propConfigId, num);
        }
        return propAwards;
    }

    /** 解析装备奖励 */
    public static List<Integer> parserEquipAwards(String equips) {
        List<Integer> equipAwards = new ArrayList<>();
        String[] equipAwardStrs = equips.split("\\|");
        for (String equipAward : equipAwardStrs) {
            int equipConfigId = Integer.parseInt(equipAward);
            equipAwards.add(equipConfigId);
        }
        return equipAwards;
    }
}
