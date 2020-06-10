package com.game.module.player.model;

import lombok.Data;

/**
 * 简要角色信息
 *
 * @author xuewenkang
 * @date 2020/6/10 11:30
 */
@Data
public class BriefPlayerInfo {
    private int id;
    private String name;
    private int level;
    private int career;
}
