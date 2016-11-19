package com.main.game.objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class EntityObject extends PhysicalObject {

	protected float hp, maxHp;

	public EntityObject(float x, float y, float width, float height, float hp, float maxHp, float speed, Texture texture) {
		super(x, y, width, height, speed, texture);
		this.hp = hp;
		this.maxHp = maxHp;
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

	public boolean isAlive() {
		return hp > 0;
	}

	public String toString() {
		return super.toString() + String.format(", hp: %.2f, maxHp: %.2f", hp, maxHp);
	}
}
