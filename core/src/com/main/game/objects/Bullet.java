package com.main.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Bullet extends PhysicalObject {

	protected Vector3 start;
	protected float xVel, yVel, maxDistance;

	public Bullet(float x, float y, float width, float height, float speed, float maxDistance, Sprite sprite, Direction direction) {
		super(x, y, width, height, speed, sprite);
		this.start = this.position.cpy();
		this.maxDistance = maxDistance;

		// Compute xVel and yVel
		this.xVel = speed * MathUtils.sin(direction.getAngle());
		this.yVel = speed * MathUtils.cos(direction.getAngle());
	}

	/**
	 * To be called each frame.
	 */
	public void update() {
		position.x += xVel * Gdx.graphics.getDeltaTime();
		position.y += yVel * Gdx.graphics.getDeltaTime();
	}

	public boolean isRemovable() {
		return Math.abs(start.dst(position)) >= maxDistance;
	}
}
