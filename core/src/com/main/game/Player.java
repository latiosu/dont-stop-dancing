package com.main.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Entity {

	private double maxHp, hp;
	private Sprite sprite;

	public Player(double maxHp, Sprite sprite) {
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.sprite = sprite;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
