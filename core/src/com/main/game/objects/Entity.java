package com.main.game.objects;

/**
 * A living unit that can move and has health points.
 */
public abstract class Entity extends GameObject {

	protected float hp, maxHp;
	protected float speed;

	public Entity() {
		this(0, 0);
	}

	public Entity(float maxHp, float speed) {
		this(0, 0, maxHp, speed);
	}

	public Entity(float x, float y, float maxHp, float speed) {
		super(x, y);
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getHp() {
		return hp;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public float getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(float maxHp) {
		this.maxHp = maxHp;
	}
}
