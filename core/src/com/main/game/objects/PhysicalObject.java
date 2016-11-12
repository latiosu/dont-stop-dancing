package com.main.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * A living unit that has a size, position and health points.
 */
public abstract class PhysicalObject extends GameObject {

	public enum Direction {

		NORTH(0), NORTHEAST(1), EAST(2), SOUTHEAST(3), SOUTH(4), SOUTHWEST(5), WEST(6), NORTHWEST(7);

		private int value;
		private float angle;

		Direction(int value) {
			this.value = value;
			this.angle = (float) (value * Math.PI / 4f);
		}

		public int getValue() {
			return value;
		}

		public float getAngle() {
			return angle;
		}
	}

	protected Direction direction;
	protected Sprite sprite;
	protected float width, height, speed;

	public PhysicalObject(float x, float y, float width, float height, float speed, Sprite sprite) {
		this(x, y, width, height, speed, sprite, null);
	}

	public PhysicalObject(float x, float y, float width, float height, float speed, Sprite sprite, Direction direction) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.sprite = sprite;
		this.direction = direction;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getSpeed() {
		return speed;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Direction getDirection() {
		return direction;
	}

	public float getRenderX() {
		return position.x - width/2f;
	}

	public float getRenderY() {
		return position.y - height/2f;
	}

	public Rectangle getBounds() {
		return new Rectangle(getRenderX(), getRenderY(), width, height);
	}

	public String toString() {
		return super.toString() + String.format(", width: %.2f, height: %.2f, speed: %.2f, sprite: %s, direction: %s",
				width, height, speed, sprite, direction);
	}
}
