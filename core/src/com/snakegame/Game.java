package com.snakegame;

import java.awt.Font;
import java.awt.RenderingHints.Key;
import java.sql.Time;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public class Game extends ApplicationAdapter implements InputProcessor {

	Arena game;
	long lastLoopTime = System.nanoTime();
	int TARGET_FPS = 15;
	long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	// ShapeRenderer shapeRenderer;
	ShapeRenderer shapeRenderer;
	SpriteBatch	spriteBatch;
	CharSequence title;
	BitmapFont font;
	long startTime;
	boolean speedUp;

	@Override
	public void create() {
		/*
		 * spriteFrame = 1; batch = new SpriteBatch();
		 * 
		 * textureAtlas = new
		 * TextureAtlas(Gdx.files.internal("spritesheets/jump.atlas")); textureRegion =
		 * textureAtlas.findRegion(Integer.toString(spriteFrame));
		 * 
		 * sprite = new Sprite(textureRegion);
		 * sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
		 * Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2); sprite.scale(5.0f);
		 */
		speedUp = false;
		startTime = System.currentTimeMillis();
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		
		font = new BitmapFont();
		font.getData().setScale(1f, 1f);
		
		Gdx.input.setInputProcessor(this);
		game = new Arena(); // any even number, denotes the cell size thus the difficulty
		game.init();

	}
	
	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		long now = System.nanoTime();
		long updateLength = now - lastLoopTime;
		if(updateLength >= OPTIMAL_TIME)
		{
			
			game.update();	
			lastLoopTime = now;
		}
		if(game.score == 0)
			startTime = System.currentTimeMillis();
		if(game.score % 500 == 0 && speedUp && game.score !=0)
		{
			TARGET_FPS+=5;
			speedUp = false;
		}
		else if(game.score % 500 != 0)
		{
			speedUp = true;
		}
		OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		for(int i =0; i<=750; i+=10)
			shapeRenderer.rect(i,600 , 5,2);
		game.draw(shapeRenderer);
		shapeRenderer.end();

		System.out.println(TARGET_FPS);
		

		
		title = "SCORE: " + Integer.toString(game.score);		
		spriteBatch.begin();
		font.draw(spriteBatch, title, 5, 625);
		title = "TIME: " + Long.toString((System.currentTimeMillis()-startTime)/1000);
		font.draw(spriteBatch, title, 675, 625);
		spriteBatch.end();

	}

	@Override
	public void dispose() {
		// batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		game.snake.state = "Dynamic";
		// System.out.println("key typed");
		if (keycode == Keys.RIGHT && game.snake.vx == 0) {
			game.snake.vx = game.cellSize;
			game.snake.vy = 0;
			game.update();
		} else if (keycode == Keys.LEFT && game.snake.vx == 0) {
			game.snake.vx = game.cellSize * -1;
			game.snake.vy = 0;
			game.update();
		} else if (keycode == Keys.UP && game.snake.vy == 0) {
			game.snake.vx = 0;
			game.snake.vy = game.cellSize;
			game.update();
		} else if (keycode == Keys.DOWN && game.snake.vy == 0) {
			game.snake.vx = 0;
			game.snake.vy = game.cellSize * -1;
			game.update();
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
