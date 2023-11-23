package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeartRenderer {
    private Texture heartTexture;
    private Character character;
    private float x;
    private float y;

    public HeartRenderer(float x, float y, Character character) {
        this.x = x;
        this.y = y;
        this.character = character;
        heartTexture = new Texture("heart.png");
    }

    public void draw(SpriteBatch batch) {
        int totalHearts = character.getHealth();

        for (int i = 0; i < totalHearts; i++) {
            float heartX = x + i * (heartTexture.getWidth() + 2);
            float heartY = y;

            batch.draw(getHeartRegion(i), heartX + 10, heartY);
        }
    }

    private TextureRegion getHeartRegion(int index) {
        int columnIndex = 0;
        int rowIndex = 1;

        if (index < character.getHealth()) {
            int regionX = columnIndex * (heartTexture.getWidth() / 2);
            int regionY = rowIndex * (heartTexture.getHeight() / 2);
            int regionWidth = heartTexture.getWidth() / 2;
            int regionHeight = heartTexture.getHeight() / 2;

            return new TextureRegion(heartTexture, regionX, regionY, regionWidth, regionHeight);
        } else {
            return new TextureRegion(heartTexture, 0, 0, 0, 0);
        }
    }

    public void dispose() {
        heartTexture.dispose();
    }
}