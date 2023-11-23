package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.List;
public class Movement {
    public static void playerMovement(Player player, TiledMap currentMap, List<Monster> monsters) {
        float speed = 3;

        float playerX = player.getX();
        float playerY = player.getY();
        float playerWidth = player.getWidth();
        float playerHeight = player.getHeight();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && CollisionUtils.isCollidingWith(currentMap, "Collision", playerX - speed, playerY, playerWidth, playerHeight)) {
            player.moveLeft(speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && CollisionUtils.isCollidingWith(currentMap, "Collision", playerX + speed, playerY, playerWidth, playerHeight)) {
            player.moveRight(speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && CollisionUtils.isCollidingWith(currentMap, "Collision", playerX, playerY + speed, playerWidth, playerHeight)) {
            player.moveUp(speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && CollisionUtils.isCollidingWith(currentMap, "Collision", playerX, playerY - speed, playerWidth, playerHeight)) {
            player.moveDown(speed);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.attack(monsters);
        }
    }
    public static void monsterMovement(Monster monster, TiledMap currentMap) {
        float speed = 3;
        float playerX = monster.getX();
        float playerY = monster.getY();
        float playerWidth = monster.getWidth();
        float playerHeight = monster.getHeight();

        if (CollisionUtils.isCollidingWith(currentMap,"Collision",playerX - speed, playerY, playerWidth, playerHeight)) {
            monster.moveLeft(speed);
        }
        if (CollisionUtils.isCollidingWith(currentMap,"Collision",playerX + speed, playerY, playerWidth, playerHeight)) {
            monster.moveRight(speed);
        }
        if (CollisionUtils.isCollidingWith(currentMap,"Collision",playerX, playerY + speed, playerWidth, playerHeight)) {
            monster.moveUp(speed);
        }
        if (CollisionUtils.isCollidingWith(currentMap, "Collision",playerX, playerY - speed, playerWidth, playerHeight)) {
            monster.moveDown(speed);
        }
    }
}