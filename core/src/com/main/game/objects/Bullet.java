package com.main.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet extends PhysicalObject {

	protected float xVel, yVel;

	public Bullet(float x, float y, float width, float height, float speed, Sprite sprite, Direction direction) {
		super(x, y, width, height, speed, sprite);

		// Compute xVel and yVel
		this.xVel = (float)(speed * Math.sin(direction.getAngle()));
		this.yVel = (float)(speed * Math.cos(direction.getAngle()));
	}

	/**
	 * To be called each frame.
	 */
	public void update() {
		x += xVel * Gdx.graphics.getDeltaTime();
		y += yVel * Gdx.graphics.getDeltaTime();
	}
}
