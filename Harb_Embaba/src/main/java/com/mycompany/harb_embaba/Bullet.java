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


public class Bullet {
    int x, y;
    private int speed = 8;
    
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    } 
    
    public void update() {
        y -= speed;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 5, 10);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);
    }
}
