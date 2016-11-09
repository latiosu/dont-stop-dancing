package com.main.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends EntityObject {

	private Sprite sprite;
	private Color colour; // Temporary until sprites are added

	public Player(float x, float y, float width, float height, float hp, float maxHp, float speed, Color colour) {
		super(x, y, width, height, hp, maxHp, speed);
		this.colour = colour;
		this.sprite = null;
	}

	public Player(float x, float y, float width, float height, float hp, float maxHp, float speed, Sprite sprite) {
		super(x, y, width, height, hp, maxHp, speed);
		this.sprite = sprite;
		this.colour = null;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
}
