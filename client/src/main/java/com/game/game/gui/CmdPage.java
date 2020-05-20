package com.game.game.gui;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 命令框
 * @author: xuewenkang
 * @date: 2020/5/13 11:47
 */

public class CmdPage extends JTextField {
    private final static String DEFAULT_TEXT = "请输入指令";

    public CmdPage(){
        setText(DEFAULT_TEXT);
        setBackground(Color.BLACK);
        setFont(new Font("宋体", Font.PLAIN, 24));
        setForeground(Color.white);
        setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
    }

    public void refresh(){
        repaint();
        validate();
    }
}
