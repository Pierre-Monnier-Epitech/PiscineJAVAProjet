package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {


	private BitmapFont buttonFont;
	private Viewport viewport;
	private boolean restartButtonClicked = false;
	private boolean exitButtonClicked = false;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Player player;
	private TiledMap mapBegin;
	private TiledMap currentMap;
	private HeartRenderer heartRenderer;
	private Menu menu;
	private BitmapFont gameOverFont;
	private OrthogonalTiledMapRenderer renderer;
	private BitmapFont font;
	private BitmapFont boom;
	private ShapeRenderer shapeRenderer;
	private List<Monster> monsters;
	private boolean gameStarted = false;
	private boolean charactersCreated = false;
	private List<Item> potions;
	protected List<Item> potionsToRemove = new ArrayList<>();
	protected List<Item> keys;
	protected List<Item> shields;
	protected List<Item> shieldsToRemove = new ArrayList<>();
	private boolean gameOver = false;
	private enum GameState {
		DESERT,
		SWAMP,
		BOSS,
		VICTORY
	}
	private GameState gameState = GameState.DESERT;
	private Boss boss;

	protected int livingMonsterCount;
	private List<Projectile> projectiles;

	private void clearCharacters() {
		monsters.clear();
		livingMonsterCount = 0;
	}
	private void clearPlayer(){
		player = null;
	}

	private void restartGame() {

		System.out.println("restart ok");
		keys.clear();
		potions.clear();
		shields.clear();
		clearCharacters();
		clearPlayer();
		createMonsters();
		createPlayer();
		if (gameState == GameState.SWAMP) {
			gameState = GameState.DESERT;
			changeMap("Desert"); // Assurez-vous d'implémenter la méthode changeMap pour revenir au désert
		} else if (gameState == GameState.BOSS) {
			gameState = GameState.DESERT;
			changeMap("Desert");
		}
		gameOver = false;
		currentMap = mapBegin;
		renderer.setMap(currentMap);
	}

	private void changeMap(String mapName) {
		keys.clear();
		potions.clear();
		int randomMapNumber = new Random().nextInt(3) + 1;
		currentMap = new TmxMapLoader().load(mapName + "/" + mapName + randomMapNumber + ".tmx");
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

        switch (mapName) {
            case "Desert":
            case "Swamp":
				createMonsters();
                break;
            case "Boss":
                createBoss();
                break;
			case "Win":
			break;
        }

		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		renderer.setMap(currentMap);
		player.setPosition(490, 20);

	}

	public void createPlayer(){
		player = new Player(400, 200, batch);
		player.initFrames();
		heartRenderer = new HeartRenderer(0, Gdx.graphics.getHeight() - 20, player);
	}
	public void createBoss() {
		monsters = new ArrayList<>();
		boss = new Boss(400, 400);
		boss.initFrames();
		monsters.add(boss);
		Item cle = new Item(new Texture("key.png"), 450, 300);
		keys.add(cle);
		livingMonsterCount += monsters.size();
		charactersCreated = true;
	}

	private void createMonsters() {

		monsters = new ArrayList<>();
		Random random = new Random();

		int numberOfMonsters = random.nextInt(3) + 1;

		boolean ogreCreated = true;

		for (int i = 0; i < numberOfMonsters; i++) {
			int monsterType = new Random().nextInt(4) + 1;

			Monster monster;

			if (monsterType == 3){
				if (ogreCreated) {
					ogreCreated = false;
				} else {
					monsterType = random.nextInt(4) + 1;
				}
		}

			switch (monsterType) {
				case 1:
					System.out.println("----- creating a Pig -----");
					monster = new Pig(400, 400);
					break;
				case 2:
					System.out.println("----- creating aa Alien -----");
					monster = new Alien(400, 400);
					break;
				case 3:
					System.out.println("----- creating an Ogre -----");
					monster = new Ogre(400,400);
					break;
				case 4:
					System.out.println("----- creating a Zombie -----");
					monster = new Zombie(400, 400);
					break;
				default :
					System.out.println("----- creating an Ogre -----");
					monster = new Ogre(400, 400);
					break;
			}

			monster.initFrames();
			monsters.add(monster);
		}

		Item cle = new Item(new Texture("key.png"), 450, 300);
		keys.add(cle);

		livingMonsterCount += monsters.size();
		charactersCreated = true;
	}

	@Override
	public void create() {
		menu = new Menu();
		menu.create();
		shapeRenderer = new ShapeRenderer();
		int randomMapNumber = new Random().nextInt(3) + 1;
		mapBegin = new TmxMapLoader().load("Desert/Desert" + randomMapNumber + ".tmx");
		currentMap = mapBegin;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		camera.update();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		renderer = new OrthogonalTiledMapRenderer(currentMap);

		batch = new SpriteBatch();
		font = new BitmapFont();
		potions = new ArrayList<>();
		keys = new ArrayList<>();
		shields = new ArrayList<>();
		projectiles = new ArrayList<>();

		gameOverFont = new BitmapFont();
		gameOverFont.getData().setScale(3);
		gameOverFont.setColor(Color.RED);

		buttonFont = new BitmapFont();
	}


	@Override
	public void render() {
		if (!gameStarted) {
			if (menu.isPlayButtonClicked()) {
				System.out.println("=====================================");
				System.out.println("          Launch of the game         ");
				System.out.println("=====================================");
				gameStarted = true;
				createMonsters();
				createPlayer();
				System.out.println("=====================================");

			}
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (gameStarted) {
			renderGame();
		} else {
			renderMenu();
		}
	}

	private void renderGame() {
		Iterator<Monster> iterator = monsters.iterator();
		float deltaTime = Gdx.graphics.getDeltaTime();

		if (player != null) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			camera.update();
			renderer.setView(camera);
			renderer.render();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();

			Movement.playerMovement(player, currentMap, monsters);
			player.render();
			player.update(deltaTime);
			heartRenderer.draw(batch);

			while (iterator.hasNext()) {
				Monster monster = iterator.next();

				if (monster.isAlive() && monster instanceof Boss) {
					Movement.monsterMovement(monster, currentMap);
					float scale = 2.5f;
					float bossWidth = boss.getCurrentFrame().getRegionWidth() * scale;
					float bossHeight = boss.getCurrentFrame().getRegionHeight() * scale;
					boss.updateBoss(deltaTime, player, monsters);
					monster.updateDisplayDamageTimer(deltaTime);
					batch.draw(monster.getCurrentFrame(), monster.getX(), monster.getY(), bossWidth, bossHeight);
				}else if (monster.isAlive() && monster instanceof Ogre ) {
					Movement.monsterMovement(monster, currentMap);
					((Ogre) monster).updateOgre(deltaTime, player);
					monster.updateDisplayDamageTimer(deltaTime);
					batch.draw(monster.getCurrentFrame(), monster.getX(), monster.getY());
				}
				else if (monster.isAlive()) {
					Movement.monsterMovement(monster, currentMap);
					monster.update(deltaTime, player);
					monster.updateDisplayDamageTimer(deltaTime);
					batch.draw(monster.getCurrentFrame(), monster.getX(), monster.getY());
					if (monster instanceof Alien) {
						Alien alien = (Alien) monster;
						if (alien.timeSinceLastShot > alien.shotCooldown) {
							projectiles.add(alien.shootProjectile(player));
							System.out.println("Alien has fired a projectile!");
						}
					}
				}
				else {
					iterator.remove();
					if (monster instanceof Zombie) {
						System.out.println("Oh ! A zombie dropped a potion.");
						Item potion = new Item(new Texture("empty.png"), monster.getX(), monster.getY());
						potions.add(potion);
					}
					if (monster instanceof Pig) {
						System.out.println("Oh ! A pig dropped a shield.");
						Shield shield = new Shield(new Texture("shield.png"), monster.getX(), monster.getY());
						shields.add(shield);
						System.out.println("Added shield");
					}
				}

				if (monster.getDisplayDamageTimer() > 0) {
					font.setColor(Color.RED);
					font.draw(batch, "" + player.getDamage(), monster.getX() + 50, monster.getY() + 50);
				}
			}

		Iterator<Projectile> projectileIterator = projectiles.iterator();
		while (projectileIterator.hasNext()) {
			Projectile projectile = projectileIterator.next();
			projectile.update(Gdx.graphics.getDeltaTime());
			batch.draw(projectile, projectile.getX(), projectile.getY());

			if (projectile.collidesWithPlayer(player)) {
				player.receiveDamage(1);
				projectileIterator.remove();
			}
		}

			if (player.isAlive) {
				batch.draw(player.getCurrentFrame(), player.getX(), player.getY());
				if (!CollisionUtils.isCollidingWith(currentMap, "Door", player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
					if (!keys.isEmpty() && keys.get(0).isTouched() && gameState == GameState.DESERT) {
						gameState = GameState.SWAMP;
						changeMap("Swamp");
					} else if (!keys.isEmpty() && keys.get(0).isTouched() && gameState == GameState.SWAMP) {
						gameState = GameState.BOSS;
						changeMap("Boss");
					}
				}
				if (!CollisionUtils.isCollidingWith(currentMap, "Chest", player.getX(), player.getY(), player.getWidth(), player.getHeight())){
					if(!keys.isEmpty() && keys.get(0).isTouched() && gameState == GameState.BOSS){
						gameState = GameState.VICTORY;
						changeMap("Win");
					}
				}
				if (!CollisionUtils.isCollidingWith(currentMap, "Restart", player.getX(), player.getY(), player.getWidth(), player.getHeight())){
					gameState = GameState.DESERT;
					restartGame();
				}
				for (Item potion : potions) {
					potion.render(batch);
					if (player.getHitbox().overlaps(potion.getHitbox())) {
						player.heal(2);
						potionsToRemove.add(potion);
					}
				}
				for (Item shield : shields) {
					shield.render(batch);
					for (Monster monster : monsters) {
						if (player.getHitbox().overlaps(monster.getHitbox())) {
							shieldsToRemove.add(shield);
						}
					}
					if (player.getHitbox().overlaps(shield.getHitbox()) && !player.isShielded()) {
						shield.applyShield(player);
					}
				}
					shields.removeAll(shieldsToRemove);
					shieldsToRemove.clear();
					potions.removeAll(potionsToRemove);

			} else {

				float gameOverX = 320;
				float gameOverY = 500;

				float restartButtonX = 420;
				float restartButtonY = 425;

				float exitButtonX = (float) 430.00;
				float exitButtonY = (float) 390;

				gameOverFont.draw(batch, "Game Over", gameOverX, gameOverY);

				buttonFont.setColor(Color.WHITE);
				buttonFont.draw(batch, "Restart", restartButtonX, restartButtonY+10);

				buttonFont.setColor(Color.WHITE);
				buttonFont.draw(batch, "Exit", exitButtonX, exitButtonY+10);

				if (Gdx.input.isTouched()) {
					float mouseX = Gdx.input.getX();
					float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

					if (isClicked(mouseX, mouseY, restartButtonX, restartButtonY, buttonFont, "Restart")) {
						restartButtonClicked = true;
					}

					if (isClicked(mouseX, mouseY, exitButtonX, exitButtonY, buttonFont, "Exit")) {
						exitButtonClicked = true;
					}
				}
			}

			if (!player.isAlive && !gameOver) {
				System.out.println("=========== Game Over ============");
				gameOver = true;

			}

			if (restartButtonClicked) {
				restartButtonClicked = false;
				restartGame();
			} else if (exitButtonClicked) {
				System.out.println("exit");
				exitButtonClicked = false;
				Gdx.app.exit();
			}

			if (charactersCreated) {
				List<Monster> copyOfMonsters = new ArrayList<>(monsters);
				Iterator<Monster> verif = copyOfMonsters.iterator();

				boolean noMoreMonsters = !verif.hasNext();
				if (noMoreMonsters) {
					for (Item key : keys) {
						if (player.getHitbox().overlaps(key.getHitbox())) {
							System.out.println(player.getClass().getSimpleName() + " recovered a key.");
							float newX = 10;
							float newY = 10;
							key.setPosition(newX, newY);

							key.setPosition(10, 10);
							key.markAsTouched();
							key.setScale(2.0f);
						}
						key.updateSize();
						key.render(batch);
					}
				}
			}
			batch.end();
		}
	}

	private void renderMenu() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		menu.render();
	}

	@Override
	public void dispose() {
		mapBegin.dispose();
		currentMap.dispose();
		renderer.dispose();
		batch.dispose();
		heartRenderer.dispose();
		shapeRenderer.dispose();
	}
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	private boolean isClicked(float mouseX, float mouseY, float buttonX, float buttonY, BitmapFont font, String text) {
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, text);

		return mouseX >= buttonX && mouseX <= buttonX + layout.width &&
				mouseY >= buttonY && mouseY <= buttonY + layout.height;
	}
}