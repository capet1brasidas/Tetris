package com.mygdx.tetrisGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jga.util.ads.AdController;
import com.jga.util.game.GameBase;
import com.mygdx.tetrisGame.screen.game.GameScreen;
import com.mygdx.tetrisGame.screen.loading.LoadingScreen;

public class TetrisGame extends GameBase {

	public TetrisGame(AdController adController) {
		super(adController);
	}

	@Override
	public void postCreate() {
		setScreen(new LoadingScreen(this));
	}

}
