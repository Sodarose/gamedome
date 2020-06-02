package com.game.util;

/**
 * @author xuewenkang
 * @date 2020/6/1 16:31
 */
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/26 16:30
 */
public class StaticData {
    /** 职业ID 对应的职业名称
     *  1	狂战士
     *  2	剑魂
     *  3	阿修罗
     *  4	鬼泣
     * */
    public static Map<Integer,String> careerMap = new HashMap<>();

    /**
     * 品质ID 对应的品质名称
     * 1	普通
     * 2	高级
     * 3	稀有
     * 4	神器
     * 5	传说
     * 6	史诗
     */
    public static Map<Integer,String> qualityMap = new HashMap<>();

    /***
     * 装备部位Id 对应的部位名称
     * 0	胸甲
     * 1	肩甲
     * 2	腰带
     * 3	裤子
     * 4	鞋子
     * 5	武器
     * 6	称号
     * 7	手镯
     * 8	项链
     * 9	戒指
     * 10	辅助装备
     * 11	耳环
     * 12	魔法石
     */
    public static Map<Integer,String> equipPart = new HashMap<>();
    static {
        careerMap.put(1,"狂战士");
        careerMap.put(2,"剑魂");
        careerMap.put(3,"阿修罗");
        careerMap.put(4,"鬼泣");

        qualityMap.put(1,"普通");
        qualityMap.put(2,"高级");
        qualityMap.put(3,"稀有");
        qualityMap.put(4,"神器");
        qualityMap.put(5,"传说");
        qualityMap.put(6,"史诗");

        equipPart.put(0,"胸甲");
        equipPart.put(1,"肩甲");
        equipPart.put(2,"腰带");
        equipPart.put(3,"裤子");
        equipPart.put(4,"鞋子");
        equipPart.put(5,"武器");
        equipPart.put(6,"称号");
        equipPart.put(7,"手镯");
        equipPart.put(8,"项链");
        equipPart.put(9,"戒指");
        equipPart.put(10,"辅助装备");
        equipPart.put(11,"耳环");
        equipPart.put(12,"魔法石");
    }

}
