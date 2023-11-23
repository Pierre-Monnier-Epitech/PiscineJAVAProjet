package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

public class Projectile extends Sprite {
    protected float speedX;
    protected float speedY;

    public Projectile(Texture texture, float x, float y, float speedX , float speedY) {
        super(texture);
        this.setPosition(x, y);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void update(float deltaTime) {
        this.translate(speedX * deltaTime, speedY * deltaTime);
    }


    public void resize(float newWidth, float newHeight) {
        this.setSize(newWidth, newHeight);
    }

    public boolean collidesWithPlayer(Player player) {
        return this.getBoundingRectangle().overlaps(player.getHitbox());
    }
}
