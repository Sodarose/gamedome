package com.game.game.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author: xuewenkang
 * @date: 2020/5/13 11:47
 */
@Component
public class CmdPage extends JTextField {

    @Autowired
    private WordPage wordPage;


    public CmdPage(){
        setText("请输入指令");
        setBackground(Color.BLACK);
        setFont(new Font("宋体", Font.PLAIN, 24));
        setForeground(Color.white);
        setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
    }

    @PostConstruct
    public void init(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    String cmd = getText();
                    if(cmd==null||cmd.isEmpty()){
                        return;
                    }

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
