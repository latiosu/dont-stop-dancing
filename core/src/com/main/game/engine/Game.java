package com.main.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
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

	int[] foregroundLayers, backgroundLayers;

	boolean isPlaying;
	float width, height;

	@Override
	public void create() {
		Box2D.init();

		// Load external files
		assets().load("core/assets/cascoon.png", Texture.class);
		assets().load("core/assets/spheal-down.png", Texture.class);
		assets().load("core/assets/spheal-left.png", Texture.class);
		assets().load("core/assets/spheal-right.png", Texture.class);
		assets().load("core/assets/spheal-up.png", Texture.class);
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

			// === Bullets ===
//			sr.begin(ShapeRenderer.ShapeType.Filled);
//			sr.setColor(Color.WHITE);
//			for (Bullet b : level.getPlayer().getBullets()) {
//				sr.circle(b.getX() - player.getX() + correctX, b.getY() - player.getY() + correctY, b.getWidth()/2f, 20);
//			}
//			sr.end();

			batch.begin();
			// === Player ===
			float newWidth = (player.getTexture().getWidth()-3) * UNIT_RATIO;
			float newHeight = (player.getTexture().getHeight()-3) * UNIT_RATIO;
			batch.draw(player.getTexture(),
					correctX + player.getRenderOffsetX(),
					correctY + player.getRenderOffsetY(),
					newWidth,
					newHeight);

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
			WorldManager.getDebugRenderer().render(WorldManager.getWorld(), camera.combined);
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
