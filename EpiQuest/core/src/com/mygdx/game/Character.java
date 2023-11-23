package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Character implements Movable {

    protected float x;
    protected float y;
    protected TextureRegion[] downFrames;
    protected TextureRegion[] leftFrames;
    protected TextureRegion[] rightFrames;
    protected TextureRegion[] upFrames;
    protected TextureRegion currentFrame;
    protected Texture skin;
    protected int damage;
    protected int health;
    protected int width;
    protected int height;
    protected Rectangle hitbox;

    public Character(TextureRegion[] downFrames, TextureRegion[] leftFrames, TextureRegion[] rightFrames, TextureRegion[] upFrames, Texture skin, int damage, int health) {
        this.downFrames = downFrames;
        this.leftFrames = leftFrames;
        this.rightFrames = rightFrames;
        this.upFrames = upFrames;
        this.skin = skin;
        this.health = health;
        this.damage = damage;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public Texture getSkin() {
        return skin;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public void receiveDamage(int damage) {
    }

    public abstract void moveUp(float speed);

    public abstract void moveDown(float speed);

    public abstract void moveLeft(float speed);

    public abstract void moveRight(float speed);

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }
}
