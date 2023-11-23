package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Monster extends Character {
    protected float directionX;
    protected float directionY;
    protected Animation<TextureRegion> downAnimation;
    protected Animation<TextureRegion> leftAnimation;
    protected Animation<TextureRegion> rightAnimation;
    protected Animation<TextureRegion> upAnimation;
    protected float stateTime;
    protected boolean isAlive = true;
    protected int lastDamageReceived = 0;
    protected float displayDamageDuration = 0.5f;
    protected float displayDamageTimer = 0;
    protected Random random;
    protected List<Item> droppedItems;

    public Monster(TextureRegion[] downFrames, TextureRegion[] leftFrames, TextureRegion[] rightFrames, TextureRegion[] upFrames, Texture skin, int damage, int healt) {
        super(downFrames, leftFrames, rightFrames, upFrames, skin, damage, healt);

        initFrames();
        this.width = 16;
        this.height = 16;
        stateTime = 0f;
        hitbox = getHitbox();
        random = new Random();
        droppedItems = new ArrayList<>();
        changerDirectionAleatoire();
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void moveLeft(float speed) {
        x -= speed;
        updateFrame(leftAnimation);
    }

    @Override
    public void moveRight(float speed) {
        x += speed;
        updateFrame(rightAnimation);
    }

    @Override
    public void moveUp(float speed) {
        y += speed;
        updateFrame(upAnimation);
    }

    @Override
    public void moveDown(float speed) {
        y -= speed;
        updateFrame(downAnimation);
    }

    private void updateFrame(Animation<TextureRegion> animation) {
        currentFrame = animation.getKeyFrame(stateTime, true);
    }

    private void changerDirectionAleatoire() {
        directionX = random.nextFloat() * 2 - 1;
        directionY = random.nextFloat() * 2 - 1;
    }

    public List<Item> getDroppedItems() {
        return droppedItems;
    }

    protected void initFrames() {
        TextureRegion[][] regions = TextureRegion.split(getSkin(), getSkin().getWidth() / 3, getSkin().getHeight() / 4);

        downFrames = new TextureRegion[regions.length];
        upFrames = new TextureRegion[regions.length];
        rightFrames = new TextureRegion[regions.length];
        leftFrames = new TextureRegion[regions.length];

        for (int i = 0; i < regions.length; i++) {
            downFrames[i] = regions[i][0];
            leftFrames[i] = new TextureRegion(regions[i][2]);
            leftFrames[i].flip(true, false);
            upFrames[i] = regions[i][1];
            rightFrames[i] = regions[i][2];
        }

        downAnimation = new Animation<>(0.1f, downFrames);
        leftAnimation = new Animation<>(0.1f, leftFrames);
        rightAnimation = new Animation<>(0.1f, rightFrames);
        upAnimation = new Animation<>(0.1f, upFrames);
        currentFrame = downFrames[0];
    }

    public void update(float deltaTime, Player player) {
        stateTime += deltaTime;

        float randomSpeed = 1.0f;

        if (random.nextInt(100) < 0.5) {
            changerDirectionAleatoire();
        }

        hitbox = new Rectangle(x, y, getWidth(), getHeight());

        if (isAlive) {
            if (!player.isCollidingWithMonster() && hitbox.overlaps(player.getHitbox())) {
                int damageInflicted = getDamage();
                player.receiveDamage(damageInflicted);
                player.setCollidingWithMonster(true);
                System.out.println(this.getClass().getSimpleName() + " inflicts " + damageInflicted + " damage to the " + player.getClass().getSimpleName()+".");
                System.out.println(player.getClass().getSimpleName() + " health: " + player.health);
            }

            if (directionX > 0) {
                moveRight(randomSpeed);
            } else if (directionX < 0) {
                moveLeft(randomSpeed);
            }

            if (directionY > 0) {
                moveUp(randomSpeed);
            } else if (directionY < 0) {
                moveDown(randomSpeed);
            }

            if (health <= 0) {
                isAlive = false;
            }
        }
    }

    public void updateDisplayDamageTimer(float deltaTime) {
        displayDamageTimer -= deltaTime;
    }

    @Override
    public void receiveDamage(int damage) {
        health -= damage;
        lastDamageReceived = damage;
        if (health <= 0) {
            System.out.println(getClass().getSimpleName() + " is Dead.");
            health = 0 ;
            isAlive = false;
        } else {
            System.out.println(getClass().getSimpleName() + " received " + damage + " damage."+ getClass().getSimpleName()+ " have health: " + health);
            displayDamageTimer = displayDamageDuration;
        }
    }

    public float getDisplayDamageTimer() {
        return displayDamageTimer;
    }

}