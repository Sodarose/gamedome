package com.game.page;

import com.game.pojo.GameMap;
import com.game.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 */
public class WayPage extends JPanel {

    private final int height = 80;
    private final int width = 790;

    private List<Protocol.Map> ways;
    private JLabel label;
    private JPanel waysPanel;

    public WayPage(){
        setSize(width,height);
        setBackground(Color.black);
        setOpaque(true);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        ways = new ArrayList<>();
        label = new JLabel("地图:");
        label.setFont(new Font("宋体", Font.PLAIN, 24));
        label.setForeground(Color.white);
        label.setBounds(0,15,60,50);
        add(label);

        waysPanel =  new JPanel();
        waysPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        waysPanel.setBounds(70,15,650,50);
        waysPanel.setBackground(Color.BLACK);
        add(waysPanel);
    }

    public void setWays(List<Protocol.Map> ways){
        this.ways = ways;
    }

    public void refresh(){
        for(Protocol.Map way:ways){
            JLabel jLabel = new JLabel(way.getName());
            jLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            jLabel.setForeground(Color.white);
            waysPanel.add(jLabel);
        }
        waysPanel.repaint();
        waysPanel.validate();
        repaint();
        validate();
    }
}
