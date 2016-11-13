package com.main.game.structs;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.main.game.engine.Game;
import com.main.game.engine.WorldManager;
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
	private static List<Body> colliders;

	private Level(String levelName) {
		this.spawners = new ArrayList<>();
		this.enemies = new ArrayList<>();
		this.map = new TmxMapLoader().load(levelName); // From internal file storage
		this.width = map.getProperties().get("width", Integer.class);
		this.height = map.getProperties().get("height", Integer.class);
		Level.colliders = new ArrayList<>();
		setupMapObjects();
	}

	public void update() {
		player.update();
	}

	private void setupMapObjects() {
		for (MapLayer l : map.getLayers()) {
			if (l.getName().equals("Colliders")) {
				for (MapObject mo : l.getObjects()) {
					if (mo instanceof PolylineMapObject) {
						Polyline polyline = ((PolylineMapObject) mo).getPolyline();

						BodyDef bodyDef = new BodyDef();
						bodyDef.type = BodyDef.BodyType.StaticBody;
						bodyDef.position.set(polyline.getX() * Game.UNIT_RATIO, polyline.getY() * Game.UNIT_RATIO);

						Body body = WorldManager.getWorld().createBody(bodyDef);

						PolygonShape polygon = new PolygonShape();
						float[] scaled = new float[polyline.getVertices().length];
						for (int i = 0; i < scaled.length; i++) {
							scaled[i] = Game.UNIT_RATIO * polyline.getVertices()[i];
						}

						polygon.set(scaled);

						FixtureDef fixtureDef = new FixtureDef();
						fixtureDef.shape = polygon;
						fixtureDef.density = 0f;

						body.createFixture(fixtureDef);
						polygon.dispose();
						colliders.add(body);
					} else if (mo instanceof RectangleMapObject) {
						Rectangle rect = ((RectangleMapObject) mo).getRectangle();

						BodyDef bodyDef = new BodyDef();
						bodyDef.type = BodyDef.BodyType.StaticBody;
						Vector2 position = rect.getPosition(Vector2.Zero);
						position.add(rect.width/2f, rect.height/2f).scl(Game.UNIT_RATIO);
						bodyDef.position.set(position);

						Body body = WorldManager.getWorld().createBody(bodyDef);

						PolygonShape polygon = new PolygonShape();
						polygon.setAsBox(rect.width / 2f * Game.UNIT_RATIO, rect.height / 2f * Game.UNIT_RATIO);

						FixtureDef fixtureDef = new FixtureDef();
						fixtureDef.shape = polygon;
						fixtureDef.density = 0f;

						body.createFixture(fixtureDef);
						polygon.dispose();
						colliders.add(body);
					} else if (mo instanceof PolygonMapObject) {
						Polygon polygon = ((PolygonMapObject) mo).getPolygon();

						BodyDef bodyDef = new BodyDef();
						bodyDef.type = BodyDef.BodyType.StaticBody;
						bodyDef.position.set(polygon.getX() * Game.UNIT_RATIO, polygon.getY() * Game.UNIT_RATIO);

						Body body = WorldManager.getWorld().createBody(bodyDef);

						PolygonShape shape = new PolygonShape();
						float[] scaled = new float[polygon.getVertices().length];
						for (int i = 0; i < scaled.length; i++) {
							scaled[i] = Game.UNIT_RATIO * polygon.getVertices()[i];
						}

						shape.set(scaled);

						FixtureDef fixtureDef = new FixtureDef();
						fixtureDef.shape = shape;
						fixtureDef.density = 0f;

						body.createFixture(fixtureDef);
						shape.dispose();
						colliders.add(body);
					}
				}
			} else if (l.getName().equals("Spawners")) {
				for (MapObject mo : l.getObjects()) {
					String type = mo.getProperties().get("type", String.class);

					if (type.equals("Spawner")) { // Generate spawner objects
						spawners.add(new Spawner(mo));
					} else if (mo.getName().equals("Player Spawn")) { // Create player at spawn location
						player = new Player(mo.getProperties().get("x", Float.class) * Game.UNIT_RATIO,
								mo.getProperties().get("y", Float.class) * Game.UNIT_RATIO,
								0.75f,
								0.75f,
								100f,
								100f,
								40f,
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

	public static List<Body> getColliders() {
		return colliders;
	}
}
