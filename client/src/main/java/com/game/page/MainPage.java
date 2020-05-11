package com.game.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * @author xuwenkang
 */
@Component
public class MainPage extends JPanel {

    @Autowired
    private CmdPage cmdPage;

    @Autowired
    private ScenePage scenePage;

    public MainPage(){
        setBackground(Color.BLACK);
        setOpaque(true);
        setLayout(null);
    }

    @PostConstruct
    public void init(){
        scenePage.setBounds(5,10,790,450);
        cmdPage.setBounds(5,480,790,80);
        add(scenePage);
        add(cmdPage);
    }

}

