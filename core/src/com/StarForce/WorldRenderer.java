package com.StarForce;


import com.StarForce.Hero.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class WorldRenderer {
	private TiledMap map;
	float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
	private OrthogonalTiledMapRenderer renderer2;
	private OrthographicCamera cam;
	private static final float RUNNING_FRAME_DURATION = 0.06f;
	private Level level;
	private StarForce game;
	TextureAtlas atlas;
	private Hero hero;
	private TextureRegion heroIdleLeft;
	private TextureRegion heroIdleRight;
	private TextureRegion heroFrame;
	private TextureRegion heroJumpLeft;
	private TextureRegion heroFallLeft;
	private TextureRegion heroJumpRight;
	private TextureRegion heroFallRight;
	
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	
	private SpriteBatch spriteBatch;
	
	private float ppuX;	
	private float ppuY;
	
	private int width;
	private int height;
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / w;
		ppuY = (float)height / h;
		//resizes the screen based on the width and the height of the level
	}
	public WorldRenderer(Level level, StarForce game) {//first param was World world not Level level!
		this.level = level; 
		this.hero = level.getHero();
		TmxMapLoader loader = new TmxMapLoader();
         map = loader.load("Level1.tmx");
         renderer2 = new OrthogonalTiledMapRenderer(map);
		this.cam = new OrthographicCamera(w,h);
		cam.setToOrtho(false, w,h);
		this.game = game;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}
	private void loadTextures() {
		atlas = new TextureAtlas(Gdx.files.internal("PlayerSprite.txt")); //internal is read only!
		heroIdleLeft = atlas.findRegion("Ntransparent01");
		heroIdleRight = new TextureRegion(heroIdleLeft);
		heroIdleRight.flip(true, false);
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("Ntransparent0" + (i + 2));
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false); //first param flips x, second param flips y
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		heroJumpLeft = atlas.findRegion("Ntransparent01");
		heroJumpRight = new TextureRegion(heroJumpLeft);
		heroJumpRight.flip(true, false);
		heroFallLeft = atlas.findRegion("Ntransparent01");
		heroFallRight = new TextureRegion(heroFallLeft);
		heroFallRight.flip(true, false); //first param flips x, second param flips y
	}
	public void render() {
		this.cam.position.set(hero.getPosition().x, hero.getPosition().y+150,0);
		this.cam.update();
		renderer2.setView(cam);
		renderer2.render();
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
			drawHero();
		spriteBatch.end();
	}
	private void drawHero() {
		Hero hero = level.getHero(); //was level.getBob();
		heroFrame = hero.isFacingLeft() ? heroIdleLeft : heroIdleRight;
		if(hero.getState().equals(State.WALKING)) {
			heroFrame = hero.isFacingLeft() ? walkLeftAnimation.getKeyFrame(hero.getStateTime(), true) : walkRightAnimation.getKeyFrame(hero.getStateTime(), true);
		} else if (hero.getState().equals(State.JUMPING)) {
			if (hero.getVelocity().y > 0) {
				heroFrame = hero.isFacingLeft() ? heroJumpLeft : heroJumpRight;
			} else {
				heroFrame = hero.isFacingLeft() ? heroFallLeft : heroFallRight;
			}
		}
		spriteBatch.draw(heroFrame, hero.getPosition().x, hero.getPosition().y, Hero.SIZE, Hero.SIZE);
	}
	public void dispose(){
		spriteBatch.dispose();
		atlas.dispose();
		map.dispose();
	}
}