package com.mygdx.tetrisGame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jga.util.ads.NullAdController;
import com.mygdx.tetrisGame.config.GameConfig;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(GameConfig.WIDTH,GameConfig.HEIGHT);
		config.setForegroundFPS(60);
		config.setTitle("Tetris");
		new Lwjgl3Application(new TetrisGame(NullAdController.INSTANCE), config);
	}
}
