package com.game.page;

import javax.swing.*;
import java.awt.*;

/**
 * @author xuewenkang
 */
public class AroundPage extends JPanel {

    private final int height = 80;
    private final int width = 790;


    private JLabel label;
    private JPanel rolesPanel;
    public AroundPage(){
        setSize(width,height);
        setBackground(Color.black);
        setOpaque(true);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        label = new JLabel("周围:");
        label.setFont(new Font("宋体", Font.PLAIN, 24));
        label.setForeground(Color.white);
        label.setBounds(0,15,60,50);
        add(label);

        rolesPanel = new JPanel();
        rolesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        rolesPanel.setBounds(60,15,680,50);
        rolesPanel.setBackground(Color.BLACK);
        add(rolesPanel);
    }


    public void refresh(){

    }
}
