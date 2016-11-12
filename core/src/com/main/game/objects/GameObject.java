package com.main.game.objects;

import com.badlogic.gdx.math.Vector3;

public abstract class GameObject {

	protected Vector3 position;

	public GameObject() {
		this(0, 0);
	}

	public GameObject(float x, float y) {
		position = new Vector3(x, y, 0);
	}

	public void setPosition(float x, float y) {
		position.set(x, y, position.z);
	}

	public float getX() {
		return position.x;
	}

	public void setX(float x) {
		position.set(x, position.y, position.z);
	}

	public float getY() {
		return position.y;
	}

	public void setY(float y) {
		position.set(position.x, y, position.z);
	}

	public void translate(float x, float y) {
		position.add(x, y, 0);
	}

	public Vector3 getPosition() {
		return position;
	}

	public String toString() {
		return String.format("position: %s", position);
	}
}
