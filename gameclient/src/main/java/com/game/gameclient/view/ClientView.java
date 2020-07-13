package com.game.gameclient.view;

import com.game.gameclient.client.CmdHandle;
import org.omg.CORBA.ORB;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author xuewenkang
 * @date 2020/7/10 17:30
 */
public class ClientView extends JFrame {

    public static JTextArea SCENE_PAGE = new JTextArea();
    public static JTextArea PLAYER_PAGE = new JTextArea();
    public static JTextArea EQUIP_PAGE = new JTextArea();
    public static JTextArea BACK_BAG_PAGE = new JTextArea();
    public static JTextArea MSG_PAGE = new JTextArea();
    public static JTextArea CMD_PAGE = new JTextArea();

    public ClientView(){
        this.setLayout(null);
        SCENE_PAGE.setFont(new Font("宋体", Font.PLAIN, 16));
        SCENE_PAGE.setBackground(Color.BLACK);
        SCENE_PAGE.setForeground(Color.white);
        SCENE_PAGE.setLineWrap(true);
        SCENE_PAGE.setWrapStyleWord(true);
        SCENE_PAGE.setText("场景信息:");
        JScrollPane scenePane = new JScrollPane(SCENE_PAGE);
        scenePane.setBounds(0,0,800,200);
        this.add(scenePane);

        PLAYER_PAGE.setFont(new Font("宋体", Font.PLAIN, 16));
        PLAYER_PAGE.setBackground(Color.BLACK);
        PLAYER_PAGE.setForeground(Color.white);
        PLAYER_PAGE.setLineWrap(true);
        PLAYER_PAGE.setWrapStyleWord(true);
        PLAYER_PAGE.setText("角色信息:");
        JScrollPane playerPane = new JScrollPane(PLAYER_PAGE);
        playerPane.setBounds(800,0,400,300);
        this.add(playerPane);


        EQUIP_PAGE.setFont(new Font("宋体", Font.PLAIN, 16));
        EQUIP_PAGE.setBackground(Color.BLACK);
        EQUIP_PAGE.setForeground(Color.white);
        EQUIP_PAGE.setLineWrap(true);
        EQUIP_PAGE.setWrapStyleWord(true);
        EQUIP_PAGE.setText("装备信息:");
        JScrollPane equipPane = new JScrollPane(EQUIP_PAGE);
        equipPane.setBounds(800,300,400,200);
        this.add(equipPane);

        BACK_BAG_PAGE.setFont(new Font("宋体", Font.PLAIN, 16));
        BACK_BAG_PAGE.setBackground(Color.BLACK);
        BACK_BAG_PAGE.setForeground(Color.white);
        BACK_BAG_PAGE.setLineWrap(true);
        BACK_BAG_PAGE.setWrapStyleWord(true);
        BACK_BAG_PAGE.setText("背包信息:");
        JScrollPane backBagPane = new JScrollPane(BACK_BAG_PAGE);
        backBagPane.setBounds(800,500,400,300);
        this.add(backBagPane);

        MSG_PAGE.setFont(new Font("宋体", Font.PLAIN, 16));
        MSG_PAGE.setBackground(Color.BLACK);
        MSG_PAGE.setForeground(Color.white);
        MSG_PAGE.setLineWrap(true);
        MSG_PAGE.setWrapStyleWord(true);
        JScrollPane msgPane = new JScrollPane(MSG_PAGE);
        msgPane.setBounds(0,200,800,520);
        this.add(msgPane);

        CMD_PAGE.setFont(new Font("宋体", Font.PLAIN, 16));
        CMD_PAGE.setBackground(Color.BLACK);
        CMD_PAGE.setForeground(Color.white);
        CMD_PAGE.setLineWrap(true);
        CMD_PAGE.setWrapStyleWord(true);
        CMD_PAGE.setText("请输入命令");
        JScrollPane cmdPane = new JScrollPane(CMD_PAGE);
        cmdPane.setBounds(0,720,800,80);
        this.add(cmdPane);

        // 命令行添加监听器
        CMD_PAGE.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    String order = CMD_PAGE.getText();
                    if(order==null||order.isEmpty()){
                        return;
                    }
                    MSG_PAGE.append(order);
                    MSG_PAGE.validate();
                    MSG_PAGE.repaint();
                    CMD_PAGE.setText("");
                    CMD_PAGE.repaint();
                    CMD_PAGE.validate();
                    CmdHandle.submit(order.trim());
                }
            }
        });

        this.setSize(1220, 850);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screenSize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screenSize.getHeight() / 2 - getHeight() / 2;
        this.setLocation(x, y);
    }

    public static void print2ScenePane(String content){
        SCENE_PAGE.setText(content);
        SCENE_PAGE.validate();
        SCENE_PAGE.repaint();
    }

    public static void print2PlayerPane(String content){
        PLAYER_PAGE.setText(content);
        PLAYER_PAGE.validate();
        PLAYER_PAGE.repaint();
    }

    public static void print2EquipPane(String content){
        EQUIP_PAGE.setText(content);
        EQUIP_PAGE.validate();
        EQUIP_PAGE.repaint();
    }

    public static void print2BackBagPane(String content){
        BACK_BAG_PAGE.setText(content);
        BACK_BAG_PAGE.validate();
        BACK_BAG_PAGE.repaint();
    }

    public static void print2Msg(String content){
        MSG_PAGE.append("\n"+content+"\n");
        MSG_PAGE.validate();
        MSG_PAGE.repaint();
    }

    public static void cleanMsgPane(){
        MSG_PAGE.setText("");
        MSG_PAGE.validate();
        MSG_PAGE.repaint();
    }
}
