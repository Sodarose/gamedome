package com.game.page;

import com.game.entity.Scene;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @author xuewenkang
 */
@Component
public class ScenePage extends JPanel {
    private final int height = 450;
    private final int width = 790;

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

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void refresh(){

    }

}
