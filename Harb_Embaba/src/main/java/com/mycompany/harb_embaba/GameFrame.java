/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.harb_embaba;

/**
 *
 * @author Mahmoud Elshafei
 */

import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import java.awt.Dimension;


public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("حرب إمبابة");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); 

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        add(new GamePanel(screenSize));
        setVisible(true);
    }
}

