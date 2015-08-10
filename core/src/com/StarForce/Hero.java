package com.StarForce;

import static com.StarForce.B2DVars.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
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

	private float health;
	float stateTime = 0;
	Body body;
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	boolean facingLeft = false;
	private SpriteBatch spriteBatch;
    
	private TextureRegion heroIdleLeft;
	TextureRegion[] walkRightFrames;
	TextureRegion[] walkLeftFrames;
	private TextureRegion heroIdleRight;
	private TextureRegion heroFrame;
	private TextureRegion heroJumpLeft;
	private TextureRegion heroFallLeft;
	private TextureRegion heroJumpRight;
	private TextureRegion heroFallRight;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	
	private Animations anim;
	TextureAtlas atlas;
	private WorldRenderer worldrenderer;

	public Hero(Body body, WorldRenderer wr) {
		this.body = body;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.spriteBatch = new SpriteBatch();
		this.worldrenderer = wr;
		this.health = 10f;
		anim=new Animations();
		
		loadTextures();

	}
	private void loadTextures() {
		atlas=anim.getTextureAtlas(atlas, "PlayerSprite.txt");
		heroIdleLeft=anim.getTextureRegion(atlas, heroIdleLeft, "Ntransparent01");
		heroIdleRight=anim.getTextureRegion(heroIdleRight, heroIdleLeft);
		heroJumpLeft=anim.getTextureRegion(atlas, heroJumpLeft, "Ntransparent01");
		heroJumpRight=anim.getTextureRegion(heroJumpRight, heroJumpLeft);
		heroFallLeft=anim.getTextureRegion(atlas, heroFallLeft, "Ntransparent01");
		heroFallRight=anim.getTextureRegion(heroFallRight, heroFallLeft);
		
		walkLeftFrames=anim.getTextureRegions(atlas, walkLeftFrames, 5, "Ntransparent0");
		walkRightFrames=anim.getTextureRegions(atlas, walkRightFrames, walkLeftFrames, 5);
		
		walkLeftAnimation= anim.getAnimation(walkLeftAnimation, walkLeftFrames);
		walkRightAnimation=anim.getAnimation(walkRightAnimation, walkRightFrames);
	}
	
	public Body getBody() {
		return body;
	}
	
	public void disposeStuff() {
		spriteBatch.dispose();
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
		return state;
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
	
	public void takeDamage(float health) {  // Use negative value to gain health, default is damage
		this.health -= health;
	}
	
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