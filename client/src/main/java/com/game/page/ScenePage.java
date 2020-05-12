package com.game.page;

import com.game.context.GameContext;
import com.game.entity.BaseRole;
import com.game.entity.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 */
@Component
public class ScenePage extends JPanel {
    private final int height = 450;
    private final int width = 790;

    @Autowired
    private GameContext gameContext;

    private Scene scene;

    private SceneDesPage sceneDes;
    private AroundPage around;
    private WayPage way;

    public ScenePage(){
        init();
    }

    private void init(){
        setBackground(Color.black);
        setSize(width,height);
        setVisible(true);
        setOpaque(true);
        setLayout(null);
        sceneDes = new SceneDesPage();
        sceneDes.setBounds(0,0,750,250);
        add(sceneDes);

        around = new AroundPage();
        around.setBounds(0,270,750,80);
        add(around);

        way = new WayPage();
        way.setBounds(0,360,750,80);
        add(way);

    }

    public void refresh(){
        this.scene = gameContext.getScene();
        this.sceneDes.setGameMap(scene.getMap());
        this.sceneDes.refresh();
        List<BaseRole> roles = new ArrayList<>();
        roles.addAll(scene.getUser().values());
        roles.addAll(scene.getNpc().values());
        roles.addAll(scene.getMonsters().values());
        this.around.setRoles(roles);
        this.way.setWays(scene.getMap().getWays());
    }

}
