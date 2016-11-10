package com.main.game.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.main.game.objects.Block;
import com.main.game.objects.Bullet;
import com.main.game.objects.Player;
import com.main.game.structs.Level;

public class Game extends ApplicationAdapter {

	OrthographicCamera camera;
	ShapeRenderer sr;
	SpriteBatch batch;
	Level level;
	Player player;

	@Override
	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/35f, Gdx.graphics.getHeight()/35f);
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		level = new Level(null);
		player = new Player(0, 0, 1.5f, 1.5f, 100, 100, 10, null);
		InputMultiplexer multiplexer = new InputMultiplexer();
		// TODO -- Add UI input processor here
		multiplexer.addProcessor(player.movementAdapter());
		// TODO -- Add minigame input processor here
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render () {
		player.update();
		camera.update();

		Gdx.gl.glClearColor(1, 1, 1, 1); // Clear to white
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sr.setProjectionMatrix(camera.combined);

		sr.begin(ShapeRenderer.ShapeType.Filled); // === Shapes ===
		sr.setColor(Color.ORANGE);
		for (Block b : level.getBlocks()) {
			sr.rect(b.getRenderX(), b.getRenderY(), b.getWidth(), b.getHeight());
		}
		sr.end();

		sr.begin(ShapeRenderer.ShapeType.Filled); // === Player ===
		sr.setColor(Color.RED);
		sr.rect(player.getRenderX(), player.getRenderY(), player.getWidth(), player.getHeight());
		sr.end();

		sr.begin(ShapeRenderer.ShapeType.Line); // === Bullets ===
		sr.setColor(Color.RED);
		for (Bullet b : player.getBullets()) {
			b.update();
			sr.circle(b.getRenderX(), b.getRenderY(), b.getWidth(), 10);
		}
		sr.end();
	}
	
	@Override
	public void dispose () {
		sr.dispose();
		batch.dispose();
	}
}
