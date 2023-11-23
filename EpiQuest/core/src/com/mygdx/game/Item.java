package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Item {
    protected Texture texture;
    protected float x;
    protected float y;
    protected Rectangle hitbox;
    private float width;
    private float height;
    private boolean touched;
    private float scale = 1.0f;

    public Item(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width / 2, height / 2, width, height, scale, scale, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }


    public void setScale(float scale) {
        this.scale = scale;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox.setPosition(x, y);
    }


    public boolean isTouched() {
        return touched;
    }

    public void markAsTouched() {
        touched = true;
    }

    public void updateSize() {
        if (touched) {
            width = texture.getWidth() * scale;
            height = texture.getHeight() * scale;
        }
    }

    public void applyShield(Player player) {
    }
}