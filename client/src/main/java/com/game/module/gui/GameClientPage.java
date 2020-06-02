package com.game.module.gui;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏GUI页面
 * @author xuewenkang
 * @date 2020/5/19 20:01
 */
@Component
public class GameClientPage extends JFrame{

    private final static String  GAME_TITLE = "game client";
    private final static Integer WIN_HEIGHT = 800;
    private final static Integer WIN_WIDTH = 1000;

    private JPanel currentPanel;

    private LoginRegisterPage loginRegisterPage = new LoginRegisterPage();
    private LoadPage loadPage = new LoadPage();
    private TextPage textPage = new TextPage("");
    @Autowired
    private MainPage mainPage;

    public GameClientPage(){
        setTitle(GAME_TITLE);
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screenSize.getHeight() / 2 - getHeight() / 2;
        setLocation(x, y);
    }

    public void showLoginPage(){
        currentPanel = loginRegisterPage;
        setContentPane(currentPanel);
        refresh();
    }

    public void showLoadPage(){
        currentPanel = loadPage;
        setContentPane(currentPanel);
        refresh();
    }

    public void showTextPage(String text){
        currentPanel = textPage;
        textPage.setText(text);
        setContentPane(currentPanel);
        refresh();
    }

    public void showMainPage(){
        currentPanel = mainPage;
        setContentPane(mainPage);
        refresh();
    }

    public void showMessageDialog(String msg){
        JOptionPane.showMessageDialog(this,msg,"提示信息",JOptionPane.WARNING_MESSAGE);
    }

    public void exit(){
        this.dispose();
    }



    public void refresh(){
        repaint();
        validate();
        if(currentPanel!=null){
            currentPanel.repaint();
            currentPanel.validate();
        }
    }

}
