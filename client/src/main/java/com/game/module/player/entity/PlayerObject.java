package com.game.module.player.entity;

import com.game.protocol.PlayerProtocol;
import lombok.Data;

/**
 * // 个人角色全部信息
 * message PlayerInfo {
 *     int32 id = 1;
 *     string name = 2;
 *     int32 level = 3;
 *     int32 careerId = 4;
 *     int32 golds = 5;
 *     int32 sceneId = 6;
 *     // 战斗属性
 *     PlayerBattle playerBattle = 7;
 *     // 装备信息
 *     repeated GoodsInfo equipInfo = 8;
 *     // 背包物品
 *     repeated GoodsInfo goodsInfo = 9;
 *     // 技能数据
 *     PlayerSkill playerSkill = 10;
 *     // 角色Buffer信息
 *     repeated BufferInfo bufferInfo = 11;
 * }
 * @author xuewenkang
 * @date 2020/6/1 9:52
 */
@Data
public class PlayerObject {
    private long id;
    private String name;
    private int level;
    private int careerId;
    private int golds;
    private int sceneId;
    private PlayerBattle playerBattle;
}
