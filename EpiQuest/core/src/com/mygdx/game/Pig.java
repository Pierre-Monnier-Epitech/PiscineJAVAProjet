package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Pig extends Monster {

    public Pig(float x, float y) {
        super(null, null, null, null, new Texture("Characters/pig.png"), 2, 5);
        this.x = x;
        this.y = y;
    }

}