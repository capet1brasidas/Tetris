package com.mygdx.tetrisGame.screen.loading;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.utils.Array;
import com.jga.util.game.GameBase;
import com.jga.util.screen.loading.LoadingScreenBase;
import com.mygdx.tetrisGame.assets.AssetDescriptors;
import com.mygdx.tetrisGame.screen.game.GameScreen;

public class LoadingScreen extends LoadingScreenBase {


    public LoadingScreen(GameBase game) {
        super(game);
    }


    @Override
    protected Array<AssetDescriptor> getAssetDescriptors() {
        return AssetDescriptors.ALL;
    }

    @Override
    protected void onLoadDone() {
        game.setScreen(new GameScreen(game));

    }
}
