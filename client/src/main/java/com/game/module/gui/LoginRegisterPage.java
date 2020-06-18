package com.game.module.gui;

import com.game.context.ClientSpringContext;
import com.game.module.account.AccountHandle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 登录注册页面
 * @author xuewenkang
 */
public class LoginRegisterPage extends JPanel {

    private static final int LOGIN_PAGE = 0;
    private static final int REGISTER_PAGE = 1;

    private int type = LOGIN_PAGE;

    public LoginRegisterPage(){
        init();
    }

    public void init(){
        setBackground(Color.black);
        setOpaque(true);
        setLayout(null);

        JLabel gameName = new JLabel("勇者斗恶龙",JLabel.CENTER);
        gameName.setForeground(Color.white);
        gameName.setFont(new Font("宋体", Font.BOLD, 64));
        gameName.setBounds(300,150,400,125);
        JTextField accountField = new JTextField();
        accountField.setBounds(300,300,400,50);
        accountField.setBorder(null);
        accountField.setFont(new Font("宋体", Font.PLAIN, 24));
        JTextField passwordField = new JTextField();
        passwordField.setBounds(300,400,400,50);
        passwordField.setBorder(null);
        passwordField.setFont(new Font("宋体", Font.PLAIN, 24));
        JButton btn = new JButton("登录");
        btn.setBounds(400,475,200,50);
        btn.setBorder(null);
        JLabel label = new JLabel("-->注册",JLabel.CENTER);
        label.setBounds(450,525,100,50);
        label.setForeground(Color.white);

        add(gameName);
        add(accountField);
        add(passwordField);
        add(btn);
        add(label);

        btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loginId = accountField.getText();
                String password = passwordField.getText();
                AccountHandle accountHandle = ClientSpringContext.application.getBean(AccountHandle.class);
                if(type==LOGIN_PAGE){
                    accountHandle.login(loginId,password);
                }else{

                }
            }
        });

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(type==LOGIN_PAGE){
                    type = REGISTER_PAGE;
                    btn.setText("注册");
                    label.setText("-->登录");
                }else{
                    type = LOGIN_PAGE;
                    btn.setText("登录");
                    label.setText("-->注册");
                }
                repaint();
                validate();
            }
        });
    }
}