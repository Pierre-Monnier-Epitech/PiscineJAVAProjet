package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import java.util.List;

public class Player extends Character implements Disposable {

    protected BitmapFont font;
    private Animation<TextureRegion> downAnimation;
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> rightAnimation;
    private Animation<TextureRegion> upAnimation;
    private float stateTime;
    protected float attackDuration = 0.0f;
    protected boolean isFacingLeft = false;
    protected boolean isFacingRight = false;
    protected boolean isFacingTop = false;
    protected boolean isFacingBottom = true;
    protected Texture dmgTop;
    protected Texture dmgBottom;
    protected Texture dmgLeft;
    protected Texture dmgRight;
    protected SpriteBatch batch;
    protected Sprite characterSprite;
    protected int maxHealth;
    protected boolean isAlive = true;
    private boolean isCollidingWithMonster = false;
    protected float invulnerabilityDuration = 1.0f;
    private float timeColliding = 0.0f;
    protected float redDuration = 0.5f;
    private float redTimer = 0.0f;
    protected boolean shielded;
    protected ShapeRenderer shapeRenderer;


    public Player(float x, float y, SpriteBatch batch) {
        super(null, null, null, null, new Texture("Characters/hero.png"), 2, 10);
        this.width = 16;
        this.height = 16;
        this.x = x;
        this.y = y;
        stateTime = 0f;
        initFrames();
        dmgTop = new Texture("attackTop.png");
        dmgBottom = new Texture("attackBottom.png");
        dmgLeft = new Texture("attackLeft.png");
        dmgRight = new Texture("attackRight.png");
        this.batch = batch;
        characterSprite = new Sprite();
        this.maxHealth = 10;
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 0);
        if (this.isAlive) {
            if (attackDuration > 0.0f) {
                float attackX = 0, attackY = 0;
                Texture drawAttack = null;
                if (this.isFacingLeft) {
                    attackX = getX() -32 ;
                    attackY = getY() -10;
                    drawAttack = dmgLeft;
                } else if (isFacingRight) {
                    attackX = getX() + 16;
                    attackY = getY() - 8;
                    drawAttack = dmgRight;
                } else if (isFacingTop) {
                    attackX = getX() -8;
                    attackY = getY() + 16;
                    drawAttack = dmgTop;
                } else if (isFacingBottom) {
                    attackX = getX() - 5 ;
                    attackY = getY() - 32 ;
                    drawAttack = dmgBottom;
                }

                batch.draw(drawAttack, attackX, attackY, 32, 32);
                attackDuration -= Gdx.graphics.getDeltaTime();
            }
        }
        if (redTimer > 0.0f) {
            font.draw(batch, "OUCH!", getX(), getY() + getHeight());
            batch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        if (timeColliding > 0.0f) {
            if (isCollidingWithMonster) {
                timeColliding -= deltaTime;
            }
            if (redTimer > 0.0f) {
                redTimer -= deltaTime;
            }
        }
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

    public void attack(List<Monster> monsters) {
        if (attackDuration <= 0.0f) {
            if (attackDuration <= 0.0f) {
                attackDuration = 0.2f;
                float attackX = getX();
                float attackY = getY();
                float attackWidth = 45; // Ajustez la largeur de l'attaque selon vos besoins
                float attackHeight = 45; // Ajustez la hauteur de l'attaque selon vos besoins

                if (isFacingLeft) {
                    attackX -= 32;
                    attackY -= 10;

                } else if (isFacingRight) {
                    attackX += 16;
                    attackY -= 8;

                } else if (isFacingTop) {
                    attackX -= 8;
                    attackY += 16;

                } else if (isFacingBottom) {
                    attackX -= 5;
                    attackY -= 32;
                }

                Rectangle drawAttackHitbox = new Rectangle(attackX, attackY, attackWidth, attackHeight);

                for (Character monster : monsters) {
                    Rectangle monsterHitbox = monster.getHitbox();
                    if (monsterHitbox != null) {
                        if (drawAttackHitbox.overlaps(monsterHitbox)) {
                            System.out.println("Player use a attack on " + monster.getClass().getSimpleName() + ".");
                            monster.receiveDamage(getDamage());
                        } else {
                            System.out.println("Player use a attack.");
                        }
                    }
                }
            }
        }
    }


    public boolean isCollidingWithMonster() {
        return timeColliding > 0.0f && isCollidingWithMonster;
    }

    public void setCollidingWithMonster(boolean colliding) {
        isCollidingWithMonster = colliding;
        if (colliding) {
            timeColliding = invulnerabilityDuration;
        }
    }

    public void receiveDamage(int damage) {
        if (shielded) {
            setShielded(false);
        } else {
            health -= damage;
        }
        if (health <= 0) {
            isAlive = false;
        }
        timeColliding = invulnerabilityDuration;
        redTimer = redDuration;
    }

    public void heal(int amount) {
        if (isAlive) {
            health += amount;
            if (health >= maxHealth) {
                health = maxHealth;
                System.out.println("your life is at the max");
            }else if ( health == 9) {
                health = maxHealth;
                System.out.println("Your health : "+ health +".");
            } else {
                System.out.println("you have gaine "+ amount +" life points.");
                System.out.println("Your health : "+ health +".");
            }
        }
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    public boolean isShielded() {
        return shielded;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }

    @Override
    public void moveUp(float speed) {
        isFacingLeft = false;
        isFacingRight = false;
        isFacingBottom = false;
        isFacingTop = true;
        y += speed;
        currentFrame = upAnimation.getKeyFrame(stateTime, true);
    }
    @Override
    public void moveDown(float speed) {
        isFacingLeft = false;
        isFacingRight = false;
        isFacingBottom = true;
        isFacingTop = false;
        y -= speed;
        currentFrame = downAnimation.getKeyFrame(stateTime, true);
    }

    @Override
    public void moveLeft(float speed) {
        isFacingLeft = true;
        isFacingRight = false;
        isFacingBottom = false;
        isFacingTop = false;
        x -= speed;
        currentFrame = leftAnimation.getKeyFrame(stateTime, true);
    }

    @Override
    public void moveRight(float speed) {
        isFacingLeft = false;
        isFacingRight = true;
        isFacingBottom = false;
        isFacingTop = false;
        x += speed;
        currentFrame = rightAnimation.getKeyFrame(stateTime, true);
    }
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}