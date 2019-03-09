package app;

import java.applet.Applet;
import java.awt.Color;

import javax.swing.JFrame;

class GameFrame extends Applet{

    JFrame mainFrame;
    GamePanel gamePanel;

    public GameFrame() {
        mainFrame = new JFrame("Snake");
        gamePanel = new GamePanel();
        mainFrame.setBounds(10,10,GamePanel.WIDTH, GamePanel.HEIGHT);
        mainFrame.setBackground(Color.DARK_GRAY);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(gamePanel);
    }
    

    
}