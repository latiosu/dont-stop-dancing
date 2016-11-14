package com.main.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.main.game.engine.Game;
import com.main.game.structs.Level;

public class Spawner extends GameObject {

	private static final float SPAWN_THRESHOLD = 1f;
	private Level level;
	private float lastSpawnTime;

	public Spawner(MapObject mapObject, Level level) {
		super(mapObject.getProperties().get("x", Float.class) * Game.UNIT_RATIO,
				mapObject.getProperties().get("y", Float.class) * Game.UNIT_RATIO);
		this.lastSpawnTime = 0f;
		this.level = level;
	}

	public void update() {
		if (lastSpawnTime > SPAWN_THRESHOLD) {
			// Spawn an enemy
			level.getEnemies().add(spawnEnemy());
			lastSpawnTime -= SPAWN_THRESHOLD;
		}
		lastSpawnTime += Gdx.graphics.getDeltaTime();
	}

	public Enemy spawnEnemy() {
		return new Enemy(position.x, position.y, 0.75f, 0.75f, 100f, 100f, 5f, null, level.getPlayer());
	}
}
