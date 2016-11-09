package com.main.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Entity {

	private Sprite sprite;

	public Player(double x, double y, double maxHp, double speed, Sprite sprite) {
		super(x, y, maxHp, speed);
		this.sprite = sprite;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
