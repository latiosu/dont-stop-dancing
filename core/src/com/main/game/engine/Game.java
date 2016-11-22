package com.main.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.main.game.objects.Bullet;
import com.main.game.objects.Enemy;
import com.main.game.objects.Player;
import com.main.game.structs.Level;

public class Game extends ApplicationAdapter {

	public final static float UNIT_RATIO = 1 / 16f; // 1 unit = 16 px
	private static AssetManager assets = null;

	OrthogonalTiledMapRenderer mapRenderer;
	OrthographicCamera camera;
	ShapeRenderer sr;
	SpriteBatch batch;
	Level level;
	Music bgm;

	int[] foregroundLayers, backgroundLayers;

	boolean isPlaying;
	float width, height;

	@Override
	public void create() {
		Box2D.init();

		// Load external files
		assets().load("core/assets/fox-sprites.png", Texture.class);
		assets().load("core/assets/fox-attacks.png", Texture.class);
		assets().load("core/assets/cascoon.png", Texture.class);
		assets().load("core/assets/silcoon.png", Texture.class);
		assets().load("core/assets/cyborg-ninja.mp3", Music.class);
		assets().load("core/assets/ice-flow.mp3", Music.class);
		assets().finishLoading();
		level = Level.loadFromFile("core/assets/basic.tmx");

		// Setup rendering tools
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		mapRenderer = new OrthogonalTiledMapRenderer(level.getMap(), UNIT_RATIO);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 30 * (height / width));
		camera.position.set(0, 0, 0);
		camera.update();
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		mapRenderer.setView(camera);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		// Rendering layers
		backgroundLayers = new int[]{0, 1, 2, 3, 4};
		foregroundLayers = new int[]{5};

		// Setup input handling
		InputMultiplexer multiplexer = new InputMultiplexer();
		// TODO -- Add UI input processor here
		multiplexer.addProcessor(level.getPlayer().movementAdapter());
		// TODO -- Add minigame input processor here
		Gdx.input.setInputProcessor(multiplexer);

		// Start in-game
		isPlaying = true;
		String randomBgm = Math.random() < 0.5 ? "core/assets/cyborg-ninja.mp3" : "core/assets/ice-flow.mp3";
		bgm = assets().get(randomBgm, Music.class);
		bgm.setLooping(true);
		bgm.setVolume(0.5f);
		bgm.play();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1); // Clear to white
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (isPlaying) {
			level.update();

			// Track player
			Player player = level.getPlayer();
			camera.position.set(player.getPosition());

			// Adjust to map bounds
			if (camera.position.x < camera.viewportWidth / 2f) {
				camera.position.x = camera.viewportWidth / 2f;
			} else if (camera.position.x > level.getWidth() - camera.viewportWidth / 2f) {
				camera.position.x = level.getWidth() - camera.viewportWidth / 2f;
			}
			if (camera.position.y < camera.viewportHeight / 2f) {
				camera.position.y = camera.viewportHeight / 2f;
			} else if (camera.position.y > level.getHeight() - camera.viewportHeight / 2f) {
				camera.position.y = level.getHeight() - camera.viewportHeight / 2f;
			}

			camera.update();
			mapRenderer.setView(camera);

			mapRenderer.render(backgroundLayers);

			// Determine offset player position
			float correctX = 0;
			float correctY = 0;
			if (camera.position.x <= camera.viewportWidth / 2f) {
				correctX = player.getX() - camera.viewportWidth / 2f;
			} else if (camera.position.x >= level.getWidth() - camera.viewportWidth / 2f) {
				correctX = player.getX() - (level.getWidth() - camera.viewportWidth / 2f);
			}
			if (camera.position.y <= camera.viewportHeight / 2f) {
				correctY = player.getY() - camera.viewportHeight / 2f;
			} else if (camera.position.y >= level.getHeight() - camera.viewportHeight / 2f) {
				correctY = player.getY() - (level.getHeight() - camera.viewportHeight / 2f);
			}

			batch.begin();
			// === Bullets ===
			// TODO -- FIX
			for (Bullet b : level.getPlayer().getBullets()) {
				batch.draw(b.getAnimationFrame(),
						b.getBody().getPosition().x - player.getBody().getPosition().x + correctX,
						b.getBody().getPosition().y - player.getBody().getPosition().y + correctY,
						0.7f,
						0.7f);
			}

			// === Player ===
			batch.draw(player.getAnimationFrame(),
					correctX,
					correctY,
					player.getWidth(),
					player.getHeight());

			// === Enemies ===
			for (Enemy e : level.getEnemies()) {
				batch.draw(e.getTexture(),
						e.getBody().getPosition().x - player.getBody().getPosition().x + correctX,
						e.getBody().getPosition().y - player.getBody().getPosition().y + correctY,
						e.getWidth(),
						e.getHeight());
			}
			batch.end();

			mapRenderer.render(foregroundLayers);

			// === Box2D ===
//			WorldManager.getDebugRenderer().render(WorldManager.getWorld(), camera.combined);
			WorldManager.doPhysicsStep();
		}
	}

	@Override
	public void dispose() {
		mapRenderer.dispose();
		sr.dispose();
		batch.dispose();
		WorldManager.getWorld().dispose();
	}

	public static AssetManager assets() {
		if (assets == null) {
			assets = new AssetManager();
		}
		return assets;
	}
}
