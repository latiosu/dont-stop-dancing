package com.main.game;

/**
 * A living unit that can move and has health points.
 */
public abstract class Entity extends GameObject {

	protected double hp, maxHp;
	protected double speed;

	public Entity() {
		this(0, 0);
	}

	public Entity(double maxHp, double speed) {
		this(0, 0, maxHp, speed);
	}

	public Entity(double x, double y, double maxHp, double speed) {
		super(x, y);
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}
}
