package com.mygdx.tetrisGame.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.jga.util.entity.EntityBase;
import com.mygdx.tetrisGame.config.GameConfig;
import com.mygdx.tetrisGame.screen.game.GameRenderer;

public class Square extends EntityBase{

    private TextureRegion squareTexture;

    public Square(float x,float y){
        super();
        super.setPosition(x,y);
        super.setVelocityY(GameConfig.DROP_SPEED);

    }

    public void setSquareTexture(TextureRegion squareTexture) {
        this.squareTexture = squareTexture;
    }

    public TextureRegion getSquareTexture() {
        return squareTexture;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    //can only be used between batch begin and end
    public void drawSquare(SpriteBatch batch){
        Rectangle rectangle=super.bounds.getBoundingRectangle();

        batch.draw(squareTexture,rectangle.x, rectangle.y,
              rectangle.width,rectangle.height);

    }


}
