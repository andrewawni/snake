package app;

import java.applet.Applet;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;


public class App{

 
    public static void main(String[] args) throws Exception {

        // DigitalClock dc = new DigitalClock();
            EventQueue.invokeLater(() -> {
              GameFrame ex = new GameFrame();
                ex.setVisible(true);
            });
    
    
           
        
        }
  
}