package com.main.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

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
	protected Animation[] animations;
	protected TextureRegion currentFrame;
	protected Texture texture;
	protected Body body;
	protected float width, height, speed, stateTime;

	public PhysicalObject(float x, float y, float width, float height, float speed, Texture texture) {
		this(x, y, width, height, speed, texture, null);
	}

	public PhysicalObject(float x, float y, float width, float height, float speed, Texture texture, Direction direction) {
		super(x, y);
		this.body = null;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.texture = texture;
		this.direction = direction;
		this.animations = null;
		this.currentFrame = null;
		this.stateTime = 0f;
	}

	public abstract TextureRegion getAnimationFrame();

	public Body getBody() {
		return body;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public Vector2 getDimensions() {
		return new Vector2(width, height);
	}

	public float getSpeed() {
		return speed;
	}

	public Texture getTexture() {
		return texture;
	}

	public Direction getDirection() {
		return direction;
	}

	public float getRenderX() {
		return position.x - width / 2f;
	}

	public float getRenderY() {
		return position.y - height / 2f;
	}

	public Rectangle getBounds() {
		return new Rectangle(getRenderX(), getRenderY(), width, height);
	}

	public String toString() {
		return super.toString() + String.format(", width: %.2f, height: %.2f, speed: %.2f, texture: %s, direction: %s",
				width, height, speed, texture, direction);
	}
}
