package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Random;

public class Boss extends Monster{

    // Constructor
    public Boss(float x, float y) {
        super(null, null, null, null, new Texture("Characters/rock.png"),4,20); // Les paramètres seront définis dans la méthode init
        this.x = x;
        this.y = y;
        this.width = 48;
        this.height = 48;
    }
    public void updateBoss(float deltaTime, Player player, List<Monster> otherMonsters) {
        stateTime += deltaTime;

        float randomSpeed = 1.5f;

        if (random.nextInt(100) < 0.5) {
            changerDirectionVersPlayer(player);
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
            float playerDirectionX = player.getX() - getX();
            float playerDirectionY = player.getY() - getY();

            float length = (float) Math.sqrt(playerDirectionX * playerDirectionX + playerDirectionY * playerDirectionY);
            if (length != 0) {
                playerDirectionX /= length;
                playerDirectionY /= length;
            }

            for (Monster otherMonster : otherMonsters) {
                if (otherMonster != this) {
                    float avoidX = getX() - otherMonster.getX();
                    float avoidY = getY() - otherMonster.getY();
                    float avoidLength = (float) Math.sqrt(avoidX * avoidX + avoidY * avoidY);

                    if (avoidLength < 4) {
                        // Ajuster le vecteur de direction pour éviter la collision
                        avoidX /= avoidLength;
                        avoidY /= avoidLength;

                        playerDirectionX += avoidX;
                        playerDirectionY += avoidY;
                    }
                }
            }

            x += playerDirectionX * randomSpeed;
            y += playerDirectionY * randomSpeed;

            // Check if the monster is dead after update
            if (health <= 0) {
                isAlive = false;
            }
        }
    }
    private void changerDirectionVersPlayer(Player player) {
        float playerX = player.getX();
        float playerY = player.getY();

        directionX = playerX - x;
        directionY = playerY - y;

        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
        if (length > 0) {
            directionX /= length;
            directionY /= length;
        }
    }
}
