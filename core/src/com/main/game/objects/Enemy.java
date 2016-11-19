package com.main.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.main.game.engine.WorldManager;

public class Enemy extends EntityObject {

	private Player player;
	private boolean isAlert;

	public Enemy(float x, float y, float width, float height, float hp, float maxHp, float speed, Texture texture, Player player) {
		super(x, y, width, height, hp, maxHp, speed, texture);
		this.player = player;
		this.isAlert = false;

		// Register as Box2D rigid body
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(position.x + width / 2f, position.y + height / 2f);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true;

		body = WorldManager.getWorld().createBody(bodyDef);
		body.setUserData(this);

		CircleShape shape = new CircleShape();
		shape.setRadius(width / 2f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;

		body.createFixture(fixtureDef);
		shape.dispose();
	}

	public void update() {
		if (hp <= 0) {
			WorldManager.getToRemove().add(body);
			return;
		}

		// Move towards player
		float dirX = player.getX() - body.getPosition().x;
		float dirY = player.getY() - body.getPosition().y;
		Vector2 targetDirection = new Vector2(dirX, dirY).nor().scl(speed);

		body.setLinearVelocity(targetDirection);
		position = new Vector3(body.getPosition().cpy().sub(width / 2f, height / 2f), 0);
	}
}
