package com.mygdx.tetrisGame.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.StreamUtils;
import com.jga.util.game.GameBase;
import com.mygdx.tetrisGame.config.GameConfig;
import com.mygdx.tetrisGame.entity.Brick;
import com.mygdx.tetrisGame.entity.EntityFactory;
import com.mygdx.tetrisGame.entity.Square;

public class GameController {

    private static final Logger log=new Logger(GameController.class.getName(), Logger.DEBUG);

    private final GameBase game;
    private EntityFactory factory;
    private float timeCount=0f;
    private Array<Brick> inactiveBricks =new Array<>();//put inactive bricks into the array
    private Array<Brick> activeBricks=new Array<>();//active brick always changes
    private final Array<Square> inactiveSquares=new Array<>();//put inactive bricks in here
    private boolean start=true;// start to initialize a brick and then counting on the brick as condition

    //constructor,initialized in gamescreen
    public GameController(GameBase game, EntityFactory factory) {
        this.factory=factory;
        this.game = game;
    }

    public Array<Brick> getInactiveBricks() {
        return inactiveBricks;
    }

    public Array<Brick> getActiveBricks() {
        return activeBricks;
    }

    public Array<Square> getInactiveSquares() {
            return inactiveSquares;
    }

    public void update(float delta){



        timeCount+=delta;
        if(timeCount>=GameConfig.BRICK_RENDER_SPAWN_TIME&&start){
            start=false;
            timeCount=0;
           Brick brick1=factory.createBrick();
           activeBricks.add(brick1);
        }

        for (Brick brick: activeBricks
             ) {
            brick.update(delta);

            if(!brick.isActive()){
//                inactiveBricks.add(brick);
                Array<Square> squares=brick.getSquares();
                for (Square square:squares
                     ) {
                    log.debug("square is at x= "+square.getX()+" y= "+square.getY());
                    inactiveSquares.add(square);

                }
                squares.clear();


                activeBricks.removeValue(brick,false);
                brick=null;
                Brick brick1=factory.createBrick();
                activeBricks.add(brick1);
            }
        }

        checkCollision();

        checkScore();



    }



    //check the active brick's  collision with other bricks
    //block bricks from leaving
    private void checkCollision(){

        //check the active brick's  collision with other bricks

        for (Brick activeBrick:activeBricks
             ) {
            //only one active  brick
            Array<Square> activeSquares=activeBrick.getSquares();
            int countRight=0;
            int countLeft=0;
            int countDown=0;

            for (Square activeSquare: activeSquares
                 ) {
                float squareCenterX=activeSquare.getX()+GameConfig.SQUARE_WIDTH/2;
                float squareCenterY=activeSquare.getY()+GameConfig.SQUARE_HEIGHT/2;
                float squareRightX=activeSquare.getX()+GameConfig.SQUARE_WIDTH;
                float squareUpY=activeSquare.getY()+GameConfig.SQUARE_HEIGHT;


                //block square from leaving
                if (activeSquare.getX() <= 0) {
                    activeSquare.setX(0);
                    countLeft++;
                }
                if (activeSquare.getX() >= GameConfig.WORLD_WIDTH - GameConfig.SQUARE_WIDTH) {
                    activeSquare.setX(GameConfig.WORLD_WIDTH - GameConfig.SQUARE_WIDTH);
                    countRight++;
                }
                if (activeSquare.getY() <=0) {
                    activeSquare.setY(0);
                    activeBrick.setActive(false);
                }
                if (activeSquare.getY() < 1) {
                   countDown++;
                }

                    for (Square inactiveSquare: inactiveSquares
                    ) {
                        //check with inactive squares
                        float leftDownPointX=inactiveSquare.getX();
                        float leftDownPointY=inactiveSquare.getY();
                        float leftUpPointX=leftDownPointX;
                        float leftUpPointY=leftDownPointY+GameConfig.SQUARE_HEIGHT;
                        float rightDownPointX=leftDownPointX+GameConfig.SQUARE_WIDTH;
                        float rightDownPointY=leftDownPointY;
                        float rightUpPointX=rightDownPointX;
                        float rightUpPointY=leftUpPointY;
                        //check collision with top of square
                        if(Intersector.distanceSegmentPoint(leftUpPointX,leftUpPointY,rightUpPointX,rightUpPointY,squareCenterX,activeSquare.getY())<0.1){
                            activeBrick.setActive(false);
                        }
                        //check collision with the right of square

//                        log.debug("distance with right side= "+Intersector.distanceSegmentPoint(rightDownPointX,rightDownPointY,rightUpPointX,rightUpPointY,squareCenterX,squareCenterY));
                        if(Intersector.distanceSegmentPoint(rightDownPointX,rightDownPointY,rightUpPointX,rightUpPointY,activeSquare.getX(),squareUpY)<=GameConfig.SQUARE_WIDTH/2){
                            countLeft++;
                        }
                        //check collision with the left of square
                        //logic one : 右边的中点到inactive square左边的距离小于等于0.5
                        if(Intersector.distanceSegmentPoint(leftDownPointX,leftDownPointY,leftUpPointX,leftUpPointY,squareRightX,activeSquare.getY())<=GameConfig.SQUARE_WIDTH/2){
                            countRight++;
                        }

                        //check the space to pull down
                        if(Intersector.distanceSegmentPoint(leftDownPointX,leftDownPointY,rightDownPointX,rightDownPointY,squareCenterX,activeSquare.getY())<=GameConfig.SQUARE_HEIGHT+0.1f){
                            countDown++;
                        }



                    }

            }
//            log.debug("count right = "+countRight);
            if(countLeft>0){
                activeBrick.setLeftClear(false);
            }else {
                activeBrick.setLeftClear(true);
            }
            if(countDown>0){
                activeBrick.setDownClear(false);
            }else {
                activeBrick.setDownClear(true);
            }

            if(countRight>0){
                activeBrick.setRightClear(false);
            }else {
                activeBrick.setRightClear(true);
            }


            if(!activeBrick.isActive()){
                activeBrick.fix();
            }
        }





    }


    private void checkScore() {

        for (int i = 0; i <24 ; i++) {
            //from line 1
            float lineXSum=0;

            for (Square square: inactiveSquares
                 ) {

                if((int) square.getY()==i) {

                    lineXSum += square.getX()+1;

                }

            }
            if(i==0){
//                log.debug("line "+i+" equals "+lineXSum);
            }
            if(lineXSum>=136){
                log.debug("remove line "+i);
                for (int j = 0; j < inactiveSquares.size; j++) {
                    if(inactiveSquares.get(j).getY()==i){
                        inactiveSquares.removeIndex(j);
                    }

                }
                for (Square square: inactiveSquares
                     ) {
                    log.debug("square is x= "+square.getX()+" y= "+square.getY());
                }

                for (int j = 0; j < inactiveSquares.size; j++) {
                    if(inactiveSquares.get(j).getY()==i){
                        inactiveSquares.removeIndex(j);
                    }
                }

                for (int j = 0; j < inactiveSquares.size; j++) {
                    if(inactiveSquares.get(j).getY()==i){
                        inactiveSquares.removeIndex(j);
                    }
                }

                for (int j = 0; j < inactiveSquares.size; j++) {
                    if(inactiveSquares.get(j).getY()==i){
                        inactiveSquares.removeIndex(j);
                    }
                }
                for (int j = 0; j < inactiveSquares.size; j++) {
                    if(inactiveSquares.get(j).getY()==i){
                        inactiveSquares.removeIndex(j);
                    }
                }

                for (int j = 0; j < inactiveSquares.size; j++) {
                    if(inactiveSquares.get(j).getY()>i){
                        inactiveSquares.get(j).setY(inactiveSquares.get(j).getY()-1);
                    }
                }


            }



        }


    }

}
