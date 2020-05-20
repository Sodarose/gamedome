package com.game.game.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author: xuewenkang
 * @date: 2020/5/13 11:47
 */
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

}
