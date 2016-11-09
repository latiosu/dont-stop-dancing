package com.main.game;

public class Player {

	protected double x, y;
	protected double speed;

	public Player() {
		this(0, 0, 0);
	}

	public Player(double x, double y, double speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void move() {
		// TODO -- Make it work nicely with key events
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
