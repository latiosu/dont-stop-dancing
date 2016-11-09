package com.main.game;

public abstract class GameObject {

	protected double x, y;

	public GameObject() {
		this(0, 0);
	}

	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
