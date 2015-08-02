package com.StarForce;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Hero {

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float SPEED = 200f;	// unit per second
	// static final float JUMP_VELOCITY = 1f;
	 static final float SIZE = 50f; // half a unit

	float stateTime = 0;
	Body body;
	Rectangle bounds = new Rectangle();
	Vector2 velocity = new Vector2();
	State state = State.IDLE;
	boolean facingLeft = false;
	
	// float health = 10f;

	public Hero(Body body) {
		this.body = body;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public Body getBody() {
		return body;
	}
	
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2 v) {
		velocity = v;
	}
	
	public State getState() {
		return this.state;
	}
	
	public void setState(State s) {
		state = s;
	}
	
	public boolean isFacingLeft() {
		return facingLeft;
	}
	
	public void setFacingLeft(boolean isLeft) {
		facingLeft = isLeft;
	}
	
	//public void takeDamage(float health) {  // Use negative value to gain health, default is damage
	//	this.health -= health;
	//}
	
	public void update(float delta) {
		//position.add(velocity.cpy().scl(delta)); // Adds the velocity vector scaled by delta time to position
		stateTime += delta;
	}
}