package com.main.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends PhysicalObject {

	protected float xVel, yVel;

	public Bullet(float x, float y, float width, float height, float speed, Sprite sprite, Direction direction) {
		super(x, y, width, height, speed, sprite);

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
}
