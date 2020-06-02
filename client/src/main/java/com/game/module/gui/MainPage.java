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
    private CmdPage cmdPage ;
    @Autowired
    private TipPage tipPage;
    @Autowired
    private ScenePage scenePage;


    public MainPage(){
        setBackground(Color.BLACK);
        setLayout(new BorderLayout(0,0));

    }

    @PostConstruct
    protected void init(){
        this.add(new JScrollPane(wordPage),BorderLayout.CENTER);
        this.add(cmdPage,BorderLayout.SOUTH);
        this.add(new JScrollPane(tipPage),BorderLayout.WEST);
        this.add(new JScrollPane(scenePage),BorderLayout.NORTH);
    }
}

