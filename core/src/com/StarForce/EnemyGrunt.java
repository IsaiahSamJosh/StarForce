package com.StarForce;

import static com.StarForce.B2DVars.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class EnemyGrunt {

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
    
	private Textures tx;
	private TextureAtlas atlas;
	private TextureRegion heroFrame;
	private TextureRegion heroIdleRight;
	private TextureRegion heroIdleLeft;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	private WorldRenderer worldrenderer;

	public EnemyGrunt(Body body, WorldRenderer wr) {
		this.body = body;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.spriteBatch = new SpriteBatch();
		this.tx=new Textures();
		this.worldrenderer = wr;
		this.health = 5f;
		
		loadTextures();
	}
	private boolean isFacingLeft() {
		return facingLeft;
	}
	public State getState() {
		return this.state;
	}
	private void loadTextures() {
		tx.loadTextureAtlas();
		tx.loadTextures();
		tx.loadTextureRegions();
		tx.loadAnimation();
	}
	public void update() {
		spriteBatch.setProjectionMatrix(worldrenderer.getCam().combined);
		spriteBatch.begin();
			drawEnemyGrunt();
		spriteBatch.end();
	}
	public Vector2 getPosition() {
		return body.getPosition();
	}
	private void drawEnemyGrunt() {
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
		spriteBatch.draw(heroFrame, getPosition().x*PPM - EnemyGrunt.SIZE/2, getPosition().y*PPM - EnemyGrunt.SIZE/2, EnemyGrunt.SIZE, EnemyGrunt.SIZE);
	}
}