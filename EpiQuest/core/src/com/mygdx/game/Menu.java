package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu extends ApplicationAdapter {
    private Stage stage;
    private Table table;
    private TextButton playButton;
    private TextButton exitButton;
    private Skin skin;
    private BitmapFont font;
    private SpriteBatch batch;
    private Image backgroundImage;  // New variable for the background image
    private Music menuMusic;

    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        font = new BitmapFont();

        Texture bgTexture = new Texture("bg.jpg");
        backgroundImage = new Image(bgTexture);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;

        playButton = new TextButton("Play", textButtonStyle);
        playButton.pad(20);
        playButton.setPosition(100, 100);

        exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.pad(20);
        exitButton.setPosition(50, 50);

        table = new Table();
        table.setFillParent(true);

        stage.addActor(backgroundImage);

        table.add(playButton);
        table.row();
        table.add(exitButton);

        stage.addActor(table);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("test.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (!menuMusic.isPlaying()) {
            menuMusic.play();
        }
    }

    public boolean isPlayButtonClicked() {
        return playButton.isPressed();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        batch.dispose();
        font.dispose();
        menuMusic.stop();
        menuMusic.dispose();
    }
}
