package com.main.game.objects;

/**
 * A living unit that can move and has health points.
 */
public abstract class PhysicalObject extends GameObject {

	protected float width, height;

	public PhysicalObject(float x, float y, float width, float height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}
