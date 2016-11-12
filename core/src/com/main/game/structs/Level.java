package com.main.game.structs;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.main.game.engine.Game;
import com.main.game.objects.Enemy;
import com.main.game.objects.Player;
import com.main.game.objects.Spawner;

import java.util.ArrayList;
import java.util.List;

public class Level {

	private int width, height;
	private Player player;
	private List<Spawner> spawners;
	private List<Enemy> enemies;
	private TiledMap map;
	private TiledMapTileLayer shrubberyLayer;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer waterLayer;

	private Level(String levelName) {
		this.spawners = new ArrayList<>();
		this.enemies = new ArrayList<>();
		this.map = new TmxMapLoader().load(levelName); // From internal file storage
		this.width = map.getProperties().get("width", Integer.class);
		this.height = map.getProperties().get("height", Integer.class);
		setupMapObjects();
	}

	public void update() {
		player.update();
	}

	private void setupMapObjects() {
		for (MapLayer l : map.getLayers()) {
			if (l.getName().equals("Shrubbery")) {

				shrubberyLayer = (TiledMapTileLayer) l;
			} else if (l.getName().equals("Ground")) {
				groundLayer = (TiledMapTileLayer) l;
			} else if (l.getName().equals("Water")) {
				waterLayer = (TiledMapTileLayer) l;
			} else if (l.getName().equals("Spawners")) {

				for (MapObject mo : l.getObjects()) {
					String type = mo.getProperties().get("type", String.class);

					if (type.equals("Spawner")) { // Generate spawner objects
						spawners.add(new Spawner(mo));
					} else if (mo.getName().equals("Player Spawn")) { // Create player at spawn location
						player = new Player(mo.getProperties().get("x", Float.class) * Game.UNIT_RATIO,
								mo.getProperties().get("y", Float.class) * Game.UNIT_RATIO,
								1f,
								1f,
								100f,
								100f,
								7.5f,
								null);
					}
				}

			}
		}
	}

	public static Level loadFromFile(String levelName) {
		return new Level(levelName);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public TiledMap getMap() {
		return map;
	}
}
