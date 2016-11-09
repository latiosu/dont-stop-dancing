package com.main.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class Block extends GameObject {

	protected Rectangle bounds;
	protected Color colour;

	public Block(float x, float y, float width, float height, Color colour) {
		super(x, y);
		this.bounds = new Rectangle(x, y, width, height);
		this.colour = colour;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
}
