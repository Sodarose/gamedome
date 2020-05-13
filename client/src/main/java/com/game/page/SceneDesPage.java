package com.game.page;

import com.game.pojo.GameMap;
import com.game.protocol.Protocol;

import javax.swing.*;
import java.awt.*;

/**
 * @author xuewenkang
 */
public class SceneDesPage extends JPanel {
    private Protocol.Map gameMap;
    private final int height = 300;
    private final int with = 790;

    private String name;
    private String description;
    private JLabel namePanel;
    private JTextArea desPanel;

    public SceneDesPage(){
        init();
    }

    private void init(){
        setSize(with,height);
        setBackground(Color.black);
        setOpaque(true);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        setBackground(Color.BLACK);
        name = "";
        description = "";
        namePanel = new JLabel("------"+"初始之地"+"------",JLabel.CENTER);
        namePanel.setFont(new Font("宋体", Font.PLAIN, 24));
        namePanel.setForeground(Color.white);
        namePanel.setBounds(0,0,750,50);
        add(namePanel);

        desPanel = new JTextArea("这几天有些空闲，在做一个缩略词的词典。\n" +
                "\n" +
                "　　要用到swing，也是许久没有写过swing构件的代码了，对于swing这把刀已经感觉很生疏了。\n" +
                "\n" +
                "　　要用到一个文本显示区，为了便于复制，我用了JTextArea。" +
                "JTextArea本身默认的是不换行，不滚动条显示。" +
                "你设置完他的大小之后，他就只是显示在这个窗口大小内的内容");
        desPanel.setBackground(Color.BLACK);
        desPanel.setFont(new Font("宋体", Font.PLAIN, 16));
        desPanel.setForeground(Color.white);
        desPanel.setBounds(10,60,720,220);
        desPanel.setLineWrap(true);
        desPanel.setWrapStyleWord(true);
        desPanel.setEditable(false);
        add(desPanel);
    }

    public void setGameMap(Protocol.Map gameMap){
        this.gameMap = gameMap;
        this.name = gameMap.getName();
        this.description = gameMap.getDescription();
    }

    public void refresh(){
        namePanel.setText("------"+name+"------");
        desPanel.setText(description);
        namePanel.repaint();
        namePanel.validate();
        desPanel.repaint();
        desPanel.validate();
    }

}
