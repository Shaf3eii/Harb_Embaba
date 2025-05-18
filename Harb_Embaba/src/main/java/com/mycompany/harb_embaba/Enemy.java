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


public class Enemy {
    int x, y;
    private double yPos;
    private double speed = 0.1;
    private BufferedImage image;
    
    
    public Enemy (int x, int y) {
        this.x = x;
        this.y = y;
        
        try {
             image = ImageIO.read(new File("resources/enemy.png"));
        } catch (IOException e) {
             System.out.println("Enemy image not Found");
        }
    }
    
    private int framecount = 0;
    public void update() {
        framecount++;
        if (framecount % 10 == 0) {
            yPos += speed;
            y += (int)yPos; 
        }
    }
    
    public void setSpeed(double s) {
        this.speed = s;
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }
}
