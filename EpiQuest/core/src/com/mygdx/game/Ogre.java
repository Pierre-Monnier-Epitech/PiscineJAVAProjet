package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Ogre extends Monster {

    protected Random random = new Random();

    public Ogre(float x, float y) {
        super(null, null, null, null, new Texture("Characters/ogre.png"), 5, 2);
        this.x = x;
        this.y = y;
    }

    public void updateOgre(float deltaTime, Player player) {
        if (player.isAlive) {
            stateTime += deltaTime;

            float randomSpeed = 2.0f;

            if (random.nextInt(100) < 0.5) {
                changerDirectionVersPlayer(player);
            }

            hitbox = new Rectangle(x, y, getWidth(), getHeight());

            if (isAlive) {

                if (!player.isCollidingWithMonster() && hitbox.overlaps(player.getHitbox()) && player.isAlive) {
                    int damageInflicted = getDamage();
                    this.receiveDamage(10000);
                    player.receiveDamage(damageInflicted);
                    player.setCollidingWithMonster(true);
                    System.out.println(this.getClass().getSimpleName() + " inflicts " + damageInflicted + " damage to the " + player.getClass().getSimpleName() + ".");
                    System.out.println(player.getClass().getSimpleName() + " health: " + player.health);

                }


                float playerDirectionX = player.getX() - getX();
                float playerDirectionY = player.getY() - getY();

                float length = (float) Math.sqrt(playerDirectionX * playerDirectionX + playerDirectionY * playerDirectionY);
                playerDirectionX /= length;
                playerDirectionY /= length;


                // Update monster position towards the player
                x += playerDirectionX * randomSpeed;
                y += playerDirectionY * randomSpeed;

                // Check if the monster is dead after update
                if (health <= 0) {
                    isAlive = false;
                }
            }
        }
    }

    private void changerDirectionVersPlayer(Player player) {

        float playerX = player.getX();
        float playerY = player.getY();

        directionX = playerX - x;
        directionY = playerY - y;

        // Normalize the direction vector to have unit length
        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
        if (length > 0) {
            directionX /= length;
            directionY /= length;

        }
    }
}
