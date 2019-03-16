package com.snakegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.snakegame.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Snake";
		config.height = 650;
		config.width = 750;
		config.resizable = false;
		new LwjglApplication(new Game(), config);
	}
}
