package com.game.game.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author xuewenkang
 * @date 2020/5/19 21:52
 */
public class AttachPage extends JPanel {

    private StringBuilder selfText = new StringBuilder();
    private StringBuilder enemyText = new StringBuilder();

    private JTextArea self = new JTextArea();
    private JTextArea enemy = new JTextArea();

    public AttachPage(){
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        init();
        add(self);
        add(enemy);
    }

    private void init(){
        self.setBorder(BorderFactory.createLineBorder(Color.white,3));
        self.setFont(new Font("宋体", Font.PLAIN, 16));
        self.setBackground(Color.BLACK);
        self.setForeground(Color.white);

        enemy.setBorder(BorderFactory.createLineBorder(Color.white,3));
        enemy.setFont(new Font("宋体", Font.PLAIN, 16));
        enemy.setBackground(Color.BLACK);
        enemy.setForeground(Color.white);
    }

    

}
