package com.game.page;

import com.game.entity.BaseRole;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 */
public class AroundPage extends JPanel {

    private final int height = 80;
    private final int width = 790;

    private List<BaseRole>  roles;
    private JLabel label;
    private JPanel rolesPanel;
    public AroundPage(){
        setSize(width,height);
        setBackground(Color.black);
        setOpaque(true);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        roles = new ArrayList<>();
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

    public void setRoles(List<BaseRole> roles) {
        this.roles = roles;
    }

    public void refresh(){

    }
}
