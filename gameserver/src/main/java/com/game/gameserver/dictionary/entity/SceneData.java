package com.game.gameserver.dictionary.entity;

import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:17
 */
@Data
public class SceneData {
    private int id;
    private String name;
    private String desc;
    private List<Integer> exitWay;
    private int sceneConfig;
}
