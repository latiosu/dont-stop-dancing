package com.main.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.main.game.objects.Bullet;
import com.main.game.objects.Enemy;
import com.main.game.objects.Player;

import java.util.HashSet;
import java.util.Set;

public class WorldManager {

	private final static float TIME_STEP = 1 / 45f;
	private final static int VELOCITY_ITERATIONS = 6;
	private final static int POSITION_ITERATIONS = 2;
	private static Set<Body> toRemove;
	private static Box2DDebugRenderer debugRenderer = null;
	private static World world = null;
	private static float accumulator = 0;

	public static World getWorld() {
		if (world == null) {
			world = new World(new Vector2(0, 0), true);
			world.setContactListener(new CollisionListener());
		}
		return world;
	}

	public static Box2DDebugRenderer getDebugRenderer() {
		if (debugRenderer == null) {
			debugRenderer = new Box2DDebugRenderer();
		}
		return debugRenderer;
	}

	public static void doPhysicsStep() {
		toRemove = new HashSet<>();
		// Fixed time step
		// Max frame time to avoid spiral of death (on slow devices)
		float frameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
		accumulator += frameTime;
		while (accumulator >= TIME_STEP) {
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;
		}
		cleanUp();
	}

	private static void cleanUp() {
		for (Body b : toRemove) {
			world.destroyBody(b);
		}
	}

	public static class CollisionListener implements ContactListener {
		@Override
		public void beginContact(Contact contact) {
			Object dataA = contact.getFixtureA().getBody().getUserData();
			Object dataB = contact.getFixtureB().getBody().getUserData();

			if (dataA instanceof Bullet) {
				if (dataB instanceof Enemy) { // Bullet - Enemy
					// Destroy bullet
					toRemove.add(contact.getFixtureA().getBody());

					// TODO -- Damage enemy

				} else if (dataB instanceof String && dataB.equals("Wall")) { // Bullet - Wall

					// Destroy bullet
					toRemove.add(contact.getFixtureA().getBody());
				}
			} else if (dataA instanceof Enemy) {
				if (dataB instanceof Bullet) { // Enemy - Bullet
					// Destroy bullet
					toRemove.add(contact.getFixtureA().getBody());

					// TODO -- Damage enemy

				} else if (dataB instanceof Player) { // Enemy - Player
					// TODO -- Trigger minigame

				}
			} else if (dataA instanceof String && dataA.equals("Wall")) {

				if (dataB instanceof Bullet) { // Wall - Bullet
					toRemove.add(contact.getFixtureB().getBody());
				} else if (dataB instanceof Player) { // Wall - Player
					// TODO -- Play SFX
				}

			} else if (dataA instanceof Player) {
				if (dataB instanceof Enemy) { // Player - Enemy
					// TODO -- Trigger minigame

				} else if (dataB instanceof String && dataB.equals("Wall")) { // Player - Wall
					// TODO -- Trigger SFX
				}
			}

		}

		@Override
		public void endContact(Contact contact) {
			// Do nothing
		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {
			// Do nothing
		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
			// Do nothing
		}
	}
}
