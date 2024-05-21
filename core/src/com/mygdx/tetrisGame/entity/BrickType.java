package com.mygdx.tetrisGame.entity;

import com.badlogic.gdx.utils.Array;

public enum BrickType {
    LEFTL(new int[]{1, 1, 1, 0}, new int[]{1, 0, 0, 0}),
    RIGHTL(new int[]{1,1,1,0},new int[]{0,0,1,0}),
    I(new int[]{1, 1, 1, 1}, new int[]{0, 0, 0, 0}),
    LEFTZ(new int[]{1, 1, 0, 0}, new int[]{0, 1, 1, 0}),
    RIGHTZ(new int[]{0,1,1,0},new int[]{1,1,0,0}),
    T(new int[]{1, 1, 1, 0}, new int[]{0, 1, 0, 0}),
    SQUARE(new int[]{1, 1, 0, 0}, new int[]{1, 1, 0, 0});

    private int[] line1;
    private int[] line2;

    private BrickType(int[] line1,int[] line2){
        this.line1=line1;
        this.line2=line2;
    }

    public int[] getLine1() {
        return line1;
    }

    public int[] getLine2() {
        return line2;
    }
    public static BrickType getRandomType(){
        Array<BrickType> brickTypes=new Array<>(BrickType.values());
        brickTypes.shuffle();
        return brickTypes.random();
    }

}
