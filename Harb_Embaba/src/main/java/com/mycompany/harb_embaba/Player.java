/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.harb_embaba;

/**
 *
 * @author Mahmoud Elshafei
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Player {
    int x, y;
    private int speed = 5;
    private boolean left = false, right = false;
    private BufferedImage image;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        
        
        try {
            image = ImageIO.read(new File("resources/player.png"));
        } catch (IOException e) {
            System.out.println("Player image not Found");
        }
    }
    
    public void update() {
        if (left && x > 0) x -= speed;
        if (right && x < 750) x += speed;
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }
    
    
    public void setLeft(boolean b) {left = b;}
    public void setRight(boolean b) {right = b;}
}
