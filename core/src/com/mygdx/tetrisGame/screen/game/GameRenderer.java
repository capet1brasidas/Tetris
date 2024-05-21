package com.mygdx.tetrisGame.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.util.GdxUtils;
import com.jga.util.Validate;
import com.jga.util.ViewportUtils;
import com.jga.util.debug.DebugCameraController;
import com.jga.util.debug.ShapeRendererUtils;
import com.jga.util.entity.EntityBase;
import com.mygdx.tetrisGame.assets.AssetDescriptors;
import com.mygdx.tetrisGame.assets.RegionNames;
import com.mygdx.tetrisGame.config.GameConfig;
import com.mygdx.tetrisGame.entity.Brick;
import com.mygdx.tetrisGame.entity.Square;

import java.util.Random;

public class GameRenderer implements Disposable {

    private static final Logger log=new Logger(GameRenderer.class.getName(), Logger.DEBUG);

    //attributes
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private GameController controller;
    private OrthographicCamera camera;
    private Viewport viewport;
    private DebugCameraController debugCameraController;
    private ShapeRenderer renderer;
    private TextureRegion backgroundRegion;

    private TextureRegion redBrickRegion;
    private TextureRegion purpleBrickRegion;
    private TextureRegion greenBrickRegion;

    //constructors,created in game screen,passing controller to renderer
    public GameRenderer(GameController controller,SpriteBatch batch, AssetManager assetManager) {
        this.controller=controller;
        this.batch = batch;
        this.assetManager = assetManager;
        init();
    }
    //init
    private void init(){
        camera=new OrthographicCamera();
        viewport=new FitViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT,camera);

        debugCameraController=new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X,GameConfig.WORLD_CENTER_Y);

        renderer=new ShapeRenderer();
        TextureAtlas gameplayAtlas=assetManager.get(AssetDescriptors.GAME_PLAY);
        backgroundRegion=gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        redBrickRegion=gameplayAtlas.findRegion(RegionNames.RED_BRICK);
        greenBrickRegion=gameplayAtlas.findRegion(RegionNames.GREEN_BRICK);
        purpleBrickRegion=gameplayAtlas.findRegion(RegionNames.PURPLE_BRICK);


    }
    //public methods

    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);
        controller.update(delta);
        //clear screen
        GdxUtils.clearScreen();
//        log.debug("render debug");
        //render debug

        renderGameplay();

        renderDebug();




    }

    private void renderGameplay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawGamePlay();

        batch.end();

    }

    private void drawGamePlay() {
        //background start from 0,0 end with world width &height

        batch.draw(backgroundRegion,0,0,GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT);

        //draw active bricks allocate the texture into squares
        for (Brick brick: controller.getActiveBricks()
             ) {
            TextureRegion textureRegion=redBrickRegion;
            int random=(int)(Math.random()*3);
           if(random==0){
               textureRegion=redBrickRegion;
           } else if (random==1) {
               textureRegion=purpleBrickRegion;
           } else if (random==2) {
               textureRegion=greenBrickRegion;
           }


            for (Square square: brick.getSquares()
                 ) {
                if(square.getSquareTexture()==null){
                    square.setSquareTexture(textureRegion);
                }

                square.drawSquare(batch);
            }

        }

        //draw inactive bricks

        for (Square square: controller.getInactiveSquares()
             ) {
            square.drawSquare(batch);
        }



    }

    public void resize(int width, int height) {
        viewport.update(width,height,true);
//        hudViewport.update(width,height,true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }


    //private methods
    private void renderDebug(){
        viewport.apply();

        ViewportUtils.drawGrid(viewport,renderer);

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();

    }

    private void drawDebug() {
        Color oldColor=renderer.getColor().cpy();

            renderer.setColor(Color.BLUE);

        Array<Square> inactiveSquares=controller.getInactiveSquares();


            for (Square square:inactiveSquares
                 ) {
                Polygon squareRect=square.getBounds();
                ShapeRendererUtils.polygon(renderer,squareRect);
            }

            Array<Brick> activeBricks=controller.getActiveBricks();
            for (Brick brick:activeBricks
                 ) {
                Array<Square> activeSquares=brick.getSquares();
                for (Square square:activeSquares
                ) {
                    Polygon squareRect=square.getBounds();
                    ShapeRendererUtils.polygon(renderer,squareRect);
                }
            }



        renderer.setColor(oldColor);


    }

    private static void drawEntity(SpriteBatch batch, EntityBase entity, TextureRegion region){
        Validate.notNull(batch);
        Validate.notNull(region);
        Validate.notNull(entity);

        batch.draw(region,entity.getX(),entity.getY(),entity.getWidth(),entity.getHeight());


    }


}
