package com.main.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class WorldManager {

	private final static float TIME_STEP = 1/45f;
	private final static int VELOCITY_ITERATIONS = 6;
	private final static int POSITION_ITERATIONS = 2;
	private static Box2DDebugRenderer debugRenderer = null;
	private static World world = null;
	private static float accumulator = 0;

	public static World getWorld() {
		if (world == null) {
			world = new World(new Vector2(0, 0), true);
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
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		float frameTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
		accumulator += frameTime;
		while (accumulator >= TIME_STEP) {
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;
		}
	}

}
