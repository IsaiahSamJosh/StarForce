package com.StarForce;


import com.StarForce.Hero.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldRenderer {
	private TiledMap map;
	float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    
	private OrthogonalTiledMapRenderer renderer2;
	private OrthographicCamera cam;
	private float PPM = 100f;
	private OrthographicCamera physicsCam;
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
	private Body playerBody;
	private MyContactListener cl;
	
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
		this.world = new World(new Vector2(0f, -10f), true);
		cl=new MyContactListener();
		world.setContactListener(cl);
		this.b2dr = new Box2DDebugRenderer();
		this.tx=new Textures();
		TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("Level1.tmx");
        renderer2 = new OrthogonalTiledMapRenderer(map);
		this.cam = new OrthographicCamera();
		cam.setToOrtho(false, w,h);
		this.physicsCam = new OrthographicCamera();
		physicsCam.setToOrtho(false, w/PPM, h/PPM);
		this.game = game;
		Gdx.input.setInputProcessor(new PlayScreen(game));
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		// create player
		bdef.position.set(600/PPM,900/PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody= world.createBody(bdef);
		shape.setAsBox(Hero.SIZE/PPM/2f, Hero.SIZE/PPM/2f);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits =B2DVars.BIT_GROUND;
		fdef.isSensor=false;
		playerBody.createFixture(fdef).setUserData("player");
		
		//create foot
		
		shape.setAsBox(4/PPM, 4/PPM, new Vector2(0/PPM,-24/PPM), 0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits =B2DVars.BIT_GROUND;
		fdef.isSensor=true;
		playerBody.createFixture(fdef).setUserData("foot");
		
		
		this.hero = new Hero(playerBody);
		spriteBatch = new SpriteBatch();
		
		loadTextures();
		createTiles();
	}
	private void createTiles() {
		TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("Level1.tmx");
        renderer2 = new OrthogonalTiledMapRenderer(map);
		
		// load tile map
		Integer tileSize = (Integer) (map.getProperties().get("tilewidth"));
		
		TiledMapTileLayer layer;
		
		layer = (TiledMapTileLayer) map.getLayers().get("Ground");
		System.out.println(map.getLayers().get("Ground"));
		createLayer(layer, tileSize);
		
		
	}
	
	private void createLayer(TiledMapTileLayer layer, Integer tileSize) {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		// go through all the cells in the layer
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				
				// get cell
				Cell cell = layer.getCell(col, row);
				
				// check if cell exists
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				// create a body + fixture from cell
				bdef.type = BodyType.StaticBody;
				bdef.position.set(
					(col + 0.5f) * tileSize/PPM,
					(row + 0.5f) * tileSize/PPM
				);
				
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(
					-tileSize / PPM / 2, -tileSize / PPM / 2);
				v[1] = new Vector2(
					-tileSize / PPM / 2, tileSize / PPM / 2);
				v[2] = new Vector2(
					tileSize / PPM / 2, tileSize / PPM / 2);
				cs.createChain(v);
				fdef.friction = 1f;
				fdef.shape = cs;
				fdef.filter.categoryBits = B2DVars.BIT_GROUND;
				fdef.filter.maskBits = B2DVars.BIT_PLAYER;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef).setUserData("Ground");
				
				
			}
		}
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
		this.physicsCam.position.set(hero.getPosition().x, hero.getPosition().y, 0);
		this.physicsCam.update();
		this.cam.position.set(hero.getPosition().x * PPM, hero.getPosition().y * PPM, 0);
		this.cam.update();
		renderer2.setView(cam);
		renderer2.render();
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
			drawHero();
		spriteBatch.end();
		b2dr.render(world, physicsCam.combined);
		world.step(1/60f, 6, 2);
		MyInput.update();
		//System.out.println(this.cam.position);
		//System.out.println(this.physicsCam.position);
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
		spriteBatch.draw(heroFrame, hero.getPosition().x*PPM - hero.SIZE/2, hero.getPosition().y*PPM - hero.SIZE/2, Hero.SIZE, Hero.SIZE);
	}
	public void dispose(){
		spriteBatch.dispose();
		atlas.dispose();
		map.dispose();
	}
	public void handleInput() {
		if (MyInput.isPressed(MyInput.BUTTON1)) {
			if (cl.isPlayerOnGround()) {
			playerBody.applyForceToCenter(0, 200, true);	
			}
		}
	}
	
	public void update(float delta) {
	handleInput();
	}
}
