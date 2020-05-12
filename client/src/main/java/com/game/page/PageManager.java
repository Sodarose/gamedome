package com.game.page;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * @author xuewenkang
 * 页面管理器 负责页面的刷新、切换、展示
 */
@Component
@Data
public class PageManager {

    @Autowired
    private GameFrame gameFrame;

    @Autowired
    private MainPage mainPage;

    @Autowired
    private LoginAndRegisterPage loginAndRegisterPage;

    public void showMainPage(){
        mainPage.init();
        gameFrame.setMainPanel(mainPage);
    }

    public void showLoginAndRegisterPage(){
        gameFrame.setMainPanel(loginAndRegisterPage);
    }

    public void showPage(JPanel jPanel){
        gameFrame.setMainPanel(jPanel);
    }

    public void showMessageDialog(String msg){
        JOptionPane.showMessageDialog(gameFrame,msg,"提示信息",JOptionPane.WARNING_MESSAGE);
    }

    /**
     * description: 刷新页面
     *
     * @param
     * @return void
     */
    public void refresh(){
        gameFrame.getContentPane().repaint();
        gameFrame.getContentPane().validate();

        mainPage.refresh();

        loginAndRegisterPage.repaint();
        loginAndRegisterPage.validate();
    }


}
