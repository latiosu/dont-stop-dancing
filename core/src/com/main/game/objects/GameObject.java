package com.main.game.objects;

public abstract class GameObject {

	protected float x, y;

	public GameObject() {
		this(0, 0);
	}

	public GameObject(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
