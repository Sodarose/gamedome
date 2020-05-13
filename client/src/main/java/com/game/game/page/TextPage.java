package com.game.game.page;

import javax.swing.*;
import java.awt.*;

/**
 * @author xuewenkang
 */
public class TextPage extends JPanel {
    private JLabel label;
    public TextPage(String text){
        setBackground(Color.BLACK);
        label = new JLabel(text,JLabel.CENTER);
        label.setFont(new Font("宋体", Font.PLAIN, 24));
        label.setForeground(Color.white);
        add(label, BorderLayout.CENTER);
    }

    public void setText(String text){
        label.setText(text);
        refresh();
    }

    public void refresh() {
        validate();
        repaint();
    }
}
