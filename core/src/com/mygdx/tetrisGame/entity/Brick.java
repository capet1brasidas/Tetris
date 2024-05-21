package com.mygdx.tetrisGame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.jga.util.entity.EntityBase;
import com.mygdx.tetrisGame.config.GameConfig;

import java.util.List;

public class Brick extends EntityBase{

    private static final Logger log=new Logger(Brick.class.getName(), Logger.DEBUG);

    private BrickType brickType;
    private Array<Square> squares;
    private boolean isActive=true;
    private boolean leftClear=true;
    private boolean rightClear=true;
    private boolean downClear=true;
    private boolean enableRotate=true;
    private float timeCount=0;


    public Brick() {
//        brickType=BrickType.getRandomType();
        brickType=BrickType.getRandomType();
        squares=new Array<>(4);
        createBrick();
    }

    public void setRightClear(boolean rightClear) {
        this.rightClear = rightClear;
    }

    public void setLeftClear(boolean leftClear) {
        this.leftClear = leftClear;
    }

    public void setDownClear(boolean downClear) {
        this.downClear = downClear;
    }

    public Array<Square> getSquares() {
        return squares;
    }

    //create a brick according to the brick type
    public void createBrick() {
        isActive=true;
        int[] line1=brickType.getLine1();
        int[] line2=brickType.getLine2();
        for (int i = 0; i < brickType.getLine1().length; i++) {
            if(line1[i]==1){
                squares.add(new Square(GameConfig.BRICK_RENDER_POSITION_X+i,
                        GameConfig.BRICK_RENDER_POSITION_Y+1)
                        );
            }
            if(line2[i]==1){
                squares.add(new Square(GameConfig.BRICK_RENDER_POSITION_X+i,
                        GameConfig.BRICK_RENDER_POSITION_Y)
                );
            }
        }
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void fix() {

        for (Square square:squares
             ) {
            square.setVelocityY(0);
//            Thread.sleep(10);
            square.setX((int)(square.getX()+0.5));
            square.setY((int)(square.getY()+0.5));
        }
    }

    private void brickAdjust(int adjustmentX,int adjustmentY){
        for (Square square:squares
             ) {
            square.setPosition(square.getX()+adjustmentX,square.getY()+adjustmentY);
        }

    }

    public float getRightBound(){
        float maxX=0;
        for (Square square:squares
             ) {
            maxX=Math.max(square.getX(),maxX);

        }
        return maxX+1;
    }
    public float getLeftBound(){
        float minX=GameConfig.WORLD_WIDTH;
        for (Square square:squares
        ) {
            minX=Math.max(square.getX(),minX);

        }
        return minX;
    }

    public Array<float[]> getSquarePositions(){
        Array<float[]> squarePositions=new Array<>();
        for (Square square:squares
             ) {
            squarePositions.add(new float[]{square.getX(), square.getY()});

        }
        return squarePositions;

    }

    public int getCenterX(){
        int sumX=0;
        for (Square square:squares
             ) {
            sumX+=square.getX();
        }
        return (int)sumX/4;

    }

    public int getCenterY(){
        int sumY=0;
        for (Square square:squares
        ) {
            sumY+=square.getY();
        }
        return (int)sumY/4;

    }


    @Override
    public void update(float delta) {
        float xSum=0;
        float ySum=0;

        int timeCount=0;



        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)&&rightClear){
            brickAdjust(1,0);
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)&&leftClear){
            brickAdjust(-1,0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            //write the logic of rotating
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)&&downClear) {
            brickAdjust(0,-1);
        }

//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&&rightClear){
//            if(Math.random()>0.5){
//                brickAdjust(1,0);
//            }
//        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)&&leftClear) {
//            if(Math.random()>0.5){
//                brickAdjust(-1,0);
//            }
//        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)&&downClear) {
//
//            if(Math.random()>0.5){
//                brickAdjust(0,-1);
//            }
//        }
        int centerX=getCenterX();
        int centerY=getCenterY();


        if(!rightClear&&!leftClear){
            if(getRightBound()-getLeftBound()<=GameConfig.SQUARE_WIDTH*2){
                    enableRotate=false;
            }
        }else {

            enableRotate=true;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)&&enableRotate) {
            boolean hasAdjustedX=false;
            boolean hasAdjustedY=false;
            //for rotation adjustment
            int adjustmentX=0;
            int adjustmentY=0;


            for (Square square : squares
            ) {
                int x = (int) square.getX() - centerX;
                int y = (int) square.getY() - centerY;
//                if(!rightClear&&!hasAdjustedX){
//                    if(getRightBound()-getLeftBound()==3){
//                        adjustmentX--;
//                        hasAdjustedX=true;
//                    }
//                }
//
//                if(!leftClear&&!hasAdjustedX){
//                    if(getRightBound()-getLeftBound()==3){
//                        adjustmentX++;
//                        hasAdjustedX=true;
//                    }
//                }

                if(-x + centerY<0&&!hasAdjustedY){
                    adjustmentY++;
                    hasAdjustedY=true;
                }
                if((y + centerX<0)&&!hasAdjustedX){
                    adjustmentX++;
                    hasAdjustedX=true;
                }
                if(y + centerX>=GameConfig.WORLD_WIDTH&&!hasAdjustedX){
                    adjustmentX--;
                    hasAdjustedX=true;
                }

                log.debug("adjustmentx= "+adjustmentX);
                log.debug("adjustmenty= "+adjustmentY);

                square.setPosition(y + centerX, -x + centerY);

            }
            brickAdjust(adjustmentX,adjustmentY);

        }

        updateSquares(delta);

     }

    private void updateSquares(float delta) {
        for (Square square:squares
             ) {
            square.update(delta);
        }

    }


    public void fixWithoutStop(){
        for (Square square:squares
        ) {
            square.setX((int)(square.getX()+0.5));
            square.setY((int)(square.getY()+0.5));
        }
    }

    public void clearAll(){
        squares.clear();


    }

}
