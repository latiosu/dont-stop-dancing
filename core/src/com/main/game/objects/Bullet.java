package com.main.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.main.game.engine.WorldManager;

public class Bullet extends PhysicalObject {

	protected Vector3 start;
	protected float xVel, yVel, maxDistance;
	private boolean isAlive;

	public Bullet(float x, float y, float width, float height, float speed, float maxDistance, Texture texture, Direction direction) {
		super(x, y, width, height, speed, texture);
		this.start = this.position.cpy();
		this.maxDistance = maxDistance;
		this.isAlive = true;

		// Compute xVel and yVel
		this.xVel = speed * MathUtils.sin(direction.getAngle());
		this.yVel = speed * MathUtils.cos(direction.getAngle());

		// Register as Box2D rigid body
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(position.x + width / 2f, position.y + height / 2f);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.bullet = true;
		bodyDef.fixedRotation = true;

		body = WorldManager.getWorld().createBody(bodyDef);
		body.setUserData(this);

		CircleShape shape = new CircleShape();
		shape.setRadius(width / 2f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.2f;
		fixtureDef.isSensor = true;

		body.createFixture(fixtureDef);
		shape.dispose();

		// Start moving bullet
		body.applyLinearImpulse(xVel * Gdx.graphics.getDeltaTime(), yVel * Gdx.graphics.getDeltaTime(), width / 2f, height / 2f, true);
		position.set(body.getPosition(), 0);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean alive) {
		isAlive = alive;
	}
}
