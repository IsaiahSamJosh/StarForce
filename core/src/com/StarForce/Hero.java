package com.StarForce;

import com.badlogic.gdx.graphics.g2d.Animation;
import static com.StarForce.B2DVars.PPM;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Hero {

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}
	 static final float SIZE = 50f; // half a unit

	float stateTime = 0;
	Body body;
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	boolean facingLeft = false;
	private SpriteBatch spriteBatch;
    private StarForce game;
    
	private Textures tx;
	private TextureAtlas atlas;
	private TextureRegion heroFrame;
	private TextureRegion heroIdleRight;
	private TextureRegion heroIdleLeft;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	private WorldRenderer worldrenderer;
	
	// float health = 10f;

	public Hero(Body body, WorldRenderer wr) {
		this.body = body;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.spriteBatch = new SpriteBatch();
		this.tx=new Textures();
		this.worldrenderer = wr;
		
		loadTextures();

	}
	private void loadTextures() {
		tx.loadTextureAtlas();
		tx.loadTextures();
		tx.loadTextureRegions();
		tx.loadAnimation();
	}
	
	public Body getBody() {
		return body;
	}
	
	public void disposeStuff() {
		spriteBatch.dispose();
		atlas.dispose();
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
		stateTime += delta;
	}
	public void update() {
		spriteBatch.setProjectionMatrix(worldrenderer.getCam().combined);
		spriteBatch.begin();
			drawHero();
		spriteBatch.end();
	}
	private void drawHero() {
		heroFrame=tx.getHeroFrame();
		heroIdleRight=tx.getheroIdleRight();
		heroIdleLeft=tx.getheroIdleLeft();
		walkLeftAnimation=tx.getwalkLeftAnimation();
		walkRightAnimation=tx.getwalkRightAnimation();
		atlas=tx.getAtlas();
		heroFrame = isFacingLeft() ? heroIdleLeft : heroIdleRight;
		if(getState().equals(State.WALKING)) {
			heroFrame = isFacingLeft() ? walkLeftAnimation.getKeyFrame(stateTime, true) : walkRightAnimation.getKeyFrame(stateTime, true);
		} //else if (hero.getState().equals(State.JUMPING)) {
			//if (hero.getVelocity().y > 0) {
				//heroFrame = hero.isFacingLeft() ? heroJumpLeft : heroJumpRight;
			//} else {
				//heroFrame = hero.isFacingLeft() ? heroFallLeft : heroFallRight;
			//}
		//}
		spriteBatch.draw(heroFrame, getPosition().x*PPM - Hero.SIZE/2, getPosition().y*PPM - Hero.SIZE/2, Hero.SIZE, Hero.SIZE);
	}
}