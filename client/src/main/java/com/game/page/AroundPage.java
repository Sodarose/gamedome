package com.game.page;

import com.game.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author xuewenkang
 */
public class AroundPage extends JPanel {

    private final int height = 80;
    private final int width = 790;

    private Map<Integer,Protocol.Role> roles;
    private Map<Integer,Protocol.Npc> npcs;
    private Map<Integer,Protocol.Monster> monsters;

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

    public void setRoles(Map<Integer,Protocol.Role> roles){
        this.roles = roles;
    }

    public void setNpcs(Map<Integer,Protocol.Npc> npcs){
        this.npcs = npcs;
    }

    public void setMonsters(Map<Integer,Protocol.Monster> monsters){
        this.monsters = monsters;
    }

    public void refresh(){
        for(Map.Entry<Integer,Protocol.Role> entry:roles.entrySet()){
            JLabel jLabel = new JLabel(entry.getValue().getName());
            jLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            jLabel.setForeground(Color.white);
            rolesPanel.add(jLabel);
        }
        for(Map.Entry<Integer,Protocol.Npc> entry:npcs.entrySet()){
            JLabel jLabel = new JLabel(entry.getValue().getName());
            jLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            jLabel.setForeground(Color.white);
            rolesPanel.add(jLabel);
        }

        for(Map.Entry<Integer,Protocol.Monster> entry:monsters.entrySet()){
            JLabel jLabel = new JLabel(entry.getValue().getName());
            jLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            jLabel.setForeground(Color.white);
            rolesPanel.add(jLabel);
        }

        rolesPanel.repaint();
        rolesPanel.validate();
        repaint();
        validate();
    }
}
