package com.main.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.main.game.objects.Bullet;
import com.main.game.objects.Enemy;
import com.main.game.structs.Level;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {

	public final static float UNIT_RATIO = 1/16f; // 1 unit = 16 px

	OrthogonalTiledMapRenderer mapRenderer;
	OrthographicCamera camera;
	ShapeRenderer sr;
	SpriteBatch batch;
	Level level;

	int[] foregroundLayers, backgroundLayers;

	boolean isPlaying;
	float width, height;

	@Override
	public void create () {
		Box2D.init();

		// Load external files
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
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1); // Clear to white
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (isPlaying) {
			level.update();

			// Track player with camera
			camera.position.set(level.getPlayer().getPosition());
			camera.update();
			mapRenderer.setView(camera);

			mapRenderer.render(backgroundLayers);

			// === Bullets ===
			sr.begin(ShapeRenderer.ShapeType.Filled);
			sr.setColor(Color.ORANGE);
			List<Bullet> toRemove = new ArrayList<>(level.getPlayer().getBullets().size());
			for (Bullet b : level.getPlayer().getBullets()) {

//				b.update();

			}
			sr.end();
			level.getPlayer().getBullets().removeAll(toRemove); // Clean up bullets

			// === Player ===
			sr.begin(ShapeRenderer.ShapeType.Filled);
			sr.setColor(Color.WHITE);
			sr.rect(0, 0, level.getPlayer().getWidth(), level.getPlayer().getHeight());
			sr.end();

			// === Enemies ===
			sr.begin(ShapeRenderer.ShapeType.Filled);
			sr.setColor(Color.ORANGE);
			for (Enemy e : level.getEnemies()) {
				e.update();
				sr.circle(e.getRenderX(), e.getRenderY(), e.getWidth(), 10);
			}
			sr.end();

			mapRenderer.render(foregroundLayers);

			// === Box2D ===
			WorldManager.getDebugRenderer().render(WorldManager.getWorld(), camera.combined);
			WorldManager.doPhysicsStep();
		}
	}
	
	@Override
	public void dispose () {
		mapRenderer.dispose();
		sr.dispose();
		batch.dispose();
		WorldManager.getWorld().dispose();
	}
}
