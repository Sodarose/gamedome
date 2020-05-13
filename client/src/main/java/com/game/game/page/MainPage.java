package com.game.game.page;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * @author xuwenkang
 */
@Component
@Data
public class MainPage extends JPanel {

    @Autowired
    private WordPage word;

    @Autowired
    private CmdPage cmd;

    public MainPage(){
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    }

    @PostConstruct
    public void init(){
        JScrollPane scroll = new JScrollPane(word);
        add(scroll);
        add(cmd);
        refresh();
    }

    public void refresh(){
        validate();
        repaint();
    }

}

