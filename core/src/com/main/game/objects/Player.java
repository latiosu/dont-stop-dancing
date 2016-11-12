package com.main.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Player extends EntityObject {

	private List<Bullet> bullets;
	private float lastAttackTime;
	private boolean[] moveDirections; // [Up, Down, Left, Right]
	private boolean[] attackDirections; // [Up, Down, Left, Right]
	private boolean isAcceptingInput;

	public Player(float x, float y, float width, float height, float hp, float maxHp, float speed, Sprite sprite) {
		super(x, y, width, height, hp, maxHp, speed, sprite);
		this.bullets = new ArrayList<>();
		this.lastAttackTime = 0;
		this.moveDirections = new boolean[4];
		this.attackDirections = new boolean[4];
		this.isAcceptingInput = true;
	}

	/**
	 * To be called every frame.
	 */
	public void update() {
		// Move player
		Direction newDirection = computeDirection(moveDirections);
		if (newDirection != null) {
			this.direction = newDirection;
			this.x += (float) (speed * Math.sin(direction.getAngle()) * Gdx.graphics.getDeltaTime());
			this.y += (float) (speed * Math.cos(direction.getAngle()) * Gdx.graphics.getDeltaTime());
		}

		// Fire projectile
		Direction attackDirection = computeDirection(attackDirections);
		if (attackDirection != null) { // Only fire if computed direction is different
			if (lastAttackTime > 0.2f) {
				bullets.add(new Bullet(x, y, 0.35f, 0.35f, 20f, null, attackDirection));
				lastAttackTime = 0f;
			}
		}
		lastAttackTime += Gdx.graphics.getDeltaTime();
	}

	public List<Bullet> getBullets() {
		return bullets;
	}

	public boolean isAcceptingInput() {
		return isAcceptingInput;
	}

	public void setAcceptingInput(boolean acceptingInput) {
		isAcceptingInput = acceptingInput;

		// Reset movement directions when disabling
		if (!isAcceptingInput) {
			moveDirections = new boolean[4];
		}
	}

	public InputAdapter movementAdapter() {
		return new MovementAdapter();
	}

	public String toString() {
		return super.toString() + String.format(", lastAttackTime: %.2f, isAcceptingInput: %s, bullets: %d",
				lastAttackTime, isAcceptingInput, bullets.size());
	}

	/**
	 * Computes the cardinal direction of a given boolean array of directions [Up, Down, Left, Right].
	 * @param directions - array of directions
	 * @return corresponding Direction enum or null if center
	 */
	private Direction computeDirection(boolean[] directions) {
		int xVel = 0;
		int yVel = 0;

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

		if (xVel == 0 && yVel == 1) {
			direction = Direction.NORTH;
		} else if (xVel == 0 && yVel == -1) {
			direction = Direction.SOUTH;
		} else if (xVel == 1 && yVel == 0) {
			direction = Direction.EAST;
		} else if (xVel == -1 && yVel == 0) {
			direction = Direction.WEST;
		} else if (xVel == 1 && yVel == 1) {
			direction = Direction.NORTHEAST;
		} else if (xVel == 1 && yVel == -1) {
			direction = Direction.SOUTHEAST;
		} else if (xVel == -1 && yVel == -1) {
			direction = Direction.SOUTHWEST;
		} else if (xVel == -1 && yVel == 1) {
			direction = Direction.NORTHWEST;
		} else {
			direction = null;
		}

		return direction;
	}

	private class MovementAdapter extends InputAdapter {
		@Override
		public boolean keyDown(int keycode) {
			if (!isAcceptingInput) {
				return false;
			}

			switch (keycode) {
				case Input.Keys.W:
					moveDirections[0] = true;
					break;
				case Input.Keys.S:
					moveDirections[1] = true;
					break;
				case Input.Keys.A:
					moveDirections[2] = true;
					break;
				case Input.Keys.D:
					moveDirections[3] = true;
					break;
				case Input.Keys.UP:
					attackDirections[0] = true;
					break;
				case Input.Keys.DOWN:
					attackDirections[1] = true;
					break;
				case Input.Keys.LEFT:
					attackDirections[2] = true;
					break;
				case Input.Keys.RIGHT:
					attackDirections[3] = true;
					break;
				default:
					return false;
			}
			return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			if (!isAcceptingInput) {
				return false;
			}

			switch (keycode) {
				case Input.Keys.W:
					moveDirections[0] = false;
					break;
				case Input.Keys.S:
					moveDirections[1] = false;
					break;
				case Input.Keys.A:
					moveDirections[2] = false;
					break;
				case Input.Keys.D:
					moveDirections[3] = false;
					break;
				case Input.Keys.UP:
					attackDirections[0] = false;
					break;
				case Input.Keys.DOWN:
					attackDirections[1] = false;
					break;
				case Input.Keys.LEFT:
					attackDirections[2] = false;
					break;
				case Input.Keys.RIGHT:
					attackDirections[3] = false;
					break;
				default:
					return false;
			}
			return true;
		}
	}
}
