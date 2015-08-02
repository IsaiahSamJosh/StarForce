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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldRenderer {
	private TiledMap map;
	float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    
	private OrthogonalTiledMapRenderer renderer2;
	private OrthographicCamera cam;
	private StarForce game;
	private Textures tx;
	private Hero hero;
	private World world;
	private Box2DDebugRenderer b2dr;
	private TextureRegion heroFrame;
	private TextureRegion heroIdleRight;
	private TextureRegion heroIdleLeft;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	private TextureAtlas atlas;
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
	public WorldRenderer(StarForce game) {//first param was World world not Level level!
		this.world = new World(new Vector2(0f, 0f), true);
		this.b2dr = new Box2DDebugRenderer();
		this.tx=new Textures();
		TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("Level1.tmx");
        renderer2 = new OrthogonalTiledMapRenderer(map);
		this.cam = new OrthographicCamera(w,h);
		cam.setToOrtho(false, w,h);
		this.game = game;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		// create player
		bdef.position.set(30,20);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
		shape.setAsBox(Hero.SIZE/2f, Hero.SIZE/2f);
		fdef.shape = shape;
		body.createFixture(fdef);
		
		this.hero = new Hero(body);
		spriteBatch = new SpriteBatch();
		
		loadTextures();
	}
	
	public Hero getHero() {
		return hero;
	}
	
	private void loadTextures() {
		tx.loadTextureAtlas();
		tx.loadTextures();
		tx.loadTextureRegions();
		tx.loadAnimation();
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
		b2dr.render(world, cam.combined);
		world.step(1/45f, 6, 2);
	}
	private void drawHero() {
		heroFrame=tx.getHeroFrame();
		heroIdleRight=tx.getheroIdleRight();
		heroIdleLeft=tx.getheroIdleLeft();
		walkLeftAnimation=tx.getwalkLeftAnimation();
		walkRightAnimation=tx.getwalkRightAnimation();
		atlas=tx.getAtlas();
		heroFrame = hero.isFacingLeft() ? heroIdleLeft : heroIdleRight;
		if(hero.getState().equals(State.WALKING)) {
			heroFrame = hero.isFacingLeft() ? walkLeftAnimation.getKeyFrame(hero.getStateTime(), true) : walkRightAnimation.getKeyFrame(hero.getStateTime(), true);
		} //else if (hero.getState().equals(State.JUMPING)) {
			//if (hero.getVelocity().y > 0) {
				//heroFrame = hero.isFacingLeft() ? heroJumpLeft : heroJumpRight;
			//} else {
				//heroFrame = hero.isFacingLeft() ? heroFallLeft : heroFallRight;
			//}
		//}
		spriteBatch.draw(heroFrame, hero.getPosition().x - hero.SIZE/2, hero.getPosition().y - hero.SIZE/2, Hero.SIZE, Hero.SIZE);
	}
	public void dispose(){
		spriteBatch.dispose();
		atlas.dispose();
		map.dispose();
	}
}
