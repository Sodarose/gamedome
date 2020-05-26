package com.game.module.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author xuwenkang
 * @date: 2020/5/13 11:47
 */
@Component
public class MainPage extends JPanel {
    @Autowired
    private WordPage wordPage;
    @Autowired
    private CmdPage cmdPage  ;

    public MainPage(){
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    }

    @PostConstruct
    protected void init(){
        add(wordPage);
        add(cmdPage);
    }
}

