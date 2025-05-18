/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.harb_embaba;

/**
 *
 * @author Mahmoud Elshafei
 */

import javax.swing.*; // GUI components
import java.awt.*;    // Graphics and layout
import java.awt.event.*; // Keyboard events
import java.awt.image.*; // BufferedImage for drawing images
import java.util.*;      // ArrayList
import javax.imageio.ImageIO; // Load images
import java.io.*;        // File reading
import javax.sound.sampled.*; // Play Sounds

public class GamePanel extends JPanel implements Runnable, KeyListener{
    
    private enum GameState {
        START_SCREEN, MENU, PLAYING, GAMEOVER, WIN
    }
    
    private GameState state = GameState.MENU;
    
    private Thread thread;
    private boolean running = false; 
    private boolean paused = false;
    private boolean startSound = false;
    private boolean gameOverSound = false;
    private Clip clip;
    
    
    private BufferedImage bgImage;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int score = 0;
    private long lastSoundTime = System.currentTimeMillis();
    private final int soundInterval = 10000;
    private int level = 1;

    
    private int screenWidth;
    private int screenHeight;

    public GamePanel(Dimension screenSize) {
        this.screenWidth = screenSize.width;
        this.screenHeight = screenSize.height;
        setPreferredSize(screenSize);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        loadAssets();
        startGame();
    }
    
    private void loadAssets() {
        try {
            bgImage = ImageIO.read(new File("resources/background.png"));
        } catch (IOException e) {
            System.out.println("Background Not Found 404.");
        }
    }
    
    private void startGame() {
        player = new Player(screenWidth / 2 - 25, screenHeight - 100);
        
        int startX = screenWidth / 5;
        int spacing = 100;

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 3; ++j) {
                Enemy enemy = new Enemy(startX + i * spacing, 100 + j * 60);
                enemy.setSpeed(0.1 + level * 0.5);
                enemies.add(enemy);
            }
        }
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    private void resetGame() {
        bullets.clear();
        enemies.clear();
        startGame();
    }
    
    @Override
    public void run() {
        if (!startSound) {
            playSound("resources/v_prepare.wav");
            startSound = true;
        }
        while (running) {
            if (!paused) update();
            repaint();
            
            try {
                Thread.sleep(30);
            } catch(InterruptedException e){ }
        } 
    }
    
    private void update() {
        if (state != GameState.PLAYING) return;

        player.update();
        
        for (Bullet b : new ArrayList<>(bullets)) {
            b.update();
            if (b.y < 0) bullets.remove(b);
        }
        
        for (Enemy e : enemies) {
            e.update();
        }
        
        for (Bullet b : new ArrayList<>(bullets)) {
            for (Enemy e : new ArrayList<>(enemies)) {
                if (b.getBounds().intersects(e.getBounds())) {
                    bullets.remove(b);
                    enemies.remove(e);
                    score += 10;
                    break;
                }
            }
        }
        
        for (Enemy e : enemies) {
            if (e.y >= player.y) {
                gameOverSound = true;
                playSound("resources/v_gameover.wav");
                running = false;
                int option = JOptionPane.showConfirmDialog(this, "Game Over! Play Again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    gameOverSound = false;
                    resetGame();
                } else {
                    System.exit(0);
                }
            }
        }
        
        if (enemies.isEmpty()) {
            running = false;
            int option = JOptionPane.showConfirmDialog(this, "Winner Winner Chicken Dinner! Play Again?", "Winner Winner Chicken Dinner", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    level++;
                    resetGame();
                } else {
                    System.exit(0);
                }
        }
        
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSoundTime >= soundInterval && !gameOverSound) {
            playSound("resources/v_megalaser.wav");
            lastSoundTime = currentTime;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (state == GameState.MENU) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("أضغط Enter لبدأ المعركة", 300, 150);
            return;
        }
        
        g.drawImage(bgImage, 0, 0, null);
        player.draw(g);
        
        for (Enemy e : enemies) {e.draw(g);}
        
        for (Bullet b : bullets) {b.draw(g);}
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);
    }  
    
    
    public void playSound(String soundPath) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(soundPath));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
    
    @Override
    public void keyPressed(KeyEvent k) {
        
        if (state == GameState.MENU && k.getKeyCode() == KeyEvent.VK_ENTER) {
            state = GameState.PLAYING;
            startGame();
            playSound("resources/v_getready.wav");
        }
        
        if (k.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        
        if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        
        if (k.getKeyCode() == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(player.x + 20, player.y));
//            playSound("resources/shoot.wav");
        }
        
        if (k.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
        }
        
    } 
    
    @Override
    public void keyReleased(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        
        if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent k) { }
}