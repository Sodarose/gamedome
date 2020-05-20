package com.game.game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

/**
 * @author xuwenkang
 * @date: 2020/5/13 11:47
 */
public class MainPage extends JPanel {

    private WordPage wordPage = new WordPage();
    private CmdPage cmdPage = new CmdPage();

    private TimerTask timer;

    public MainPage(){
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(wordPage);
        add(cmdPage);
        init();
    }

    private void init(){
        cmdPage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    String cmd = cmdPage.getText();
                    if(cmd==null||cmd.isEmpty()){
                        return;
                    }
                    wordPage.print(cmd);
                    cmdPage.setText("");
                    cmdPage.refresh();
                }
            }
        });
    }
}

