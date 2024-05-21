package com.mygdx.tetrisGame;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {
    public static final String RAW_ASSETS_PATH="assets";

    public static final String ASSETS_PATH="assets";

    public static void main(String[] args) {

        TexturePacker.process(
                RAW_ASSETS_PATH+"/gameplay",
                ASSETS_PATH+"/gameplay",
                "gameProperties"
        );


    }



}
