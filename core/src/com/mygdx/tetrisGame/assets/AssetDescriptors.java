package com.mygdx.tetrisGame.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class AssetDescriptors {
    public static final AssetDescriptor<TextureAtlas>GAME_PLAY=
            new AssetDescriptor<TextureAtlas>(AssetPath.GAME_PLAY, TextureAtlas.class);

    public static final Array<AssetDescriptor> ALL=new Array<AssetDescriptor>();

    //static init
    static {
        ALL.addAll(
                GAME_PLAY
        );
    }
    private AssetDescriptors(){}
}
