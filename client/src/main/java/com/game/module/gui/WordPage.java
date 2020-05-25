package com.game.module.gui;

import com.game.module.game.model.Role;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * @author: xuewenkang
 * @date: 2020/5/13 11:47
 */
@Component
public class WordPage extends JTextArea {

    /**
     * GUI 展示内容
     */
    private StringBuilder builder = new StringBuilder(16);

    public WordPage(){
        setBorder(BorderFactory.createLineBorder(Color.white,3));
        setFont(new Font("宋体", Font.PLAIN, 16));
        setBackground(Color.BLACK);
        setForeground(Color.white);
        setText(builder.toString());
    }

    public void refresh(){
        setText(builder.toString());
        validate();
        repaint();
    }

    public void print(String text){
        builder.append(text);
        builder.append("\n");
        refresh();
    }

    public void clean(){
        builder = new StringBuilder();
        refresh();
    }

    /**
     * 展示角色选择
     * @param roleList
     * @return void
     */
    public void print(List<Role> roleList){
        builder.append("\n");
        for(Role role:roleList){
            builder.append("角色名：").append(role.getName()).append("\t");
        }
        builder.append("\n");
        for(Role role:roleList){
            builder.append("职业：").append(role.getCareer()).append("\t");
        }
        builder.append("\n");
        for(Role role:roleList){
            builder.append("等级：").append(role.getLevel()).append("\t");
        }
        builder.append("\n");
        refresh();
    }
}
