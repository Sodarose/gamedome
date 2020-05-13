package com.game.game.page;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @author xuewenkang
 */
@Component
public class GameFrame  extends JFrame {

    private String title = "game client";
    private Integer height = 600;
    private Integer width = 800;
    private JPanel mainPanel;

    public GameFrame(){
        setTitle(title);
        setSize(width,height);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screenSize.getHeight() / 2 - getHeight() / 2;
        setLocation(x, y);
    }

    public GameFrame(String title,Integer height,Integer width){
        this.title = title;
        this.width = width;
        this.height = height;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setMainPanel(JPanel panel){
        this.mainPanel = panel;
        getContentPane().removeAll();
        getContentPane().add(mainPanel);
        repaint();
    }

    @Override
    public void repaint() {
        super.validate();
        super.repaint();
    }
}
