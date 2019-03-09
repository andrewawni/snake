package app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
// import java.util.Timer;
import javax.swing.Timer;
// import java.util.EventListener;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.JPanel;

class Vector2D {
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x, y;
}

public class GamePanel extends JPanel implements KeyListener, Runnable {

    public final static int WIDTH = 800, HEIGHT = 600;
    public static int score;
    public char[][] grid;
    public ArrayList snake;
    public int vx, vy;
    public int cntCells;

    private Thread t;
    private final int DELAY = 100;

    public void gameLoop() {
        updateGrid();
        Vector2D head = (Vector2D) snake.get(0);
        head.x = (head.x + vx) % WIDTH;
        head.y = (head.y + vy) % HEIGHT;
        // System.out.println(head.x + " " + head.y);
        for (int i = 1; i < snake.size(); i++)
            snake.set(i - 1, (Vector2D) snake.get(i));
        snake.set(0, head);

    }

    @Override
    public void run() {

        final int FRAMES_PER_SECOND = 30;
        final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
        final int MAX_FRAMESKIP = 5;
        long next_game_tick = System.currentTimeMillis();
        long sleep_time = 0;

        while (true) {
            gameLoop();
            repaint();

            next_game_tick += SKIP_TICKS;
            sleep_time = next_game_tick - System.currentTimeMillis();
            if (sleep_time >= 0) {

                try {
                    Thread.sleep(sleep_time);
                    System.out.println(sleep_time);
                } catch (Exception e) {

                }
            }

        }

        /*
         * long beforeTime, timeDiff, sleep;
         * 
         * beforeTime = System.currentTimeMillis();
         * 
         * while (true) {
         * 
         * gameLoop(); repaint();
         * 
         * timeDiff = System.currentTimeMillis() - beforeTime;
         * System.out.println(timeDiff); sleep = DELAY - timeDiff;
         * 
         * // System.out.println(sleep); if (sleep < 0) sleep *= -1;
         * 
         * try { Thread.sleep(33); } catch (InterruptedException e) {
         * System.out.println("thread interrupted"); }
         * 
         * beforeTime = System.currentTimeMillis(); }
         */

    }

    public GamePanel() {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        grid = new char[HEIGHT][WIDTH];
        updateGrid();

        score = 0;
        cntCells = 1;
        vx = vy = 0;
        snake = new ArrayList<Vector2D>();
        snake.add(new Vector2D(400, 400));

        grid[400][400] = '#';

    }
    public int l = 10;

    public void paint(Graphics g) {

        // System.out.println("paint " + System.currentTimeMillis());

        g.setColor(Color.BLACK);
        g.fillRect(0, 50, WIDTH, HEIGHT - 50);
        String title = "Score: " + Integer.toString(score);
        g.setFont(new Font("Roboto", 15, 32));
        g.drawString(title, 10, 32);
        
        // l++;
        // draw snake
        
        for (int i = 0; i < snake.size(); i++) {
            Vector2D vec = (Vector2D) snake.get(i);
            g.fillRect(vec.x, vec.y, 20, 20);
            g.setColor(Color.WHITE);
            g.setColor(Color.GREEN);
            g.drawRect(vec.x, vec.y, 20, 20); 
            
        }
        g.dispose();

    }

    public void updateGrid() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                grid[i][j] = '0'; // 0 = empty, # = snake cell, * = Fruit
    }

    /*
     * @Override public void actionPerformed(ActionEvent e) { //
     * System.out.println(e.toString() + System.currentTimeMillis());
     * 
     * // t.start(); updateGrid(); Vector2D head = (Vector2D) snake.get(0); head.x =
     * (head.x + vx)%WIDTH; head.y = (head.y + vy)%HEIGHT; System.out.println(head.x
     * + " " + head.y); for (int i = 1; i < snake.size(); i++) snake.set(i - 1,
     * (Vector2D) snake.get(i)); snake.set(0, head);
     * 
     * try { Thread.sleep(10); } catch (InterruptedException k) {
     * System.out.println(e.toString() + " interrupted"); }
     * 
     * repaint(); }
     * 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("right key pressed");
            if (vx == 0)
                vx = 5;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void addNotify() {
        
        super.addNotify();
        t = new Thread(this);
        t.start();
    }

}
