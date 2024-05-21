package com.mygdx.tetrisGame.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;

public class EntityFactory {
    //attributes
    private final AssetManager assetManager;
    private Array<Brick> bricks;


    //constructors


    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
        init();
    }
    //init
    private void init(){
        bricks=new Array<>();


    }

    public Brick createBrick(){

        return  new Brick();
    }

}
