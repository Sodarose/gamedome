package com.game.module.gui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @author xuewenkang
 * @date 2020/6/1 9:17
 */
@Component
public class TipPage extends JTextArea {
    /**
     * GUI 展示内容
     */
    private StringBuilder builder = new StringBuilder(16);

    public TipPage(){
        setBorder(BorderFactory.createLineBorder(Color.white,3));
        setFont(new Font("宋体", Font.PLAIN, 16));
        setBackground(Color.BLACK);
        setForeground(Color.white);
        setLineWrap(true);
        setWrapStyleWord(true);
        setText(builder.toString());
    }

    public void print(String tip){
        builder.append(tip).append("\n");
        refresh();
    }

    public void refresh(){
        setText(builder.toString());
        repaint();
        validate();
    }

    public void clean(){
        builder = new StringBuilder();
        refresh();
    }

}
