package com.snakegame;

import java.util.ArrayList;

// import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
// import com.snake.Game.Body.Position;

public class Arena {

    private final int windowHeight = 600, windowWidth = 750;

    Snake snake;
    Fruit fruit;
    
    char[][] grid;  // '@' -> empty, '#' -> cell, '*' -> apple
    int gridHeight, gridWidth;
    int cellSize;
    int score;

    public Arena() {
        init();
    }

    public void init() {

        snake = new Snake();
        fruit = new Fruit();

        cellSize = 15;
        gridHeight = windowHeight / cellSize;
        gridWidth = windowWidth / cellSize;
        grid = new char[gridHeight + 1][gridWidth + 1];
        update();

    }

    public void draw(ShapeRenderer sr) {

        snake.draw(sr);
        fruit.draw(sr);
    }

    public void update() {
        score = (snake.cntCells-1)*100;
        if(snake.state.equals("Static"))
           return;
        
        snake.move();

        int row = gridHeight - (snake.cells[0].y / cellSize);
        int col = snake.cells[0].x / cellSize;

        if (isColliding(row, col)) {
            snake.vx = 0;
            snake.vy = 0;
            snake.state = "Static";
            score = 0;
            snake.shrink();
            fruit.generate();
        }
        else if (hasFruit(row, col)) {
            snake.append();
            fruit.generate();
        } 
    
        resetBoard();
        placeFruit();
        placeSnake();
        

    }

    void placeFruit() {
        grid[gridHeight - fruit.position.y / cellSize][fruit.position.x / cellSize] = '*';
    }

    void placeSnake() {
        for (int i = 0; i < snake.cntCells; i++) {
            int row = gridHeight - (snake.cells[i].y / cellSize);
            int col = snake.cells[i].x / cellSize;
            grid[row][col] = '#';
        }

    }

    boolean isColliding(int r, int c) {
        return grid[r][c] == '#';
    }

    boolean hasFruit(int r, int c) {
        return grid[r][c] == '*';
    }

    void resetBoard() {
        for (int i = 0; i <= gridHeight; i++)
            for (int j = 0; j <= gridWidth; j++)
                grid[i][j] = '@'; 

    }

    protected class Fruit {
        Position position;
        ArrayList available;

        Fruit() {
            position = new Position(windowHeight / 2 - cellSize, windowWidth / 2 - cellSize);
            available = new ArrayList<Position>();
        }

        void draw(ShapeRenderer sr) {
            sr.setColor(Color.WHITE);
            sr.rect(position.x, position.y, cellSize, cellSize);
        }

        void generate() {

            available.clear();
            for (int i = 0; i < gridHeight; i++) {
                for (int j = 0; j < gridWidth; j++) {
                    if (grid[i][j] == '@') {
                        available.add(new Position(((gridHeight - i) * cellSize) % windowWidth,
                                (j * cellSize) % windowHeight));
                    }
                }
            }
            int random = (int) (Math.random() * available.size());
            position = (Position) available.get(random);
        }
    }

    protected class Position {
        public int x, y;

        public Position() {
        }

        public Position(int x, int y) {
            set(x, y);
        }

        public void set(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    protected class Snake {

        int cntCells;
        int vx, vy;
        String state;
        Position[] cells;
     

        public Snake() {

            vx = 0;
            vy = 0;
            cntCells = 0;
            state = "Static";
            cells = new Position[windowWidth * windowHeight];
          
            cells[0] = new Position();
            cells[0].set(0, 0);
            cntCells++;


        }

        void append() {
            cells[cntCells] = new Position(cells[cntCells - 1].x, cells[cntCells - 1].y);
            cntCells++;
        }

        void shrink() {
            cntCells = 1;
        }

        public void move() {

            Position head = cells[0];
      

            for (int i = cntCells - 1; i > 0; i--) {

                cells[i].set(cells[i - 1].x, cells[i - 1].y);
              
            }
            head.x = ((head.x + vx + windowWidth) % windowWidth);
            head.y = ((head.y + vy + windowHeight) % windowHeight);

            cells[0].set(head.x, head.y); // experemintal

        

        }

        public void draw(ShapeRenderer shapeRenderer) {

            // float alphac = 1 / (float) cntCells;
            // float alphav = 1.0f + alphac;

            // float freq = 0.3f;
            float r,g,b;
            for (int i = 0; i < cntCells; i++) {
                r = (float) Math.sin(Math.PI /cntCells*2*i + 0*Math.PI *2/3);
                g = (float) Math.sin(Math.PI /cntCells*2*i + 1*Math.PI *2/3);
                b = (float) Math.sin(Math.PI /cntCells*2*i + 2*Math.PI *2/3);
                

                // alphav -= alphac;
                // System.out.println(alphav);
                shapeRenderer.setColor(r, g, b, 1);
                shapeRenderer.rect(cells[i].x, cells[i].y, cellSize, cellSize);

            }

        }

    }

}