package com.game.module.gui;

import com.game.module.order.service.CmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 命令框
 * @author: xuewenkang
 * @date: 2020/5/13 11:47
 */
@Component
public class CmdPage extends JTextField {
    private final static String DEFAULT_TEXT = "请输入指令";

    @Autowired
    private WordPage wordPage;
    @Autowired
    private CmdService cmdService;

    public CmdPage(){
        setText(DEFAULT_TEXT);
        setBackground(Color.BLACK);
        setFont(new Font("宋体", Font.PLAIN, 24));
        setForeground(Color.white);
        setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    String order = getText();
                    if(order==null||order.isEmpty()){
                        return;
                    }
                    wordPage.print(order);
                    cmdService.submitCmd(order);
                    setText("");
                    refresh();
                }
            }
        });
    }



    public void refresh(){
        repaint();
        validate();
    }
}