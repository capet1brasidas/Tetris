package com.mygdx.tetrisGame.screen.game;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jga.util.ads.AdController;
import com.jga.util.game.GameBase;
import com.jga.util.screen.ScreenBaseAdapter;
import com.mygdx.tetrisGame.entity.EntityFactory;

public class GameScreen extends ScreenBaseAdapter {

    private final  GameBase game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private EntityFactory factory;
    private GameRenderer renderer;
    private GameController controller;

    public GameScreen(GameBase game) {
        this.game=game;
        assetManager=game.getAssetManager();
        batch=game.getBatch();
    }

    @Override
    public void show() {
        factory=new EntityFactory(assetManager);
        controller=new GameController(game,factory);
        renderer=new    GameRenderer(controller,batch,assetManager);
    }

    @Override
    public void render(float delta) {
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width,height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
