package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Alien extends Monster {

    protected float timeSinceLastShot;
    protected float shotCooldown;

    public Alien(float x, float y) {
        super(null, null, null, null, new Texture("Characters/alien.png"), 2, 3); // Les paramètres seront définis dans la méthode init
        this.x = x;
        this.y = y;
        timeSinceLastShot = 0;
        shotCooldown = 2.0f;
    }

    @Override
    public void update(float deltaTime, Player player) {
        super.update(deltaTime, player);
        timeSinceLastShot += deltaTime;
    }

    public Projectile shootProjectile(Player player) {
        if (timeSinceLastShot > shotCooldown) {
            float speed = 100;
            float playerX = player.getX();
            float playerY = player.getY();
            float deltaX = playerX - getX();
            float deltaY = playerY - getY();

            float angle = MathUtils.atan2(deltaY, deltaX);
            float speedX = MathUtils.cos(angle) * speed;
            float speedY = MathUtils.sin(angle) * speed;

            Projectile projectile = new Projectile(new Texture("AttackAlien.png"), getX(), getY(), speedX, speedY);

            projectile.resize(16, 16);

            timeSinceLastShot = 0;
            return projectile;
        } else {
            return null;
        }
    }
}