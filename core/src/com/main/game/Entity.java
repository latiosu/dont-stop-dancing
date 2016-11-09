package com.main.game;

public abstract class Entity {

	protected double x, y;
	protected double speed;

	public Entity() {
		this(0, 0, 0);
	}

	public Entity(double x, double y, double speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
