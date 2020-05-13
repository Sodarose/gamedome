package com.game.game.page;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 */
public class LoadPage  extends JPanel {

    private volatile boolean stop = false;
    private final int MAX = 12;
    private JLabel jLabel;
    private String text = "loading";
    private ScheduledExecutorService task;
    public LoadPage(){
        setBackground(Color.BLACK);
        jLabel = new JLabel(text);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 32));
        jLabel.setForeground(Color.white);
        add(jLabel);
        setSize(400,200);
        task = new ScheduledThreadPoolExecutor(1);
        task.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(text.length()>MAX){
                    text = "loading";
                }else {
                    text+=".";
                }
                jLabel.setText(text);
                jLabel.validate();
                jLabel.repaint();
            }
        },0,1, TimeUnit.SECONDS);
    }

    public void setStop(){
        stop = true;
        task.shutdown();
    }

    public boolean isStop(){
        return stop;
    }
}