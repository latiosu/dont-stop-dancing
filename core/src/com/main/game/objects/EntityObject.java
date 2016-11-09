package com.main.game.objects;

public abstract class EntityObject extends PhysicalObject {

	protected float hp, maxHp, speed;

	public EntityObject(float x, float y, float width, float height, float hp, float maxHp, float speed) {
		super(x, y, width, height);
		this.hp = hp;
		this.maxHp = maxHp;
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
