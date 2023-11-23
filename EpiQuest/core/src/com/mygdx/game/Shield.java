package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Shield extends Item {
    protected float elapsedDuration;
    protected BitmapFont font;
    protected HeartRenderer heartRenderer;
    protected Player player;

    public Shield(Texture texture, float x, float y) {
        super(texture, x, y);
        this.elapsedDuration = 0;
        font = new BitmapFont();
        // Ensure you have an instance of Player
        this.heartRenderer = new HeartRenderer(0, Gdx.graphics.getHeight() - 20, player);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    @Override
    public void applyShield(Player player) {
        setPosition(10, 590);
        player.setShielded(true);
        System.out.println(player.getClass().getSimpleName() + " to recover the shield.");
    }
}