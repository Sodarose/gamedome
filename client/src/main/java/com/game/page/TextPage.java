package com.game.page;

import javax.swing.*;
import java.awt.*;

/**
 * @author kangkang
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
        label.validate();
        label.repaint();
        refresh();
    }

    public void refresh() {
        validate();
        repaint();
    }
}
