package com.main.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;

public class Player extends EntityObject {

//	private Sprite sprite;
	private Color colour; // Temporary until sprites are added

	private boolean[] directions; // [Up, Down, Left, Right]
	private boolean isAcceptingInput;

	public Player(float x, float y, float width, float height, float hp, float maxHp, float speed, Color colour) {
		super(x, y, width, height, hp, maxHp, speed);
		this.colour = colour;
		this.directions = new boolean[4];
		this.isAcceptingInput = true;
	}

	/**
	 * To be called every frame.
	 */
	public void update() {
		float xVel = 0;
		float yVel = 0;

		if (directions[0]) {
			yVel += 1;
		}
		if (directions[1]) {
			yVel -= 1;
		}
		if (directions[2]) {
			xVel -= 1;
		}
		if (directions[3]) {
			xVel += 1;
		}

		setPosition(x + (xVel * speed) * Gdx.graphics.getDeltaTime(), y + (yVel * speed) * Gdx.graphics.getDeltaTime());
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public boolean isAcceptingInput() {
		return isAcceptingInput;
	}

	public void setAcceptingInput(boolean acceptingInput) {
		isAcceptingInput = acceptingInput;

		// Reset movement directions when disabling
		if (!isAcceptingInput) {
			directions = new boolean[4];
		}
	}

	public InputAdapter movementAdapter() {
		return new MovementAdapter();
	}

	public String toString() {
		return String.format("x: %.2f, y: %.2f, width: %.2f, height : %.2f, hp: %.2f, maxHp: %.2f, speed: %.2f, colour: %s",
				x, y, width, height, hp, maxHp, speed, colour);
	}

	private class MovementAdapter extends InputAdapter {
		@Override
		public boolean keyDown(int keycode) {
			if (!isAcceptingInput) {
				return false;
			}

			if (keycode == Input.Keys.UP) {
				directions[0] = true;
			} else if (keycode == Input.Keys.DOWN) {
				directions[1] = true;
			} else if (keycode == Input.Keys.LEFT) {
				directions[2] = true;
			} else if (keycode == Input.Keys.RIGHT) {
				directions[3] = true;
			}
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			if (!isAcceptingInput) {
				return false;
			}

			if (keycode == Input.Keys.UP) {
				directions[0] = false;
			} else if (keycode == Input.Keys.DOWN) {
				directions[1] = false;
			} else if (keycode == Input.Keys.LEFT) {
				directions[2] = false;
			} else if (keycode == Input.Keys.RIGHT) {
				directions[3] = false;
			}
			return false;
		}
	}
}
