package com.game.page;

import com.game.service.CmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author xuewenkang
 */
@Component
public class CmdPage extends JPanel {

    @Autowired
    private CmdService cmdService;

    private final int height = 80;
    private final int width = 790;

    private JTextField cmdField;

    public CmdPage(){
        init();
    }

    private void init(){
        setBackground(Color.black);
        setSize(width,height);
        setOpaque(true);
        setLayout(null);
        cmdField = new JTextField();
        cmdField.setBounds(0,15,750,50);
        cmdField.setBackground(Color.BLACK);
        cmdField.setFont(new Font("宋体", Font.PLAIN, 24));
        add(cmdField);
        cmdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                String cmd = cmdField.getText();
                if(code==KeyEvent.VK_ENTER){
                    System.out.println(cmd);
                    clean();
                }
            }
        });
    }

    public void clean(){
        if(cmdField==null){
            return;
        }
        cmdField.setText("");
        cmdField.repaint();
        cmdField.validate();
    }
}
