package com.StarForce;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hero {

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float SPEED = 200f;	// unit per second
	// static final float JUMP_VELOCITY = 1f;
	 static final float SIZE = 50f; // half a unit

	float stateTime = 0;
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	Vector2 velocity = new Vector2();
	State state = State.IDLE;
	boolean facingLeft = false;
	
	// float health = 10f;

	public Hero(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 newPos) {
		position = newPos;
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
		position.add(velocity.cpy().scl(delta)); // Adds the velocity vector scaled by delta time to position
		stateTime += delta;
	}
}