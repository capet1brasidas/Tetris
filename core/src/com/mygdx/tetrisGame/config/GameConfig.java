package com.mygdx.tetrisGame.config;

public class GameConfig {
    //constants
    public static final int WIDTH=512;
    public static final int HEIGHT=768;//pixels

    public static final float HUD_WIDTH=600f;//world units
    public static final float HUD_HEIGHT=1200f;

    public static final float WORLD_WIDTH=16f;//world units
    public static final float WORLD_HEIGHT=24f;
    public static final float WORLD_CENTER_X=WORLD_WIDTH/2;
    public static final float WORLD_CENTER_Y=WORLD_HEIGHT/2;
    public static final float DROP_SPEED=-1f;
    public static final float BRICK_RENDER_POSITION_X=WORLD_CENTER_X-2f;
    public static final float BRICK_RENDER_POSITION_Y=WORLD_HEIGHT;

    public static final float BRICK_RENDER_SPAWN_TIME=0.1f;
    public static final float SQUARE_WIDTH=1f;
    public static final float SQUARE_HEIGHT=1f;
    public static final float RESET_BRICK_LEFT_RIGHT=0.1f;
    public static final float DIAGONAL_LENGTH_OF_SQUARE=1.7f;
    public static final float SPEED_INTERVAL=0.1f;

    //constructors
    private GameConfig(){}

}
