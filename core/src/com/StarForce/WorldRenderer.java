package com.StarForce;


import static com.StarForce.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	// Constants
	float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
	// Tiled stuff
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer2;
	// Camera stuff
	private OrthographicCamera cam;
	private OrthographicCamera physicsCam;
	// Miscellaneous
	private StarForce game;
	private Hero hero;
	private EnemyGrunt testEnemy;
	// Physics stuff
	private World world;
	private Box2DDebugRenderer b2dr;
	private Body playerBody;
	private Body enemyBody;
	private MyContactListener cl;
	private BodyDef bdef;
	private FixtureDef fdef;
	private PolygonShape shape;
	
	public WorldRenderer(StarForce game) {//first param was World world not Level level!
		// Physics
		this.world = new World(new Vector2(0f, -10f), true);
		cl=new MyContactListener();
		world.setContactListener(cl);
		this.b2dr = new Box2DDebugRenderer();
		// Tiled map
		TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("Level1.tmx");
        renderer2 = new OrthogonalTiledMapRenderer(map);
        // Cameras
		this.cam = new OrthographicCamera();
		cam.setToOrtho(false, w,h);
		this.physicsCam = new OrthographicCamera();
		physicsCam.setToOrtho(false, w/PPM, h/PPM);
		
		this.setGame(game);
		Gdx.input.setInputProcessor(new PlayScreen(game));
		
		// Physics definitions
		bdef = new BodyDef();
		fdef = new FixtureDef();
		shape = new PolygonShape();
		
		createEntities();
		createTiles();
	}
	
	public void createEntities() {
		// Player first
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
		shape.setAsBox(23/PPM, 4/PPM, new Vector2(0/PPM,-24/PPM), 0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits =B2DVars.BIT_GROUND;
		fdef.isSensor=true;
		playerBody.createFixture(fdef).setUserData("foot");
		// Create player
		this.hero = new Hero(playerBody, this);
		
		// Test enemy second
		bdef.position.set(600/PPM + 30/PPM, 900/PPM);
		bdef.type = BodyType.DynamicBody;
		enemyBody= world.createBody(bdef);
		shape.setAsBox(EnemyGrunt.SIZE/PPM/2f, EnemyGrunt.SIZE/PPM/2f);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits =B2DVars.BIT_GROUND;
		fdef.isSensor=false;
		enemyBody.createFixture(fdef).setUserData("enemy");
		this.testEnemy = new EnemyGrunt(enemyBody, this);
	}
	public OrthographicCamera getCam() {
		return cam;
	}
	private void createTiles() {
		TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("Level1.tmx");
        renderer2 = new OrthogonalTiledMapRenderer(map);
		
		// load tile map
		Integer tileSize = (Integer) (map.getProperties().get("tilewidth"));
		TiledMapTileLayer layer;
		
		layer = (TiledMapTileLayer) map.getLayers().get("Ground");
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
				fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_ENEMY;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef).setUserData("Ground");

			}
		}
	}
	
	public Hero getHero() {
		return hero;
	}
	
	public void render() {
		world.step(1/60f, 6, 2);
		this.physicsCam.position.set(hero.getPosition().x, hero.getPosition().y, 0);
		this.physicsCam.update();
		this.cam.position.set(hero.getPosition().x * PPM, hero.getPosition().y * PPM, 0);
		this.cam.update();
		renderer2.setView(cam);
		renderer2.render();
		hero.update();
		testEnemy.update();
		b2dr.render(world, physicsCam.combined);
		MyInput.update();
	}
	public void dispose(){
		hero.disposeStuff();
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
	public StarForce getGame() {
		return game;
	}
	public void setGame(StarForce game) {
		this.game = game;
	}
}
