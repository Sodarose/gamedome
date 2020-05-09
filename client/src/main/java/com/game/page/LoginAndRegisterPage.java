package com.game.page;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @author kangkang
 */
@Component
public class LoginAndRegisterPage extends JPanel {
    public LoginAndRegisterPage(){
        init();
    }
    
    public void init(){
        setBackground(Color.black);
        setOpaque(true);
        setLayout(null);

        JLabel gameName = new JLabel("勇者斗恶龙",JLabel.CENTER);
        gameName.setForeground(Color.white);
        gameName.setFont(new Font("宋体", Font.BOLD, 64));
        gameName.setBounds(200,50,400,125);
        JTextField accountField = new JTextField();
        accountField.setBounds(200,200,400,50);
        accountField.setBorder(null);
        accountField.setFont(new Font("宋体", Font.PLAIN, 24));
        JTextField passwordField = new JTextField();
        passwordField.setBounds(200,300,400,50);
        passwordField.setBorder(null);
        passwordField.setFont(new Font("宋体", Font.PLAIN, 24));
        JButton btn = new JButton("登录");
        btn.setBounds(300,375,200,50);
        btn.setBorder(null);
        JLabel label = new JLabel("注册",JLabel.CENTER);
        label.setBounds(350,425,100,50);
        label.setForeground(Color.white);

        add(gameName);
        add(accountField);
        add(passwordField);
        add(btn);
        add(label);
    }
}
